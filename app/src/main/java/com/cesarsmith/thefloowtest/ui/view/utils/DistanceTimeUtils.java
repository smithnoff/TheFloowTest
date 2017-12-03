package com.cesarsmith.thefloowtest.ui.view.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.SphericalUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Softandnet on 1/12/2017.
 */
/*this class calculates all the extra info for the view map dialog*/
public class DistanceTimeUtils {

    //returns the total distance in the user journey with a GoogleMaps util
    public static String getDistance(PolylineOptions options) {
        //the SphericalUtil.computeDistanceBetween takes a star location and end location and calculates total distance in meters and passed to Kilometers this result
        // the result is send to doubleToBD
        return String.valueOf(doubleToBD(SphericalUtil.computeDistanceBetween(options.getPoints().get(0), options.getPoints().get(options.getPoints().size() - 1)) / 1000));

    }

    //method calculate the average speed
    public static String getSpeed(String totalTime, String totalDistance) {
        double speed = 0;
        //split the total time string and transform to hours
        String[] totalHours = totalTime.split(":");
        double timeInteger = Double.parseDouble(totalHours[0]) + (Double.parseDouble(totalHours[1]) / 60) + (Double.parseDouble(totalHours[2]) / 3600);

        //check the time to avoid divide by zero and using speed =distance / time to calculate average speed
        if (timeInteger != 0)
            speed = Double.parseDouble(totalDistance) / timeInteger;
        // the result is send to doubleToBD
        return String.valueOf(doubleToBD(speed));

    }

    //returns the user average time stopped in the journey
    public static String getStopTime(PolylineOptions options) {
        double stopTime = 0;
        //this loop take every track location and compare if is equals to the previous if true add 5 sec
        //5 sec is the max location request time for the listener
        for (int i = 1; i < options.getPoints().size(); i++) {
            if (options.getPoints().get(i - 1).latitude == options.getPoints().get(i).latitude && options.getPoints().get(i - 1).longitude == options.getPoints().get(i).longitude)
                stopTime += 5;
        }
//return the time in minutes and send to  doubleToBD method
        return String.valueOf(doubleToBD(stopTime / 60));
    }


/* this method is to retrieve a double with max 3 decimals rounded*/
    private static BigDecimal doubleToBD(Double value) {
        BigDecimal meters = new BigDecimal(value);
        meters = meters.setScale(3, RoundingMode.HALF_UP);
        return meters;

    }


}
