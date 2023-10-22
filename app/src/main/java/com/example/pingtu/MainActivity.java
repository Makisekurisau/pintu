package com.example.pingtu;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ImageButton ib00,ib01,ib02,ib03,ib04,ib10,ib11,ib12,ib13,ib14,ib20,ib21,ib22,ib23,ib24,ib30,ib31,ib32,ib33,ib34,ib40,ib41,ib42,ib43,ib44;
    //   一个重启按钮
    public ImageButton[] easy_button={ib00,ib01,ib02,ib10,ib11,ib12,ib20,ib21,ib22};
    public ImageButton[] hard_button={ib00,ib01,ib02,ib03,ib10,ib11,ib12,ib13,ib20,ib21,ib22,ib23,ib30,ib31,ib32,ib33};
    public ImageButton[] hell_button={ib00,ib01,ib02,ib03,ib04,ib10,ib11,ib12,ib13,ib14,ib20,ib21,ib22,ib23,ib24,ib30,ib31,ib32,ib33,ib34,ib40,ib41,ib42,ib43,ib44};
    Button restartBtn;
    ImageView shili;
    int flag=0;
    //  一个显示时间的文本框
    TextView timeTv;
    int time=0;
    private SoundPool soundPool;
    private int soundID;
    private MediaPlayer music;
    DBAdapter dbAdapter;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
