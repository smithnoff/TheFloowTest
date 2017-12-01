package com.cesarsmith.thefloowtest.ui.view.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.UUID;

/**
 * Created by Softandnet on 29/11/2017.
 */

public class CustomDialogs {

    public static void saveJourneyDialog(final Activity activity, final Journey journey) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_save_journey);
        dialog.getWindow().setLayout((int) (getScreenWidth(activity) * .9), ViewGroup.LayoutParams.WRAP_CONTENT);
        final DBManager manager = new DBManager(activity);
        final Spinner dialogSpinnerPlaces = (Spinner) dialog.findViewById(R.id.dialog_spinner_places);
        final TextInputEditText dialogNewPlace = (TextInputEditText) dialog.findViewById(R.id.dialog_new_place);
        TextView dialogTitle = (TextView) dialog.findViewById(R.id.dialog_title);
        TextInputLayout placesContainer = (TextInputLayout) dialog.findViewById(R.id.places_container);
        Button dialogSaveBt = (Button) dialog.findViewById(R.id.dialog_save_bt);
        Button dialogDontSaveBt = (Button) dialog.findViewById(R.id.dialog_dont_save_bt);

        if (manager.getUserPlaces().size() == 0)
            placesContainer.setVisibility(View.INVISIBLE);
        else {
            placesContainer.setVisibility(View.VISIBLE);
            dialogSpinnerPlaces.setAdapter(new ArrayAdapter<Place>(activity, R.layout.support_simple_spinner_dropdown_item, manager.getUserPlaces()));
        }


        dialogSaveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dialogNewPlace.getText().toString().isEmpty())
                    journey.setPlace(dialogSpinnerPlaces.getSelectedItem().toString());
                else {
                    journey.setPlace(dialogNewPlace.getText().toString());
                    manager.insertPlace(UUID.randomUUID().toString(), dialogNewPlace.getText().toString());
                }
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

        dialog.show();
    }

    public static int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }

    public static void dialogMapJourney(Activity activity, final Journey journey) {
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

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                GoogleMap mMap;
                mMap = googleMap;
                mMap.clear();
                MarkerOptions markerOptions = new MarkerOptions();
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                int lastPoint = journey.getTrack().getPoints().size() - 1;
                LatLng userPositions = new LatLng(journey.getTrack().getPoints().get(0).latitude, journey.getTrack().getPoints().get(0).longitude);
                markerOptions.position(userPositions).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title("Start Point");
                mMap.addMarker(markerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userPositions, 15));
                userPositions = new LatLng(journey.getTrack().getPoints().get(lastPoint).latitude, journey.getTrack().getPoints().get(lastPoint).longitude);
                markerOptions.position(userPositions).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title("End Point");
                mMap.addMarker(markerOptions);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.addPolyline(journey.getTrack().width(4).geodesic(true).color(Color.MAGENTA));
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

}


