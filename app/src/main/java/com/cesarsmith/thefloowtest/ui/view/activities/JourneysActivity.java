package com.cesarsmith.thefloowtest.ui.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cesarsmith.thefloowtest.R;
import com.cesarsmith.thefloowtest.background.databases.pojos.Journey;
import com.cesarsmith.thefloowtest.ui.view.adapters.JourneyAdapter;

import java.util.ArrayList;
import java.util.List;

public class JourneysActivity extends AppCompatActivity {
       RecyclerView recyclerView;
       List<Journey>journeyList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journeys);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_journeys);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        journeyList=new ArrayList<>();
        journeyList.add(new Journey());
        journeyList.add(new Journey());
        journeyList.add(new Journey());
        journeyList.add(new Journey());
        journeyList.add(new Journey());
        journeyList.add(new Journey());
        journeyList.add(new Journey());
        recyclerView.setAdapter(new JourneyAdapter(this,journeyList));
        recyclerView.getAdapter().notifyDataSetChanged();





    }
}
