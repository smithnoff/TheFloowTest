package com.cesarsmith.thefloowtest.ui.presenter.callbacks;

import android.app.Activity;

import com.cesarsmith.thefloowtest.background.pojos.Journey;

import java.util.List;

/**
 * Created by Softandnet on 29/11/2017.
 */

public interface JourneysCallback {
    interface View{

         void showResults(List<Journey> journeyList);
         void showErrors();

    }
    interface Presenter{
         void showResults(List<Journey> journeyList);
         void showErrors();
         void loadJourneys(Activity activity);
    }
    interface Model{
         void loadJourneys(Activity activity);

    }
}
