package com.knight.asus_nb.accounting_app.Util;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static String FormatedDay() {
        Date date = new Date();
        SimpleDateFormat SDF1 = new SimpleDateFormat("yyyy-MM-dd");
        String today = SDF1.format(date);
        return today;
    }

    public static String FormateTime() {
        Date date = new Date();
        SimpleDateFormat SDF2 = new SimpleDateFormat("HH:mm:ss");
        String nowtime = SDF2.format(date);
        return nowtime;
    }

    public static String Formateweek(String day) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date formatdate = null;
        try {
            formatdate = format.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat weekdate = new SimpleDateFormat("EEEE");
        return weekdate.format(formatdate);

    }

}
