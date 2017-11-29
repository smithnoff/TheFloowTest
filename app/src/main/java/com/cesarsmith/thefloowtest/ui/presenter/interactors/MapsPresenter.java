package com.cesarsmith.thefloowtest.ui.presenter.interactors;

import android.app.Activity;

import com.cesarsmith.thefloowtest.ui.model.MapsModel;
import com.cesarsmith.thefloowtest.ui.presenter.callbacks.MapsCallback;

/**
 * Created by Softandnet on 27/11/2017.
 */

public class MapsPresenter implements MapsCallback.Presenter {
    MapsCallback.View view;
    MapsCallback.Model model;

    public MapsPresenter(MapsCallback.View view) {
        this.view = view;
        model=new MapsModel(this);
    }
    @Override
    public void showResults() {
        if (view!=null){
            view.showResults();
        }

    }

    @Override
    public void showErrors() {
        if (view!=null){
         view.showErrors();
        }
    }


    @Override
    public void setTrackingEnabled(Activity activity) {
    model.setTrackingEnabled(activity);
    }

    @Override
    public void setTrackingDisabled(Activity activity) {
        model.setTrackingDisabled(activity);
    }
}