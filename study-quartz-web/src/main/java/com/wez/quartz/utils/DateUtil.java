package com.wez.quartz.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "ss mm HH dd MM ? yyyy";

    /**
     * 时间戳转换成日期格式字符串
     * @param second 时间戳（精确到秒）
     * @return
     */
    /*public static String timeStampToDate(long second) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        return sdf.format(new Date(Long.valueOf(second+"000")));
    }*/

    /**
     * 日期格式字符串转换成时间戳
     * @param date 字符串日期，yyyy-MM-dd HH:mm:ss
     * @return
     */
    /*public static String dateToTimeStamp(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        return String.valueOf(sdf.parse(date).getTime()/1000);
    }*/

    /***
     * 日期转cron表达式,eg.  "0 06 10 15 1 ? 2014"
     * @param date 时间
     * @return
     */
    public static String getCron(Date date) {
        return formatDateByPattern(date, DATE_FORMAT);
    }

    /***
     * cron表达式转日期
     * @param cron cron表达式 cron表达式仅限于周为*
     * @return
     */
    public static Date getDate(String cron) throws ParseException {
        return parseStringToDate(cron, DATE_FORMAT);
    }

    /***
     * 格式化日期
     * @param date
     * @param dateFormat : e.g:yyyy-MM-dd HH:mm:ss
     * @return
     */
    private static String formatDateByPattern(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /**
     * cron表达式转日期
     * @param cron
     * @param dateFormat
     * @return
     * @throws ParseException
     */
    private static Date parseStringToDate(String cron, String dateFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = null;
        if (cron != null) {
            date = sdf.parse(cron);
        }
        return date;
    }

    public static void main(String[] args) throws ParseException {
//        long second = System.currentTimeMillis()/1000;
//        System.out.println("current: " + second);
//
//        String a = timeStampToDate(second);
//        System.out.println(a);
//
//        String b = dateToTimeStamp(a);
//        System.out.println(b);

        System.out.println(getCron(new Date()));

    }

}
