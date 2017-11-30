package com.cesarsmith.thefloowtest.ui.view.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by Softandnet on 29/11/2017.
 */

public class TimeDateUtils {

    static Long startTime,totalTime;



    public static String getStartAndEndTime() {
        String localTime ;
        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm a");
        date.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getDisplayName()));

        localTime = date.format(currentLocalTime);

        return localTime;

    }

    public static String getDayWeek() {


        return Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.UK);
    }
    public static String getCurrentDate() {


        return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }


    public static void startTimer(){

          startTime=System.currentTimeMillis();
    }
    public static String stopTimer(){
        totalTime=System.currentTimeMillis()-startTime;

        return    String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(totalTime), TimeUnit.MILLISECONDS.toMinutes(totalTime) % TimeUnit.HOURS.toMinutes(1), TimeUnit.MILLISECONDS.toSeconds(totalTime) % TimeUnit.MINUTES.toSeconds(1));

    }


}
