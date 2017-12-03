package com.cesarsmith.thefloowtest.ui.view.utils;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cesarsmith.thefloowtest.R;
import com.cesarsmith.thefloowtest.background.database.implementations.DBManager;
import com.cesarsmith.thefloowtest.background.pojos.Journey;
import com.cesarsmith.thefloowtest.background.pojos.Place;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;
import java.util.UUID;

/**
 * Created by Softandnet on 29/11/2017.
 */
/*this class is to created custom user dialogs*/
public class CustomDialogs {

    public static void saveJourneyDialog(final Activity activity, final Journey journey) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_save_journey);
        //setting a 90% width for the dialog
        dialog.getWindow().setLayout((int) (getScreenWidth(activity) * .9), ViewGroup.LayoutParams.WRAP_CONTENT);
        final DBManager manager = new DBManager(activity);
        final Spinner dialogSpinnerPlaces = (Spinner) dialog.findViewById(R.id.dialog_spinner_places);
        final TextInputEditText dialogNewPlace = (TextInputEditText) dialog.findViewById(R.id.dialog_new_place);
        TextView dialogTitle = (TextView) dialog.findViewById(R.id.dialog_title);
        TextInputLayout placesContainer = (TextInputLayout) dialog.findViewById(R.id.places_container);
        Button dialogSaveBt = (Button) dialog.findViewById(R.id.dialog_save_bt);
        Button dialogDontSaveBt = (Button) dialog.findViewById(R.id.dialog_dont_save_bt);


        //if there is not registered places hide the spinner layout
        if (manager.getUserPlaces().size() == 0)
            placesContainer.setVisibility(View.INVISIBLE);
        else {
            placesContainer.setVisibility(View.VISIBLE);
            dialogSpinnerPlaces.setAdapter(new ArrayAdapter<Place>(activity, R.layout.support_simple_spinner_dropdown_item, manager.getUserPlaces()));
        }

//save button to save a new place and journey
        dialogSaveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dialogNewPlace.getText().toString().isEmpty() && dialogSpinnerPlaces.getSelectedItem() != null)
                    //setting place if there no new place typed by the user, if no spinner or text type default journey place is saved
                    journey.setPlace(dialogSpinnerPlaces.getSelectedItem().toString());
                else {
                    //saving the new place typed by the user
                    journey.setPlace(dialogNewPlace.getText().toString());
                    //creating a custom id for the user place
                    manager.insertPlace(UUID.randomUUID().toString(), dialogNewPlace.getText().toString());
                }
                //inserting new journey
                manager.insertJourney(journey);
                dialog.dismiss();
            }
        });
        dialogDontSaveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    //calculates and returns the screen width
    public static int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }


    //custom dialog which show the map journey preview in the journey list
    public static void dialogMapJourney(final Activity activity, final Journey journey) {
        Button dialogClose;
        TextView titlePlace;
        TextView journeyStart;
        TextView journeyEnd;
        TextView journeyTotal;
        TextView journeyDistance;
        TextView journeySpeed;
        TextView journeyStoppedTime;

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_journey_map);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        MapsInitializer.initialize(activity);

        dialogClose = (Button) dialog.findViewById(R.id.dialog_close);
        titlePlace = (TextView) dialog.findViewById(R.id.title_place);
        journeyStart = (TextView) dialog.findViewById(R.id.journey_start);
        journeyEnd = (TextView) dialog.findViewById(R.id.journey_end);
        journeyTotal = (TextView) dialog.findViewById(R.id.journey_total);
        journeyDistance = (TextView) dialog.findViewById(R.id.journey_distance);
        journeySpeed = (TextView) dialog.findViewById(R.id.journey_speed);
        journeyStoppedTime = (TextView) dialog.findViewById(R.id.journey_stopped_time);
        MapView mMapView = (MapView) dialog.findViewById(R.id.map_journey_view);
        mMapView.onCreate(dialog.onSaveInstanceState());
        mMapView.onResume();

        titlePlace.setText(journey.getPlace());
        journeyStart.setText(journey.getStartTime());
        journeyEnd.setText(journey.getEndTime());
        journeyTotal.setText(journey.getTotalTime());
        //setting the total distance calling DistanceTimeUtils
        journeyDistance.setText(DistanceTimeUtils.getDistance(journey.getTrack()));
        //setting the speed calling DistanceTimeUtils
        journeySpeed.setText(DistanceTimeUtils.getSpeed(journey.getTotalTime(), journeyDistance.getText().toString()) + "Km/h");
//adding km word after using distance to calculate speed
        journeyDistance.setText(journeyDistance.getText().toString() + "Km");
        //setting the stopped time calling DistanceTimeUtils
        journeyStoppedTime.setText(DistanceTimeUtils.getStopTime(journey.getTrack()) + "min");

        //loading map view async
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                GoogleMap mMap;
                mMap = googleMap;
                MarkerOptions markerOptions = new MarkerOptions();
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.clear();
                //saving last location to add marker
                int lastPoint = journey.getTrack().getPoints().size() - 1;
                LatLng userPositions = new LatLng(journey.getTrack().getPoints().get(0).latitude, journey.getTrack().getPoints().get(0).longitude);
                //adding a marker in the start position
                markerOptions.position(userPositions).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title("Start Point");
                mMap.addMarker(markerOptions);
                //adding a marker in the end position
                userPositions = new LatLng(journey.getTrack().getPoints().get(lastPoint).latitude, journey.getTrack().getPoints().get(lastPoint).longitude);
                markerOptions.position(userPositions).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title("End Point");
                mMap.addMarker(markerOptions);

                mMap.getUiSettings().setZoomControlsEnabled(true);
                //drawing map polyline
                mMap.addPolyline(journey.getTrack().width(4).geodesic(true).color(Color.MAGENTA));
                //calling method which set a correct zoom to see full track on map
                getCorrectZoom(journey.getTrack(), mMap);
            }
        });
        dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });
        dialog.show();

    }
    //method returns a correct zoom to fit the full track journey in the map view
    private static void getCorrectZoom(final PolylineOptions route, final GoogleMap map) {
        // route is instance of PolylineOptions
         List<LatLng> points = route.getPoints();
         //builder of all location points
        final LatLngBounds.Builder bc = new LatLngBounds.Builder();

        for (LatLng item : points) {
            bc.include(item);
        }
        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                //when the process is done update the camera zoom
                map.moveCamera(CameraUpdateFactory.newLatLngBounds(bc.build(), 50));
            }
        });

    }
//dialog to request android location permissions
    public static void errorDialog(final Activity activity, String msg) {
        final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Warning!");
        builder.setMessage(msg);
        builder.setNeutralButton("Allow", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                activity.recreate();

            }
        }).setNegativeButton("Deny", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finish();
            }
        });
    }



}


