package com.cesarsmith.thefloowtest.ui.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

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
       TextView noJourneysTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
        setContentView(R.layout.activity_journeys);
        presenter=new JourneysPresenter(this);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_journeys);
        noJourneysTv=(TextView)findViewById(R.id.no_journey_tv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        journeyList=new ArrayList<>();
        recyclerView.setAdapter(new JourneyAdapter(this,journeyList));
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.setVisibility(View.GONE);
        noJourneysTv.setVisibility(View.VISIBLE);
        presenter.loadJourneys(this);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }

    @Override
    public void showResults(List<Journey> journeyList) {
       this.journeyList=journeyList;
       if (journeyList.size()>0){
           recyclerView.setVisibility(View.VISIBLE);
           noJourneysTv.setVisibility(View.GONE);
       }
        recyclerView.setAdapter(new JourneyAdapter(this,journeyList));
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showErrors() {

    }
}
