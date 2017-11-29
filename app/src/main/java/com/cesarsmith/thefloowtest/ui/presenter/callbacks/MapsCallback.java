package com.cesarsmith.thefloowtest.ui.presenter.callbacks;

import android.app.Activity;

/**
 * Created by Softandnet on 27/11/2017.
 */

public interface MapsCallback {

    interface View{
        public void showResults();
        public void showErrors();

    }
    interface Presenter{
        public void showResults();
        public void showErrors();
        public void setTrackingEnabled(Activity activity);
        public void setTrackingDisabled(Activity activity);
    }
    interface Model{
        public void setTrackingEnabled(Activity activity);
        public void setTrackingDisabled(Activity activity);

    }

}
