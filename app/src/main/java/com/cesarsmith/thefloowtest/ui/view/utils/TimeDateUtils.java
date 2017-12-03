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
/*Class which returns all time and date process*/
public class TimeDateUtils {

    static Long startTime,totalTime;


//retur a formated star and end string to show in list journey items
    public static String getStartAndEndTime() {
        String localTime ;
        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm a");
        date.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getDisplayName()));
        localTime = date.format(currentLocalTime);

        return localTime;

    }
    //return day of the week name of the journey
    public static String getDayWeek() {return Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.UK);}

    //returns day month and year in a string format
    public static String getCurrentDate() {return new SimpleDateFormat("dd-MM-yyyy").format(new Date());}

//save the start time of the journey to calculate total time
    public static void startTimer(){startTime=System.currentTimeMillis();}

    //calculate the total time of the journey and return a string with a format hh:mm:ss
    public static String stopTimer(){
        totalTime=System.currentTimeMillis()-startTime;

        return    String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(totalTime), TimeUnit.MILLISECONDS.toMinutes(totalTime) % TimeUnit.HOURS.toMinutes(1), TimeUnit.MILLISECONDS.toSeconds(totalTime) % TimeUnit.MINUTES.toSeconds(1));

    }


}
