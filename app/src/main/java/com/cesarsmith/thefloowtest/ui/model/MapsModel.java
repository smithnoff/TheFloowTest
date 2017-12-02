package com.cesarsmith.thefloowtest.ui.model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.cesarsmith.thefloowtest.background.pojos.Journey;
import com.cesarsmith.thefloowtest.background.services.TrackerService;
import com.cesarsmith.thefloowtest.ui.presenter.callbacks.MapsCallback;
import com.cesarsmith.thefloowtest.ui.view.activities.MapsActivity;
import com.cesarsmith.thefloowtest.ui.view.utils.CustomDialogs;


/**
 * Created by Softandnet on 27/11/2017.
 */

public class MapsModel implements MapsCallback.Model {

    MapsCallback.Presenter presenter;


    public MapsModel(MapsCallback.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setTrackingEnabled(Activity activity) {


        if (ActivityCompat.checkSelfPermission(activity,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            activity.startService(new Intent(activity, TrackerService.class));
        }else{
            presenter.showErrors("Location permissions needed for this feature, allows the app to location access?");
        }

    }

    @Override
    public void setTrackingDisabled(Activity activity) {activity.stopService(new Intent(activity,TrackerService.class));}


}
