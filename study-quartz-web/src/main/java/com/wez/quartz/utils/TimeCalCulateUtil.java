package com.wez.quartz.utils;

import javax.sound.midi.Soundbank;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeCalCulateUtil {

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间减去分钟
     * @param date 日期，格式：yyyy-MM-dd hh:mm:ss
     * @param minute 分钟
     * @return
     */
    public static String dateSubMinute(String date, int minute) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        Date parseTime = sdf.parse(date);
        long timestamp = parseTime.getTime() - ((long) minute * 60 *1000);
        return sdf.format(new Date(timestamp));
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(dateSubMinute("2020-03-29 15:51:05", 5));
    }

}
