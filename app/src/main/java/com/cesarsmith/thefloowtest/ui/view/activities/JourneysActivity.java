package com.cesarsmith.thefloowtest.ui.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cesarsmith.thefloowtest.R;
import com.cesarsmith.thefloowtest.background.pojos.Journey;
import com.cesarsmith.thefloowtest.ui.presenter.callbacks.JourneysCallback;
import com.cesarsmith.thefloowtest.ui.presenter.interactors.JourneysPresenter;
import com.cesarsmith.thefloowtest.ui.view.adapters.JourneyAdapter;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class JourneysActivity extends AppCompatActivity implements JourneysCallback.View{
       RecyclerView recyclerView;
       List<Journey>journeyList;
       JourneysCallback.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journeys);
        presenter=new JourneysPresenter(this);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_journeys);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        journeyList=new ArrayList<>();
        recyclerView.setAdapter(new JourneyAdapter(this,journeyList));
        recyclerView.getAdapter().notifyDataSetChanged();
        presenter.loadJourneys(this);



    }

    @Override
    public void showResults(List<Journey> journeyList) {
       this.journeyList=journeyList;
        recyclerView.setAdapter(new JourneyAdapter(this,journeyList));
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showErrors() {

    }
}
