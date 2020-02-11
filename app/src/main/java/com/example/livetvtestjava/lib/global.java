package com.example.livetvtestjava.lib;

import com.example.livetvtestjava.Entity.FilmItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class global {
    public static final int MSG_OK=1;
    public static final int MSG_ERROR=0;
    public static  FilmItem filmSelected;
    //此方法是将2017-11-18T07:12:06.615Z格式的时间转化为秒为单位的Long类型。
    public static Date UTC2GST(String utcStr){
        String time = utcStr;//"2017-11-30T10:41:44.651Z";
        time = time.replace("Z", " UTC");//UTC是本地时间
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        Date d = null;
        try {
            d = format.parse(time);
        } catch (ParseException e) {}
        return d;
    }
}
