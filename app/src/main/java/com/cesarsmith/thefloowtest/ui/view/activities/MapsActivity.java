package com.cesarsmith.thefloowtest.ui.view.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.cesarsmith.thefloowtest.R;
import com.cesarsmith.thefloowtest.background.database.implementations.DBManager;
import com.cesarsmith.thefloowtest.background.pojos.Journey;
import com.cesarsmith.thefloowtest.ui.presenter.callbacks.MapsCallback;
import com.cesarsmith.thefloowtest.ui.presenter.interactors.MapsPresenter;
import com.cesarsmith.thefloowtest.ui.view.utils.CustomDialogs;
import com.cesarsmith.thefloowtest.ui.view.utils.TimeDateUtils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
       MapsCallback.View {

    private GoogleMap mMap;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    MapsCallback.Presenter presenter;
    Switch trackSwitch;
    PolylineOptions polylineOptions;
    String startTime="",endTime="";
    DBManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        presenter = new MapsPresenter(this);
        manager=new DBManager(this);
        trackSwitch = (Switch) findViewById(R.id.track_switch);
        trackSwitch.setOnCheckedChangeListener(switchListener);
        polylineOptions=new PolylineOptions().width(3).color(Color.MAGENTA).geodesic(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                mMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }

    }



    CompoundButton.OnCheckedChangeListener switchListener=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b){
                startTime= TimeDateUtils.getStartAndEndTime();
                trackingMethod(mMap.getMyLocation());
                presenter.setTrackingEnabled(MapsActivity.this);
                registerReceiver(receiver,new IntentFilter("com.cesarsmith.thefloowtest.TRACK_RRCEIVER"));
            }else {
                presenter.setTrackingDisabled(MapsActivity.this);
                unregisterReceiver(receiver);
                drawMap(polylineOptions);
               endTime= TimeDateUtils.getStartAndEndTime();
                CustomDialogs.saveJourneyDialog(MapsActivity.this,
                        new Journey(startTime,endTime,TimeDateUtils.getCurrentDate(),"18.0",TimeDateUtils.getDayWeek(),"Sheff River",polylineOptions,""));


            }
        }
    };
    public void trackingMethod(Location location){
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19));
        polylineOptions.add(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
       // drawMap(mLastLocation);
    }
    BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location=intent.getBundleExtra("bundle").getParcelable("location");
            trackingMethod(location);
        }
    };



     public void drawMap(PolylineOptions options){
         mMap.addPolyline(options);

     }
    @Override
    public void showResults() {

    }

    @Override
    public void showErrors() {

    }
}