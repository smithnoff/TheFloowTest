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

public class DistanceTimeUtils {


    public static String getDistance(PolylineOptions options) {

        return String.valueOf(doubleToBD(SphericalUtil.computeDistanceBetween(options.getPoints().get(0), options.getPoints().get(options.getPoints().size() - 1)) / 1000));

    }

    public static String getSpeed(String totalTime, String totalDistance) {
        double speed = 0;
        String[] totalHours = totalTime.split(":");
        int timeInteger = Integer.parseInt(totalHours[0]) + (Integer.parseInt(totalHours[1]) * 60) + (Integer.parseInt(totalHours[2]) * 3600);
        Log.e("estoy aca", "Totaldis: " + Double.parseDouble(totalDistance));

        if (timeInteger != 0)
            speed = Double.parseDouble(totalDistance) / timeInteger;

        return String.valueOf(doubleToBD(speed));

    }

    public static String getStopTime(PolylineOptions options) {
        int stopTime = 1;
        for (int i = 1; i < options.getPoints().size(); i++) {
            if (options.getPoints().get(i - 1).latitude == options.getPoints().get(i).latitude && options.getPoints().get(i - 1).longitude == options.getPoints().get(i).longitude)
                stopTime *= 5;
        }

        return String.valueOf(stopTime);
    }

    private static BigDecimal doubleToBD(Double value) {

        BigDecimal meters = new BigDecimal(value);
        meters = meters.setScale(4, RoundingMode.HALF_UP);
        return meters;

    }




}
