package com.cesarsmith.thefloowtest.ui.view.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.cesarsmith.thefloowtest.R;
import com.cesarsmith.thefloowtest.background.database.implementations.DBManager;
import com.cesarsmith.thefloowtest.background.pojos.Journey;
import com.cesarsmith.thefloowtest.background.pojos.Place;

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
        TextView dialogTitle = (TextView) dialog.findViewById(R.id.dialog_title);
        TextInputLayout placesContainer = (TextInputLayout) dialog.findViewById(R.id.places_container);
        final Spinner dialogSpinnerPlaces = (Spinner) dialog.findViewById(R.id.dialog_spinner_places);
        final TextInputEditText dialogNewPlace = (TextInputEditText) dialog.findViewById(R.id.dialog_new_place);
        Button dialogSaveBt = (Button) dialog.findViewById(R.id.dialog_save_bt);
        Button dialogDontSaveBt = (Button) dialog.findViewById(R.id.dialog_dont_save_bt);

         if (manager.getUserPlaces().size()==0)
             placesContainer.setVisibility(View.INVISIBLE);
         else{
             placesContainer.setVisibility(View.VISIBLE);
             dialogSpinnerPlaces.setAdapter(new ArrayAdapter<Place>(activity,R.layout.support_simple_spinner_dropdown_item,manager.getUserPlaces()));
         }


        dialogSaveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dialogNewPlace.getText().toString().isEmpty())
                    journey.setPlace(dialogSpinnerPlaces.getSelectedItem().toString());
                else {
                    journey.setPlace(dialogNewPlace.getText().toString());
                    manager.insertPlace(UUID.randomUUID().toString(),dialogNewPlace.getText().toString());
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

       /* AlertDialog.Builder dialog=new AlertDialog.Builder(activity);
        dialog.setTitle("Atention!!");
        dialog.setMessage("Do you want to save this journey?\nStart:"+journey.getStartTime());
        dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                     manager.insertJourney(journey);
            }
        }).setNegativeButton("Don't save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });*/
        dialog.show();
    }

    public static int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }


}
