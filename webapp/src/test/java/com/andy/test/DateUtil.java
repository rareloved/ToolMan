package com.andy.test;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhangshouzheng on 2016/10/26.
 */
public class DateUtil {
    //获取昨天
    protected Date getPreDayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    //获取今天
    protected Date getToDayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    @Test
    public void testDate(){
        System.out.println(this.getPreDayDate());
    }
}
