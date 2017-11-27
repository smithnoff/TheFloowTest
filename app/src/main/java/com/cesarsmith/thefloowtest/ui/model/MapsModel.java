package com.cesarsmith.thefloowtest.ui.model;

import android.app.Activity;

import com.cesarsmith.thefloowtest.ui.presenter.callbacks.MapsCallback;

/**
 * Created by Softandnet on 27/11/2017.
 */

public class MapsModel implements MapsCallback.Model {

    MapsCallback.Presenter presenter;

    public MapsModel(MapsCallback.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getNetworkStatus(Activity activity) {

    }
}
