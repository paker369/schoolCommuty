package com.dev.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author long.guo
 * @since 1/24/21
 */
public class DateUtil {
    public static String toTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日hh:mm");
        Date date = new Date(time);
        return format.format(date);
    }
}
