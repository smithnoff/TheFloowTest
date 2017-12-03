package com.cesarsmith.thefloowtest.background.services;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.cesarsmith.thefloowtest.ui.view.activities.MapsActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.security.Provider;

import static android.content.ContentValues.TAG;

/**
 * Created by Softandnet on 28/11/2017.
 */
/*This service make the journey track listening the location*/
public class TrackerService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private GoogleApiClient mLocationClient;
    LocationRequest mLocationRequest;

    /*start service method, here the google api client and loacation request configuration is defined*/
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //google api client configuration
        mLocationClient = new GoogleApiClient.Builder(TrackerService.this)
                .addApi(LocationServices.API).addConnectionCallbacks(TrackerService.this)
                .addOnConnectionFailedListener(TrackerService.this).build();

        mLocationRequest = new LocationRequest();
        // set the interval of the location listener this case is 6 sec
        mLocationRequest.setInterval(5000);
        //setting priority of the listener in this case i choose PRIORITY_BALANCED_POWER_ACCURACY to get a good polyline using balanced power
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //retrieve a new location in 3 sec if available
        mLocationRequest.setFastestInterval(3000);
        //start onconnect method
        mLocationClient.connect();
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        Toast.makeText(this, "Connection failed", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onConnected(Bundle arg0) {
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
     //Creation of location request to start listening in onLocationChanged method
        LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient, mLocationRequest, this);


    }

    @Override
    public void onConnectionSuspended(int arg0) {
        Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
    }
    //method called every user location change
    @Override
    public void onLocationChanged(Location location) {
        //intent action to send configuartion
        Intent intent = new Intent("com.cesarsmith.thefloowtest.TRACK_RRCEIVER");
        //bundle to pass location object
        Bundle bundle = new Bundle();
        bundle.putParcelable("location", location);
        intent.putExtra("bundle", bundle);
        //sending location to Broadcast reciver in MapsActivity
        sendBroadcast(intent);
        Log.e(TAG, "onLocationChanged: " + location.getLatitude() + ", " + location.getLatitude());
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        //stoping location listener and google api client to save battery
        LocationServices.FusedLocationApi.removeLocationUpdates(mLocationClient, this);
        mLocationClient.disconnect();
        Log.e(TAG, "onDestroy: dead listener" );

    }
}