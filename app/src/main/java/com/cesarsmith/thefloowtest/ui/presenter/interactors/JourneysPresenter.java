package com.cesarsmith.thefloowtest.ui.presenter.interactors;

import android.app.Activity;
import android.os.AsyncTask;

import com.cesarsmith.thefloowtest.background.pojos.Journey;
import com.cesarsmith.thefloowtest.ui.model.JourneysModel;
import com.cesarsmith.thefloowtest.ui.presenter.callbacks.JourneysCallback;

import java.util.List;

/**
 * Created by Softandnet on 29/11/2017.
 */
/*this class send to the view all model results*/
public class JourneysPresenter implements JourneysCallback.Presenter {

    JourneysCallback.View view;
    JourneysCallback.Model model;

    public JourneysPresenter(JourneysCallback.View view) {
        this.view = view;
        model = new JourneysModel(this);
    }



    @Override
    public void showResults(List<Journey> journeyList) {
        if (view!=null){
            view.showResults(journeyList);
        }
    }

    @Override
    public void showErrors() {

    }

    @Override
    public void loadJourneys(final Activity activity) {
        model.loadJourneys(activity);



    }
}
