package com.thc.winterdemo.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NowDate {
    public String getNow(){
        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(nowDate);
    }

    public String due(int minute){
        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //Calendar 객체는 날짜와 시간을 더하거나 빼는 등의 조작이 가능하도록 돕는 클래스
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate); //현재 시각 설정
        cal.add(Calendar.MINUTE, minute); //현재 시간 + minute 더한 값
        return simpleDateFormat.format(cal.getTime());
    }
}
