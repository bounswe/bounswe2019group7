package com.eyetrade.backend.utils;

import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
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
}