//      重写handleMessage方法,根据msg中what的值判断是否执行后续操作
        public void handleMessage(Message msg) {
            if (msg.what==1) {
                time++;
                timeTv.setText("时间 : "+time+" 秒");
//               指定延时1000毫秒后发送参数what为1的空信息
                handler.sendEmptyMessageDelayed(1,1000);
            }
        }
    };

    @SuppressLint("NewApi")
    public void initSound() {
        soundPool = new SoundPool.Builder().build();
        soundID = soundPool.load(this, R.raw.y1980, 1);
    }

    public void playSound() {
        soundPool.play(
                soundID,
                0.1f,      //左耳道音量【0~1】
                0.5f,      //右耳道音量【0~1】
                0,         //播放优先级【0表示最低优先级】
                0,         //循环模式【0表示循环一次，-1表示一直循环，其他表示数字+1表示当前数字对应的循环次数】
                1          //播放速度【1是正常，范围从0~2】
        );
    }
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSound();
        final ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                MyAudioService.PlayBinder playBinder = (MyAudioService.PlayBinder) iBinder;
                //获取代理人对象
                playBinder.MyMethod();  //调用代理方法
            }
            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                //断开服务连接
            }
        };
        Intent intent = new Intent(getApplicationContext(),MyAudioService.class);
        bindService(intent,connection,BIND_AUTO_CREATE);
        dbAdapter=new DBAdapter(this);
        dbAdapter.open();
    }
    protected void onDestroy() {
        super.onDestroy();
        if (intent != null){
            // 对于intentService，这一步可能是多余的
            stopService(intent);
        }
    }
    private void easy_initView() {
        easy_button[0] = findViewById(R.id.pt_ib_00x00);
        easy_button[1] = findViewById(R.id.pt_ib_00x01);
        easy_button[2] = findViewById(R.id.pt_ib_00x02);
        easy_button[3] = findViewById(R.id.pt_ib_01x00);
        easy_button[4] = findViewById(R.id.pt_ib_01x01);
        easy_button[5] = findViewById(R.id.pt_ib_01x02);
        easy_button[6] = findViewById(R.id.pt_ib_02x00);
        easy_button[7] = findViewById(R.id.pt_ib_02x01);
        easy_button[8] = findViewById(R.id.pt_ib_02x02);
        timeTv = findViewById(R.id.pt_tv_time);
        restartBtn = findViewById(R.id.button11);
        shili=findViewById(R.id.imageView);
        easy_disruptRandom();
    }
    private void hard_initView() {
        hard_button[0] = findViewById(R.id.pt_ib_00x00);
        hard_button[1] = findViewById(R.id.pt_ib_00x01);
        hard_button[2] = findViewById(R.id.pt_ib_00x02);
        hard_button[3] = findViewById(R.id.pt_ib_00x03);
        hard_button[4] = findViewById(R.id.pt_ib_01x00);
        hard_button[5] = findViewById(R.id.pt_ib_01x01);
        hard_button[6] = findViewById(R.id.pt_ib_01x02);
        hard_button[7] = findViewById(R.id.pt_ib_01x03);
        hard_button[8] = findViewById(R.id.pt_ib_02x00);
        hard_button[9] = findViewById(R.id.pt_ib_02x01);
        hard_button[10] = findViewById(R.id.pt_ib_02x02);
        hard_button[11] = findViewById(R.id.pt_ib_02x03);
        hard_button[12] = findViewById(R.id.pt_ib_03x00);
        hard_button[13] = findViewById(R.id.pt_ib_03x01);
        hard_button[14]= findViewById(R.id.pt_ib_03x02);
        hard_button[15] = findViewById(R.id.pt_ib_03x03);
        timeTv = findViewById(R.id.pt_tv_time);
        shili=findViewById(R.id.imageView);
        restartBtn = findViewById(R.id.button11);
        hard_disruptRandom();
    }

    private void hell_initView() {
        hell_button[0] = findViewById(R.id.pt_ib_00x00);
        hell_button[1] = findViewById(R.id.pt_ib_00x01);
        hell_button[2] = findViewById(R.id.pt_ib_00x02);
        hell_button[3] = findViewById(R.id.pt_ib_00x03);
        hell_button[4]= findViewById(R.id.pt_ib_00x04);
        hell_button[5] = findViewById(R.id.pt_ib_01x00);
        hell_button[6] = findViewById(R.id.pt_ib_01x01);
        hell_button[7] = findViewById(R.id.pt_ib_01x02);
        hell_button[8] = findViewById(R.id.pt_ib_01x03);
        hell_button[9] = findViewById(R.id.pt_ib_01x04);
        hell_button[10] = findViewById(R.id.pt_ib_02x00);
        hell_button[11] = findViewById(R.id.pt_ib_02x01);
        hell_button[12] = findViewById(R.id.pt_ib_02x02);
        hell_button[13] = findViewById(R.id.pt_ib_02x03);
        hell_button[14] = findViewById(R.id.pt_ib_02x04);
        hell_button[15] = findViewById(R.id.pt_ib_03x00);
        hell_button[16] = findViewById(R.id.pt_ib_03x01);
        hell_button[17] = findViewById(R.id.pt_ib_03x02);
        hell_button[18] = findViewById(R.id.pt_ib_03x03);
        hell_button[19] = findViewById(R.id.pt_ib_03x04);
        hell_button[20] = findViewById(R.id.pt_ib_04x00);
        hell_button[21] = findViewById(R.id.pt_ib_04x01);
        hell_button[22] = findViewById(R.id.pt_ib_04x02);
        hell_button[23] = findViewById(R.id.pt_ib_04x03);
        hell_button[24] = findViewById(R.id.pt_ib_04x04);
        timeTv = findViewById(R.id.pt_tv_time);
        restartBtn = findViewById(R.id.button11);
        shili=findViewById(R.id.imageView);
        hell_disruptRandom();
    }
    private final int[]easy_image = {R.mipmap.image00,R.mipmap.image01,R.mipmap.image02,
            R.mipmap.image10,R.mipmap.image11,R.mipmap.image12,
            R.mipmap.image20,R.mipmap.image21,R.mipmap.image22};
    private Drawable[]easy_image2=new Drawable[9];
    private Drawable[]hard_image2=new Drawable[16];
    private Drawable[]hell_image2=new Drawable[25];
    private final int[]hard_image = {R.mipmap.liyu01x01,R.mipmap.liyu01x02,R.mipmap.liyu01x03,R.mipmap.liyu01x04,
            R.mipmap.liyu02x01,R.mipmap.liyu02x02,R.mipmap.liyu02x03,R.mipmap.liyu02x04,
            R.mipmap.liyu03x01,R.mipmap.liyu03x02,R.mipmap.liyu03x03,R.mipmap.liyu03x04,
            R.mipmap.liyu04x01,R.mipmap.liyu04x02,R.mipmap.liyu04x03,R.mipmap.liyu04x04};
    private final int[]hell_image = {R.mipmap.hell01x01,R.mipmap.hell01x02,R.mipmap.hell01x03,R.mipmap.hell01x04,R.mipmap.hell01x05,
            R.mipmap.hell02x01,R.mipmap.hell02x02,R.mipmap.hell02x03,R.mipmap.hell02x04,R.mipmap.hell02x05,
            R.mipmap.hell03x01,R.mipmap.hell03x02,R.mipmap.hell03x03,R.mipmap.hell03x04,R.mipmap.hell03x05,
            R.mipmap.hell04x01,R.mipmap.hell04x02,R.mipmap.hell04x03,R.mipmap.hell04x04,R.mipmap.hell04x05,
            R.mipmap.hell05x01,R.mipmap.hell05x02,R.mipmap.hell05x03,R.mipmap.hell05x04,R.mipmap.hell05x05};
    private final int[]easy_imageIndex = new int[easy_image.length];
    private final int[]hard_imageIndex = new int[hard_image.length];
    private final int[]hell_imageIndex = new int[hell_image.length];
    private void easy_swap(int rand1, int rand2) {
        int temp = easy_imageIndex[rand1];
        easy_imageIndex[rand1] = easy_imageIndex[rand2];
        easy_imageIndex[rand2] = temp;
    }
    private void hard_swap(int rand1, int rand2) {
        int temp = hard_imageIndex[rand1];
        hard_imageIndex[rand1] = hard_imageIndex[rand2];
        hard_imageIndex[rand2] = temp;
    }
    private void hell_swap(int rand1, int rand2) {
        int temp = hell_imageIndex[rand1];
        hell_imageIndex[rand1] = hell_imageIndex[rand2];
        hell_imageIndex[rand2] = temp;
    }
    private void easy_disruptRandom() {
//      给下标数组每个元素赋值，下标是i,值就为i
        //      给下标数组每个元素赋值，下标是i,值就为i
        for (int i = 0; i < easy_imageIndex.length; i++) {
            easy_imageIndex[i] = i;
        }
//        规定20次，随机选择两个角标对应的值进行交换
        int rand1,rand2;
        for (int j = 0; j < 20; j++) {
//            随机生成第一个角标
//            Math.random()产生的随机数为0~1之间的小数 此处说的0~1是包含左不包含右，即包含0不包含1
//            Math.random()的值域为[0,1)，然后*8就是[0,8)，再int取整最终值域为{0,1,2,3,4,5,,6,7}
            rand1 = (int)(Math.random()*(easy_imageIndex.length));
//            第二次随机生成的角标，不能和第一次随机生成的角标相同，如果相同，就不方便交换了
            do {
                rand2 = (int)(Math.random()*(easy_imageIndex.length));
//             判断第一次和第二次生成的角标是否相同,不同则break立刻跳出循环，执行swap交换
                if (rand1!=rand2) {
                    break;
                }
//             若第二次生成的与第一次相同，则重新进入do-while循环生成rand2
            }while (true);
//            交换两个角标上对应的值
            easy_swap(rand1,rand2);
        }
        if (flag==1)
            for(int i=0;i<9;i++)
                easy_button[i].setImageResource(easy_image[easy_imageIndex[i]]);
        else{
            for(int i=0;i<9;i++)
                easy_button[i].setImageDrawable(easy_image2[easy_imageIndex[i]]);
        }
    }
    private void hard_disruptRandom() {
//      给下标数组每个元素赋值，下标是i,值就为i
        //      给下标数组每个元素赋值，下标是i,值就为i
        for (int i = 0; i < hard_imageIndex.length; i++) {
            hard_imageIndex[i] = i;
        }
//        规定20次，随机选择两个角标对应的值进行交换
        int rand1,rand2;
        for (int j = 0; j < 40; j++) {
//            随机生成第一个角标
//            Math.random()产生的随机数为0~1之间的小数 此处说的0~1是包含左不包含右，即包含0不包含1
//            Math.random()的值域为[0,1)，然后*8就是[0,8)，再int取整最终值域为{0,1,2,3,4,5,,6,7}
            rand1 = (int) (Math.random() * (hard_imageIndex.length));
//            第二次随机生成的角标，不能和第一次随机生成的角标相同，如果相同，就不方便交换了
            do {
                rand2 = (int) (Math.random() * (hard_imageIndex.length));
//             判断第一次和第二次生成的角标是否相同,不同则break立刻跳出循环，执行swap交换
                if (rand1 != rand2) {
                    break;
                }
//             若第二次生成的与第一次相同，则重新进入do-while循环生成rand2
            } while (true);
//            交换两个角标上对应的值
            hard_swap(rand1, rand2);
        }
        if (flag==1)
            for(int i=0;i<16;i++)
                hard_button[i].setImageResource(hard_image[hard_imageIndex[i]]);
        else{
            for(int i=0;i<16;i++)
                hard_button[i].setImageDrawable(hard_image2[hard_imageIndex[i]]);
        }
    }

    private void hell_disruptRandom() {
//      给下标数组每个元素赋值，下标是i,值就为i
        //      给下标数组每个元素赋值，下标是i,值就为i
        for (int i = 0; i < hell_imageIndex.length; i++) {
            hell_imageIndex[i] = i;
        }
//        规定20次，随机选择两个角标对应的值进行交换
        int rand1,rand2;
        for (int j = 0; j < 60; j++) {
//            随机生成第一个角标
//            Math.random()产生的随机数为0~1之间的小数 此处说的0~1是包含左不包含右，即包含0不包含1
//            Math.random()的值域为[0,1)，然后*8就是[0,8)，再int取整最终值域为{0,1,2,3,4,5,,6,7}
            rand1 = (int)(Math.random()*(hell_imageIndex.length));
//            第二次随机生成的角标，不能和第一次随机生成的角标相同，如果相同，就不方便交换了
            do {
                rand2 = (int)(Math.random()*(hell_imageIndex.length));
//             判断第一次和第二次生成的角标是否相同,不同则break立刻跳出循环，执行swap交换
                if (rand1!=rand2) {
                    break;
                }
//             若第二次生成的与第一次相同，则重新进入do-while循环生成rand2
            }while (true);
//            交换两个角标上对应的值
            hell_swap(rand1,rand2);
        }
        if (flag==1)
            for(int i=0;i<25;i++)
                hell_button[i].setImageResource(hell_image[hell_imageIndex[i]]);
        else {
            for (int i = 0; i < 25; i++)
                hell_button[i].setImageDrawable(hell_image2[hell_imageIndex[i]]);
        }
    }
    public void easy(View view) {
        setContentView(R.layout.easy);
        flag=1;
        easy_initView();
        handler.sendEmptyMessageDelayed(1,1000);
    }

    public void hard(View view) {
        setContentView(R.layout.hard);
        flag=1;
        hard_initView();
        handler.sendEmptyMessageDelayed(1,1000);
    }
    public void hell(View view) {
        setContentView(R.layout.hell);
        flag=1;
        hell_initView();
        handler.sendEmptyMessageDelayed(1,1000);
    }
    public int ready=0,start=-1,start_id=-1;

    public void easy_onClick(View view) {
        playSound();
        int id = view.getId();
        if(ready==0){
            ready=1;
            if (id== R.id.pt_ib_00x00)
            {start=id;start_id=0;}
            else if (id==R.id.pt_ib_00x01)
            {start=id;start_id=1;}
            else if(id== R.id.pt_ib_00x02)
            {start=id;start_id=2;}
            else if(id==  R.id.pt_ib_01x00)
            {start=id;start_id=3;}
            else if(id==  R.id.pt_ib_01x01)
            {start=id;start_id=4;}
            else if(id==  R.id.pt_ib_01x02)
            {start=id;start_id=5;}
            else if(id==  R.id.pt_ib_02x00)
            {start=id;start_id=6;}
            else if(id==  R.id.pt_ib_02x01)
            {start=id;start_id=7;}
            else if(id==  R.id.pt_ib_02x02)
            {start=id;start_id=8;}
        }
        else{
            if (start==id)
            {
                ready=0;
                start=-1;start_id=-1;
            }
            else {
                //        九个按钮执行的点击事件的逻辑应该是相同的，如果有空格在周围，可以改变图片显示的位置，否则点击事件不响应
                if (id == R.id.pt_ib_00x00)
                    easy_move(id, 0);
                else if (id == R.id.pt_ib_00x01)
                    easy_move(id, 1);
                else if (id == R.id.pt_ib_00x02)
                    easy_move(id, 2);
                else if (id == R.id.pt_ib_01x00)
                    easy_move(id, 3);
                else if (id == R.id.pt_ib_01x01)
                    easy_move(id, 4);
                else if (id == R.id.pt_ib_01x02)
                    easy_move(id, 5);
                else if (id == R.id.pt_ib_02x00)
                    easy_move(id, 6);
                else if (id == R.id.pt_ib_02x01)
                    easy_move(id, 7);
                else if (id == R.id.pt_ib_02x02)
                    easy_move(id, 8);
                ready = 0;
            }
        }
    }

    public void hard_onClick(View view) {
        playSound();
        int id = view.getId();
        if(ready==0){
            ready=1;
            if (id== R.id.pt_ib_00x00)
            {start=id;start_id=0;}
            else if (id==R.id.pt_ib_00x01)
            {start=id;start_id=1;}
            else if(id== R.id.pt_ib_00x02)
            {start=id;start_id=2;}
            else if(id== R.id.pt_ib_00x03)
            {start=id;start_id=3;}
            else if(id==  R.id.pt_ib_01x00)
            {start=id;start_id=4;}
            else if(id==  R.id.pt_ib_01x01)
            {start=id;start_id=5;}
            else if(id==  R.id.pt_ib_01x02)
            {start=id;start_id=6;}
            else if(id==  R.id.pt_ib_01x03)
            {start=id;start_id=7;}
            else if(id==  R.id.pt_ib_02x00)
            {start=id;start_id=8;}
            else if(id==  R.id.pt_ib_02x01)
            {start=id;start_id=9;}
            else if(id==  R.id.pt_ib_02x02)
            {start=id;start_id=10;}
            else if(id==  R.id.pt_ib_02x03)
            {start=id;start_id=11;}
            else if(id==  R.id.pt_ib_03x00)
            {start=id;start_id=12;}
            else if(id==  R.id.pt_ib_03x01)
            {start=id;start_id=13;}
            else if(id==  R.id.pt_ib_03x02)
            {start=id;start_id=14;}
            else if(id==  R.id.pt_ib_03x03)
            {start=id;start_id=15;}

        }
        else{
            if (start==id)
            {
                ready=0;
                start=-1;start_id=-1;
            }
            else {
                //        九个按钮执行的点击事件的逻辑应该是相同的，如果有空格在周围，可以改变图片显示的位置，否则点击事件不响应
                if (id == R.id.pt_ib_00x00)
                    hard_move(id, 0);
                else if (id == R.id.pt_ib_00x01)
                    hard_move(id, 1);
                else if (id == R.id.pt_ib_00x02)
                    hard_move(id, 2);
                else if (id == R.id.pt_ib_00x03)
                    hard_move(id, 3);
                else if (id == R.id.pt_ib_01x00)
                    hard_move(id, 4);
                else if (id == R.id.pt_ib_01x01)
                    hard_move(id, 5);
                else if (id == R.id.pt_ib_01x02)
                    hard_move(id, 6);
                else if (id == R.id.pt_ib_01x03)
                    hard_move(id, 7);
                else if (id == R.id.pt_ib_02x00)
                    hard_move(id, 8);
                else if (id == R.id.pt_ib_02x01)
                    hard_move(id, 9);
                else if (id == R.id.pt_ib_02x02)
                    hard_move(id, 10);
                else if (id == R.id.pt_ib_02x03)
                    hard_move(id, 11);
                else if (id == R.id.pt_ib_03x00)
                    hard_move(id, 12);
                else if (id == R.id.pt_ib_03x01)
                    hard_move(id, 13);
                else if (id == R.id.pt_ib_03x02)
                    hard_move(id, 14);
                else if (id == R.id.pt_ib_03x03)
                    hard_move(id, 15);
                ready = 0;
            }
        }
    }

    public void hell_onClick(View view) {
        playSound();
        int id = view.getId();
        if(ready==0){
            ready=1;
            if (id== R.id.pt_ib_00x00)
            {start=id;start_id=0;}
            else if (id==R.id.pt_ib_00x01)
            {start=id;start_id=1;}
            else if(id== R.id.pt_ib_00x02)
            {start=id;start_id=2;}
            else if(id== R.id.pt_ib_00x03)
            {start=id;start_id=3;}
            else if(id== R.id.pt_ib_00x04)
            {start=id;start_id=4;}
            else if(id==  R.id.pt_ib_01x00)
            {start=id;start_id=5;}
            else if(id==  R.id.pt_ib_01x01)
            {start=id;start_id=6;}
            else if(id==  R.id.pt_ib_01x02)
            {start=id;start_id=7;}
            else if(id==  R.id.pt_ib_01x03)
            {start=id;start_id=8;}
            else if(id==  R.id.pt_ib_01x04)
            {start=id;start_id=9;}
            else if(id==  R.id.pt_ib_02x00)
            {start=id;start_id=10;}
            else if(id==  R.id.pt_ib_02x01)
            {start=id;start_id=11;}
            else if(id==  R.id.pt_ib_02x02)
            {start=id;start_id=12;}
            else if(id==  R.id.pt_ib_02x03)
            {start=id;start_id=13;}
            else if(id==  R.id.pt_ib_02x04)
            {start=id;start_id=14;}
            else if(id==  R.id.pt_ib_03x00)
            {start=id;start_id=15;}
            else if(id==  R.id.pt_ib_03x01)
            {start=id;start_id=16;}
            else if(id==  R.id.pt_ib_03x02)
            {start=id;start_id=17;}
            else if(id==  R.id.pt_ib_03x03)
            {start=id;start_id=18;}
            else if(id==  R.id.pt_ib_03x04)
            {start=id;start_id=19;}
            else if(id==  R.id.pt_ib_04x00)
            {start=id;start_id=20;}
            else if(id==  R.id.pt_ib_04x01)
            {start=id;start_id=21;}
            else if(id==  R.id.pt_ib_04x02)
            {start=id;start_id=22;}
            else if(id==  R.id.pt_ib_04x03)
            {start=id;start_id=23;}
            else if(id==  R.id.pt_ib_04x04)
            {start=id;start_id=24;}

        }
        else{
            if (start==id)
            {
                ready=0;
                start=-1;start_id=-1;
            }
            else {
                //        九个按钮执行的点击事件的逻辑应该是相同的，如果有空格在周围，可以改变图片显示的位置，否则点击事件不响应
                if (id == R.id.pt_ib_00x00)
                    hell_move(id, 0);
                else if (id == R.id.pt_ib_00x01)
                    hell_move(id, 1);
                else if (id == R.id.pt_ib_00x02)
                    hell_move(id, 2);
                else if (id == R.id.pt_ib_00x03)
                    hell_move(id, 3);
                else if (id == R.id.pt_ib_00x04)
                    hell_move(id, 4);
                else if (id == R.id.pt_ib_01x00)
                    hell_move(id, 5);
                else if (id == R.id.pt_ib_01x01)
                    hell_move(id, 6);
                else if (id == R.id.pt_ib_01x02)
                    hell_move(id, 7);
                else if (id == R.id.pt_ib_01x03)
                    hell_move(id, 8);
                else if (id == R.id.pt_ib_01x04)
                    hell_move(id, 9);
                else if (id == R.id.pt_ib_02x00)
                    hell_move(id, 10);
                else if (id == R.id.pt_ib_02x01)
                    hell_move(id, 11);
                else if (id == R.id.pt_ib_02x02)
                    hell_move(id, 12);
                else if (id == R.id.pt_ib_02x03)
                    hell_move(id, 13);
                else if (id == R.id.pt_ib_02x04)
                    hell_move(id, 14);
                else if (id == R.id.pt_ib_03x00)
                    hell_move(id, 15);
                else if (id == R.id.pt_ib_03x01)
                    hell_move(id, 16);
                else if (id == R.id.pt_ib_03x02)
                    hell_move(id, 17);
                else if (id == R.id.pt_ib_03x03)
                    hell_move(id, 18);
                else if (id == R.id.pt_ib_03x04)
                    hell_move(id, 19);
                else if (id == R.id.pt_ib_04x00)
                    hell_move(id, 20);
                else if (id == R.id.pt_ib_04x01)
                    hell_move(id, 21);
                else if (id == R.id.pt_ib_04x02)
                    hell_move(id, 22);
                else if (id == R.id.pt_ib_04x03)
                    hell_move(id, 23);
                else if (id == R.id.pt_ib_04x04)
                    hell_move(id, 24);
                ready = 0;
            }
        }
    }

    /*表示移动指定位置的按钮的函数，将图片和空白区域进行交换*/
    //imagebuttonId是被选中的图片的id，site是该图片在9宫格的位置（0-8）
    private void easy_move(int imagebuttonId, int site) {
        //     每行的图片个数
        int imageX = 3;
//     每列的图片个数
        int imageY = 3;
//    图片的总数目
        int imgCount = imageX*imageY;

//        判断选中的图片在第几行,imageX为3，所以进行取整运算
        int sitex = site / imageX;
//        判断选中的图片在第几列,imageY为3，所以进行取模运算
        int sitey = site % imageY;
//        获取空白区域的坐标，blankx为行坐标，blanky为列坐标
        int blankx = start_id / imageX;
        int blanky = start_id % imageY;

        int x = Math.abs(sitex-blankx);
        int y = Math.abs(sitey-blanky);
        if ((x==0&&y==1)||(y==0&&x==1)){
//            通过id，查找到这个可以移动的按钮
            ImageButton clickButton = findViewById(imagebuttonId);
//            查找到空白区域的按钮
            ImageButton blankButton = findViewById(start);
//            将空白区域的按钮设置为图片，image[imageIndex[site]就是刚刚选中的图片，因为这在上面disruptRandom()设置过
            if(flag==1){
            blankButton.setImageResource(easy_image[easy_imageIndex[site]]);
            clickButton.setImageResource(easy_image[easy_imageIndex[start_id]]);}
            else{
                blankButton.setImageDrawable(easy_image2[easy_imageIndex[site]]);
                clickButton.setImageDrawable(easy_image2[easy_imageIndex[start_id]]);
            }
//            将改变角标的过程记录到存储图片位置的数组当中
            easy_swap(site,start_id);
        }
        easy_check();
    }
    private void hard_move(int imagebuttonId, int site) {
        //     每行的图片个数
        int imageX = 4;
//     每列的图片个数
        int imageY = 4;
//        判断选中的图片在第几行,imageX为3，所以进行取整运算
        int sitex = site / imageX;
//        判断选中的图片在第几列,imageY为3，所以进行取模运算
        int sitey = site % imageY;
//        获取空白区域的坐标，blankx为行坐标，blanky为列坐标
        int blankx = start_id / imageX;
        int blanky = start_id % imageY;
//        可以移动的条件有两个
//        1.在同一行，列数相减，绝对值为1，可移动   2.在同一列，行数相减，绝对值为1，可以移动
        int x = Math.abs(sitex-blankx);
        int y = Math.abs(sitey-blanky);
        if ((x==0&&y==1)||(y==0&&x==1)){
//            通过id，查找到这个可以移动的按钮
            ImageButton clickButton = findViewById(imagebuttonId);
//            查找到空白区域的按钮
            ImageButton blankButton = findViewById(start);
//            将空白区域的按钮设置为图片，image[imageIndex[site]就是刚刚选中的图片，因为这在上面disruptRandom()设置过
            if(flag==1){
            blankButton.setImageResource(hard_image[hard_imageIndex[site]]);
            clickButton.setImageResource(hard_image[hard_imageIndex[start_id]]);}
            else{
                blankButton.setImageDrawable(hard_image2[hard_imageIndex[site]]);
                clickButton.setImageDrawable(hard_image2[hard_imageIndex[start_id]]);
            }
//            将改变角标的过程记录到存储图片位置的数组当中
            hard_swap(site,start_id);
        }
        hard_check();
    }
    private void hell_move(int imagebuttonId, int site) {
        //     每行的图片个数
        int imageX = 5;
//     每列的图片个数
        int imageY = 5;

//    图片的总数目
        int imgCount = imageX*imageY;
//    空白区域的位置
//        int blankSwap = imgCount-1;
////    初始化空白区域的按钮id
//        int blankImgid = R.id.pt_ib_02x02;

//        判断选中的图片在第几行,imageX为3，所以进行取整运算
        int sitex = site / imageX;
//        判断选中的图片在第几列,imageY为3，所以进行取模运算
        int sitey = site % imageY;
//        获取空白区域的坐标，blankx为行坐标，blanky为列坐标
        int blankx = start_id / imageX;
        int blanky = start_id % imageY;
//        可以移动的条件有两个
//        1.在同一行，列数相减，绝对值为1，可移动   2.在同一列，行数相减，绝对值为1，可以移动
        int x = Math.abs(sitex-blankx);
        int y = Math.abs(sitey-blanky);
        if ((x==0&&y==1)||(y==0&&x==1)){
//            通过id，查找到这个可以移动的按钮
            ImageButton clickButton = findViewById(imagebuttonId);
//            查找到空白区域的按钮
            ImageButton blankButton = findViewById(start);
//            将空白区域的按钮设置为图片，image[imageIndex[site]就是刚刚选中的图片，因为这在上面disruptRandom()设置过
            if(flag==1){
                blankButton.setImageResource(hell_image[hell_imageIndex[site]]);
                clickButton.setImageResource(hell_image[hell_imageIndex[start_id]]);}
            else{
                blankButton.setImageDrawable(hell_image2[hell_imageIndex[site]]);
                clickButton.setImageDrawable(hell_image2[hell_imageIndex[start_id]]);
            }
//            将改变角标的过程记录到存储图片位置的数组当中
            hell_swap(site,start_id);
        }
        hell_check();
    }

    private void easy_check() {
        boolean loop = true;   //定义标志位loop
        for (int i = 0; i < easy_imageIndex.length; i++) {
            if (easy_imageIndex[i] != i) {
                loop = false;
                break;
            }
        }
        if (loop) {
            handler.removeMessages(1);
            final View v =  getLayoutInflater().inflate(R.layout.dialog,null);
            final EditText n=new EditText(this);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            People t=new People();
            People [] people =dbAdapter.queryAllData();
            if(people==null)
                t.ID=1;
            else
                t.ID= people.length+1;
            builder.setMessage("恭喜，拼图成功！\n您是第"+t.ID+"个完成拼图的玩家，用时为"+time+"秒\n是否保存成绩？请输入您的名称")
//           第三步：调用setPositive/Negative/NeutralButton()设置：确定，取消，中立按钮
                    .setView(n)
                    .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            t.time= String.valueOf(time);
                            t.name= n.getText().toString();
                            t.level="easy";
                            dbAdapter.insert(t);
                        }})
                    .setNegativeButton("取消",null);
