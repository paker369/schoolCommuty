package com.dev.common.utils;

import java.util.Calendar;

/**
 * @author long.guo
 * @since 1/26/21
 */
public class TimeUtils {
    public static int getDaysByYearMonth(int year,int month){
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        return a.get(Calendar.DATE);
    }
}
