package com.cesarsmith.thefloowtest.ui.view.activities;

import android.Manifest;
import android.app.Activity;
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
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toolbar;

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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


/* This class handle user map tracking function*/

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        MapsCallback.View {

    private GoogleMap mMap;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    MapsCallback.Presenter presenter;
    Switch trackSwitch;
    Polyline userTrack;
    PolylineOptions polylineOptions;
    String startTime = "", endTime = "";
    DBManager manager;
    ImageButton backButton;
    boolean isReceiverOn;
    private MarkerOptions markerOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        presenter = new MapsPresenter(this);

        manager = new DBManager(this);

        backButton = (ImageButton) findViewById(R.id.toolbar_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

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
                CustomDialogs.errorDialog(this, getString(R.string.location_permission));
            }
        } else {
            //Location Permission already granted
            mMap.setMyLocationEnabled(true);
        }
        inicializeComponents();
    }

    /*Method to inicialize map marker and tracking switch*/
    public void inicializeComponents() {
        markerOptions = new MarkerOptions();
        trackSwitch = (Switch) findViewById(R.id.track_switch);
        polylineOptions = new PolylineOptions().width(4).color(Color.MAGENTA).geodesic(true);

        trackSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean trackOn) {
                if (trackOn) {
                    startTracking();
                } else {
                    stopTracking();
                }

            }
        });


    }

    /* Method to start Broadcast receiver and start tracking service*/
    public void startTracking() {
        //flag to know if receiver is registered or not to avoid Exception when onDestroy or onBackPressed method is called
        isReceiverOn = true;
        //clear map polylines and markers
        mMap.clear();
        //set start time to calculate total time
        TimeDateUtils.startTimer();
        //get start hour with format to save in the journey object
        startTime = TimeDateUtils.getStartAndEndTime();
        //start to draw map
        trackingMethod(mMap.getMyLocation());
        //calling to presenter who start
        presenter.setTrackingEnabled(MapsActivity.this);
        //register broadcast receiver
        registerReceiver(receiver, new IntentFilter("com.cesarsmith.thefloowtest.TRACK_RRCEIVER"));
    }

    /* Method to stop Broadcast receiver and stop tracking service and draw the final map*/
    public void stopTracking() {
        //setting false flag broadcast receiver is not registered
        isReceiverOn = false;
        //unregister broadcast receiver
        unregisterReceiver(receiver);
        //calling to presenter who start
        presenter.setTrackingDisabled(MapsActivity.this);
        //Draing the final map
        drawMap(polylineOptions);
        //Adding Start and End markers
        //start marker
        mMap.addMarker(markerOptions.position(polylineOptions.getPoints().get(0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        //end marker
        mMap.addMarker(markerOptions.position(polylineOptions.getPoints().get(polylineOptions.getPoints().size() - 1)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        //get end hour with format to save in the journey object
        endTime = TimeDateUtils.getStartAndEndTime();
        //custom dialog which saves or not the user journey
        CustomDialogs.saveJourneyDialog(MapsActivity.this,
                new Journey(startTime, endTime, TimeDateUtils.getCurrentDate(), TimeDateUtils.stopTimer(), TimeDateUtils.getDayWeek(), "No Place", polylineOptions, ""));
        //delete polyline
        polylineOptions = null;


    }

//BroadcastReceiver to receive location updates and draw on real time user moves
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location = intent.getBundleExtra("bundle").getParcelable("location");

            trackingMethod(location);
        }
    };
 //Method to draw map
    public void trackingMethod(Location location) {
        //last user position
        mLastLocation = location;
        //removing marker
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //getting latitude and longitude to set new marker and add to polyline
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        //adding marker in current user location
        mCurrLocationMarker = mMap.addMarker(markerOptions.position(latLng));
        //moving map to current user position
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19));
        //add new polyline to track
        polylineOptions.add(latLng);
        //call to Method  which draw a new map
         drawMap(polylineOptions);

    }

//Method draw a new map clearing and re drawing the map
    public void drawMap(PolylineOptions options) {
        mMap.clear();
        mMap.addPolyline(options);

    }

//Callback method to request Location permissions
    @Override
    public void showErrors(String msg) {
        CustomDialogs.errorDialog(this, msg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //if there is a track, call a drawMap method when user resume the app
        if (polylineOptions != null)
            drawMap(polylineOptions);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //if there is a receiver registered the app unregister when the app finish
        if (isReceiverOn)
            unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed() {
        //if receiver is off move to previous activity else send the app to background and dont interrup tracking service
        if (!isReceiverOn)
            super.onBackPressed();
        else
            moveTaskToBack(true);
    }
}