//           第四歩：调用create()方法创建这个对象
            AlertDialog dialog = builder.create();
//           第五歩：调用show()方法来显示我们的AlertDialog对话框
            dialog.show();
        }
    }
    private void hard_check() {
        boolean loop = true;   //定义标志位loop
        for (int i = 0; i < hard_imageIndex.length; i++) {
            if (hard_imageIndex[i] != i) {
                loop = false;
                break;
            }
        }
        if (loop) {
//            拼图成功了
//            停止计时
            handler.removeMessages(1);
//            拼图成功后，第九块空白显示出图片，即下标为8的第九张图片
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//          调用setIcon()设置图标，setTitle()或setCustomTitle()设置标题
//           第二步：设置对话框的内容：setMessage()方法来指定显示的内容
            final View v =  getLayoutInflater().inflate(R.layout.dialog,null);
            final EditText n=new EditText(this);
            People t=new People();
            People [] people =dbAdapter.queryAllData();
            if(people==null)
                t.ID=1;
            else
                t.ID= people.length+1;
            builder.setMessage("恭喜，拼图成功！\n您是第"+t.ID+"个完成拼图的玩家，用时为"+time+"秒\n是否保存成绩？请输入您的名称")
//           第三步：调用setPositive/Negative/NeutralButton()设置：确定，取消，中立按钮
                    .setView(n)
                    .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            t.time= String.valueOf(time);
                            t.name= n.getText().toString();
                            t.level="hard";
                            dbAdapter.insert(t);
                        }
                    })
                    .setNegativeButton("取消",null);
