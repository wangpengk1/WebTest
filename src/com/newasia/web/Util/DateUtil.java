package com.newasia.web.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{
    public static String curDate(String pattern)
    {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(System.currentTimeMillis()));
    }
}
