package com.cesarsmith.thefloowtest.ui.view.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.cesarsmith.thefloowtest.background.database.implementations.DBManager;
import com.cesarsmith.thefloowtest.background.pojos.Journey;

/**
 * Created by Softandnet on 29/11/2017.
 */

public class CustomDialogs {

    public static void saveJourneyDialog(Activity activity, final Journey journey){
        final DBManager  manager=new DBManager(activity);

        AlertDialog.Builder dialog=new AlertDialog.Builder(activity);
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
        });
        dialog.create().show();
    }



}
