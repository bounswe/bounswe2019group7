package com.eyetrade.backend.utils;

import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Emir Gökdemir
 * on 9 Kas 2019
 */
@UtilityClass
public class DateUtils {

    public static String dateTimeFormatter(Date date, String newPattern){
        SimpleDateFormat formatter = new SimpleDateFormat(newPattern);
        return formatter.format(date);
    }

    public static Date getDateXDaysAgo(Integer days){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -days);
        return cal.getTime();
    }

    public static String getDateXDaysAgoWithFormat(Integer days, String pattern){
        return dateTimeFormatter(getDateXDaysAgo(days),pattern);
    }


}
