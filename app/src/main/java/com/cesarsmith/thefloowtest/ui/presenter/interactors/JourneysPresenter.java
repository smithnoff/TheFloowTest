package com.cesarsmith.thefloowtest.ui.presenter.interactors;

import android.app.Activity;

import com.cesarsmith.thefloowtest.background.pojos.Journey;
import com.cesarsmith.thefloowtest.ui.model.JourneysModel;
import com.cesarsmith.thefloowtest.ui.presenter.callbacks.JourneysCallback;

import java.util.List;

/**
 * Created by Softandnet on 29/11/2017.
 */

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
    public void loadJourneys(Activity activity) {
        model.loadJourneys(activity);
    }
}
