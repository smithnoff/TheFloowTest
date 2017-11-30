package com.cesarsmith.thefloowtest.ui.view.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Softandnet on 29/11/2017.
 */

public class TimeDateUtils {


    public static String getStartAndEndTime() {
        String localTime = "";
        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm a");
        date.setTimeZone(TimeZone.getTimeZone("GMT-4:00"));

        localTime = date.format(currentLocalTime);

        return localTime;

    }

    public static String getDayWeek() {


        return Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.UK);
    }
    public static String getCurrentDate() {


        return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }


    public static String getTotalTime(String start, String end) {
        return "";
    }


}