//           第四歩：调用create()方法创建这个对象
            AlertDialog dialog = builder.create();
//           第五歩：调用show()方法来显示我们的AlertDialog对话框
            dialog.show();
        }
    }
    private void hell_check() {
        boolean loop = true;   //定义标志位loop
        for (int i = 0; i < hell_imageIndex.length; i++) {
            if (hell_imageIndex[i] != i) {
                loop = false;
                break;
            }
        }
        if (loop) {
//            拼图成功了
//            停止计时
            handler.removeMessages(1);
//            拼图成功后，第九块空白显示出图片，即下标为8的第九张图片
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//          调用setIcon()设置图标，setTitle()或setCustomTitle()设置标题
//           第二步：设置对话框的内容：setMessage()方法来指定显示的内容
            final View v =  getLayoutInflater().inflate(R.layout.dialog,null);
            final EditText n=new EditText(this);
            People t=new People();
            People [] people =dbAdapter.queryAllData();
            if(people==null)
                t.ID=1;
            else
                t.ID= people.length+1;
            builder.setMessage("恭喜，拼图成功！\n您是第"+t.ID+"个完成拼图的玩家，用时为"+time+"秒\n是否保存成绩？请输入您的名称")
//           第三步：调用setPositive/Negative/NeutralButton()设置：确定，取消，中立按钮
                    .setView(n)
                    .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            t.time= String.valueOf(time);
                            t.name= n.getText().toString();
                            t.level="hell";
                            dbAdapter.insert(t);
                        }
                    })
                    .setNegativeButton("取消",null);
