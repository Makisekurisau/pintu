package com.example.pingtu;

public class People {
    public int ID = -1;
    public String time;
    public String name;
    public String level;

    @Override
    public String toString(){
        String result = "";
        result += "ID：" + this.ID + "，";
        result += "昵称：" + this.name + "，";
        result += "时间：" + this.time;
        return result;
    }
}

