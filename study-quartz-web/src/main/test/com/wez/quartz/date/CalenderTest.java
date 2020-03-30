package com.wez.quartz.date;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalenderTest {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void test1() {
        Calendar calender = Calendar.getInstance();
        calender.setTime(new Date());

        calender.set(Calendar.MINUTE, calender.get(Calendar.MINUTE) - 5);
        System.out.println(sdf.format(calender.getTime()));
    }

}
