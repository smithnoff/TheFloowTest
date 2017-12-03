package com.cesarsmith.thefloowtest.ui.model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.cesarsmith.thefloowtest.background.database.implementations.DBManager;
import com.cesarsmith.thefloowtest.background.pojos.Journey;
import com.cesarsmith.thefloowtest.ui.presenter.callbacks.JourneysCallback;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Softandnet on 29/11/2017.
 */
/*Model class to manage journey list module async background task*/
public class JourneysModel implements JourneysCallback.Model {
    JourneysCallback.Presenter presenter;
    DBManager manager;

    public JourneysModel(JourneysCallback.Presenter presenter) {
        this.presenter = presenter;

    }
   //method call asyncTask  and execute database manager journey list retrieve
    @Override
    public void loadJourneys(Activity activity) {
        manager = new DBManager(activity);
        AsyncRetrieve asyncRetrieve = new AsyncRetrieve(manager, presenter);
        asyncRetrieve.execute();
    }


    private class AsyncRetrieve extends AsyncTask<Void, Void, List<Journey>> {
        JourneysCallback.Presenter presenter;
        DBManager manager;

        public AsyncRetrieve(DBManager manager, JourneysCallback.Presenter presenter) {
            this.presenter = presenter;
            this.manager = manager;
        }
    // start database journey list retrieve method
        @Override
        protected List<Journey> doInBackground(Void... voids) {
            return manager.getUserJourneys();
        }
    //send user journey list to presenter
        @Override
        protected void onPostExecute(List<Journey> journeyList) {
            presenter.showResults(journeyList);
        }
    }


}