//           第四歩：调用create()方法创建这个对象
            AlertDialog dialog = builder.create();
//           第五歩：调用show()方法来显示我们的AlertDialog对话框
            dialog.show();
        }
    }
    public void easy_restart(View view) {
        //       停止handler的消息发送
        handler.removeMessages(1);
//       将时间重新归0，并且重新开始计时
        time = 0;
        timeTv.setText("时间 : "+time+" 秒");
//      每隔1s发送参数what为1的消息msg
        handler.sendEmptyMessageDelayed(1,1000);
        easy_disruptRandom();
//        if(flag==1)
//            easy_disruptRandom();
//        else
//            easy_split(view);
    }
    public void hard_restart(View view) {
        //       停止handler的消息发送
        handler.removeMessages(1);
//       将时间重新归0，并且重新开始计时
        time = 0;
        timeTv.setText("时间 : "+time+" 秒");
//      每隔1s发送参数what为1的消息msg
        handler.sendEmptyMessageDelayed(1,1000);
        hard_disruptRandom();
    }
    public void hell_restart(View view) {
        //       停止handler的消息发送
        handler.removeMessages(1);
//       将时间重新归0，并且重新开始计时
        time = 0;
        timeTv.setText("时间 : "+time+" 秒");
//      每隔1s发送参数what为1的消息msg
        handler.sendEmptyMessageDelayed(1,1000);
        hell_disruptRandom();
    }

    public void Return(View view) {
        setContentView(R.layout.activity_main);
        handler.removeMessages(1);
        time=0;
    }

    public void rank(View view) {
        setContentView(R.layout.history);
        TextView t_1 = findViewById(R.id.tv_query_result);
        TextView t_2 = findViewById(R.id.textView4);
        TextView t_3 = findViewById(R.id.textView5);
        People[] peoples = dbAdapter.queryEasyData();
        People[] peoplea = dbAdapter.queryhardData();
        People[] people3 = dbAdapter.queryhellData();
        String t = "";
        if (peoples!=null)
            for (int i = 0; i < peoples.length & i < 10; ++i) {
                t += peoples[i].toString() + "\n";
            }
        else
            t="无记录";
        t_1.setText(t);
        String t2 = "";
        if(peoplea!=null)
            for (int i = 0; i < peoplea.length & i < 10; ++i) {
                t2 += peoplea[i].toString() + "\n";
            }
        else
            t2="无记录";
        t_2.setText(t2);
        t_1.setText(t);
        String t3 = "";
        if(people3!=null)
            for (int i = 0; i < people3.length & i < 10; ++i) {
                t3 += people3[i].toString() + "\n";
            }
        else
            t3="无记录";
        t_3.setText(t3);
    }
    Bitmap bitmaps;
    int choose=0;

    public void zidingyi(View view) {
        Log.e(this.getClass().getName(), "onCreate");
        setContentView(R.layout.zidingyi);
        Button btn_2 = findViewById(R.id.btn_2);
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 2);
            }
        });
    }
    public void easy_split(View view) {
        if(choose==0){
            Toast.makeText(getApplicationContext(),"未选择图片",Toast.LENGTH_SHORT).show();
            return;
        }
        int originalWidth = bitmaps.getWidth();
        int originalHeight = bitmaps.getHeight();
        int smallWidth = originalWidth / 3;
        int smallHeight = originalHeight / 3;
        ImageView a=findViewById(R.id.imageView);
        //Drawable as = new BitmapDrawable(bitmaps);
        //a.setBackground(as);
        // 分割图片并显示到ImageView中
        for (int i = 0; i < 9; i++) {
            ImageView imageView = new ImageView(this);
            Bitmap smallBitmap = Bitmap.createBitmap(bitmaps, (i % 3) * smallWidth, (i / 3) * smallHeight, smallWidth, smallHeight);
// 设置目标宽度和高度（新的分辨率）
            int targetWidth = 1500; // 目标宽度
            int targetHeight = 1280; // 目标高度

// 计算缩放比例
            float scaleWidth = ((float) targetWidth) / smallWidth;
            float scaleHeight = ((float) targetHeight) / smallHeight;

// 创建新的Bitmap，并进行缩放
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap resizedBitmap = Bitmap.createBitmap(smallBitmap, 0, 0, smallWidth, smallHeight, matrix, false);
            Drawable drawable = new BitmapDrawable(resizedBitmap);
            //easy_button[i].setImageDrawable(drawable);
            easy_image2[i]=drawable;
        }
        setContentView(R.layout.easy);
        easy_initView();
        flag=2;
        shili.setImageBitmap(bitmaps);
        handler.sendEmptyMessageDelayed(1,1000);
    }
    public void hard_split(View view) {
        if(choose==0){
            Toast.makeText(getApplicationContext(),"未选择图片",Toast.LENGTH_SHORT).show();
            return;
        }
        int originalWidth = bitmaps.getWidth();
        int originalHeight = bitmaps.getHeight();
        int smallWidth = originalWidth / 4;
        int smallHeight = originalHeight / 4;
        for (int i = 0; i < 16; i++) {
            ImageView imageView = new ImageView(this);
            Bitmap smallBitmap = Bitmap.createBitmap(bitmaps, (i % 4) * smallWidth, (i / 4) * smallHeight, smallWidth, smallHeight);
// 设置目标宽度和高度（新的分辨率）
            int targetWidth = 770; // 目标宽度
            int targetHeight = 750; // 目标高度

// 计算缩放比例
            float scaleWidth = ((float) targetWidth) / smallWidth;
            float scaleHeight = ((float) targetHeight) / smallHeight;

// 创建新的Bitmap，并进行缩放
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap resizedBitmap = Bitmap.createBitmap(smallBitmap, 0, 0, smallWidth, smallHeight, matrix, false);
            Drawable drawable = new BitmapDrawable(resizedBitmap);
            //easy_button[i].setImageDrawable(drawable);
            hard_image2[i]=drawable;
        }
        setContentView(R.layout.hard);
        hard_initView();
        flag=2;
        shili.setImageBitmap(bitmaps);
        handler.sendEmptyMessageDelayed(1,1000);
    }
    public void hell_split(View view) {
        if(choose==0){
            Toast.makeText(getApplicationContext(),"未选择图片",Toast.LENGTH_SHORT).show();
            return;
        }
        int originalWidth = bitmaps.getWidth();
        int originalHeight = bitmaps.getHeight();
        int smallWidth = originalWidth / 5;
        int smallHeight = originalHeight / 5;
        ImageView a=findViewById(R.id.imageView);
        //Drawable as = new BitmapDrawable(bitmaps);
        //a.setBackground(as);
        // 分割图片并显示到ImageView中
        for (int i = 0; i < 25; i++) {
            ImageView imageView = new ImageView(this);
            Bitmap smallBitmap = Bitmap.createBitmap(bitmaps, (i % 5) * smallWidth, (i / 5) * smallHeight, smallWidth, smallHeight);
// 设置目标宽度和高度（新的分辨率）
            int targetWidth = 620; // 目标宽度
            int targetHeight = 600; // 目标高度

// 计算缩放比例
            float scaleWidth = ((float) targetWidth) / smallWidth;
            float scaleHeight = ((float) targetHeight) / smallHeight;

// 创建新的Bitmap，并进行缩放
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap resizedBitmap = Bitmap.createBitmap(smallBitmap, 0, 0, smallWidth, smallHeight, matrix, false);
            Drawable drawable = new BitmapDrawable(resizedBitmap);
            //easy_button[i].setImageDrawable(drawable);
            hell_image2[i]=drawable;
        }
        setContentView(R.layout.hell);
        hell_initView();
        flag=2;
        shili.setImageBitmap(bitmaps);
        handler.sendEmptyMessageDelayed(1,1000);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            // 从相册返回的数据
            Log.e(this.getClass().getName(), "Result:" + data.toString());
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                ImageView iv_image=findViewById(R.id.imageView2);
                iv_image.setImageURI(uri);
                Log.e(this.getClass().getName(), "Uri:" + String.valueOf(uri));
                try {
                    bitmaps = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                choose=1;
                // 计算小图片的宽度和高度
            }
        }
    }

}