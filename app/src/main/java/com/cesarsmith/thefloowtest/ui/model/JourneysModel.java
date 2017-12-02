package com.cesarsmith.thefloowtest.ui.model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;

import com.cesarsmith.thefloowtest.background.database.implementations.DBManager;
import com.cesarsmith.thefloowtest.background.pojos.Journey;
import com.cesarsmith.thefloowtest.ui.presenter.callbacks.JourneysCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Softandnet on 29/11/2017.
 */

public class JourneysModel implements JourneysCallback.Model {
    JourneysCallback.Presenter presenter;
    DBManager manager;

    public JourneysModel(JourneysCallback.Presenter presenter) {
        this.presenter = presenter;

    }

    @Override
    public void loadJourneys(Activity activity) {
        manager = new DBManager(activity);
        presenter.showResults(manager.getUserJourneys());


    }


}
