package com.cesarsmith.thefloowtest.ui.presenter.callbacks;

import android.app.Activity;

import com.cesarsmith.thefloowtest.background.pojos.Journey;
import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Softandnet on 27/11/2017.
 */

public interface MapsCallback {

    interface View{
         void showErrors(String msg);

    }
    interface Presenter{
         void showErrors(String msg);
         void setTrackingEnabled(Activity activity);
         void setTrackingDisabled(Activity activity);
    }
    interface Model{
         void setTrackingEnabled(Activity activity);
         void setTrackingDisabled(Activity activity);

    }

}
