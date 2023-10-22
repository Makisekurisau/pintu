package com.example.pingtu;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MyAudioService extends Service {
    private MediaPlayer mp;

    public class PlayBinder extends Binder{        //用作代理的内部类
        public  void MyMethod(){                   //服务方法
            mp = MediaPlayer.create(getApplicationContext(),R.raw.bgm);
            mp.start();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        // throw new UnsupportedOperationException("Not yet implemented");
        return new PlayBinder();                    //返回服务代理类
    }

    @Override
    public void onDestroy() {                //服务销毁时停止音乐播放
        if (mp != null){
            mp.stop();
            mp.release();
        }
        super.onDestroy();
    }
}