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
        public void getNetworkStatus(Activity activity);
    }
    interface Model{
        public void getNetworkStatus(Activity activity);

    }

}
