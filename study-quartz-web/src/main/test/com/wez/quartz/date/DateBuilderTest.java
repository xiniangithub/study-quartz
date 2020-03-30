package com.wez.quartz.date;

import org.junit.Test;
import org.quartz.DateBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateBuilderTest {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void test8() {
        // 多久后
        Date date = DateBuilder.futureDate(10, DateBuilder.IntervalUnit.MINUTE);
        System.out.println(sdf.format(date));
    }

    /**
     * 时区时间转换
     */
    @Test
    public void test7() {
        // 时区时间转换
        Date date = DateBuilder.translateTime(new Date(), TimeZone.getDefault(), TimeZone.getTimeZone("America/Los_Angeles"));

        System.out.println(sdf.format(date));
    }

    @Test
    public void test6() {
        System.out.println("某一时间的下一个给定秒的时间" + sdf.format(DateBuilder.nextGivenSecondDate(new Date(), 5)));
        System.out.println("某一时间的下一个给定分钟的时间" + sdf.format(DateBuilder.nextGivenMinuteDate(new Date(), 5)));
    }

    /**
     * 指定日期
     */
    @Test
    public void test5() {
        System.out.println("指定日期: " + sdf.format(DateBuilder.dateOf(01, 02, 03, 10, 12, 2019)));
    }

    /**
     * 前一时刻
     */
    @Test
    public void test4() {
        // 前一小时
        System.out.println("前一小时: " + sdf.format(DateBuilder.evenHourDateBefore(new Date())));
        // 前一分钟
        System.out.println("前一分钟: " + sdf.format(DateBuilder.evenMinuteDateBefore(new Date())));
        // 前一秒
        System.out.println("前一秒: " + sdf.format(DateBuilder.evenSecondDateBefore(new Date())));
    }

    /**
     * 下一时刻
     */
    @Test
    public void test3() {
        // 下一小时
        System.out.println("下一小时: " + sdf.format(DateBuilder.evenHourDate(new Date())));
        // 下一分钟
        System.out.println("下一分钟: " + sdf.format(DateBuilder.evenMinuteDate(new Date())));
        // 下一秒
        System.out.println("下一秒: " + sdf.format(DateBuilder.evenSecondDate(new Date())));

        // 当前时间的下一小时
        System.out.println("当前时间的下一小时: " + sdf.format(DateBuilder.evenHourDateAfterNow()));
        // 当前时间的下一分钟
        System.out.println("当前时间的下一分钟: " + sdf.format(DateBuilder.evenMinuteDateAfterNow()));
        // 当前时间的下一秒
        System.out.println("当前时间的下一秒: " + sdf.format(DateBuilder.evenSecondDateAfterNow()));

    }

    /**
     * 设置年、月、日
     */
    @Test
    public void test2() {
        Date date = DateBuilder.newDate()
                .inYear(2019) // 设置年
                .inMonth(1) // 设置月
                .onDay(20) // 设置日
                .build();
        System.out.println(sdf.format(date));
    }

    /**
     * 设置时、分、秒
     */
    @Test
    public void test1() {
//        Date date = DateBuilder.newDate()
//                .atHourOfDay(10) // 设置小时
//                .atMinute(19) // 设置分钟
//                .atSecond(30) // 设置秒
//                .build();

        Date date = DateBuilder.newDate()
                .atHourMinuteAndSecond(10, 19, 30) // 设置时、分、秒
                .build();
        System.out.println(sdf.format(date));
    }

}
