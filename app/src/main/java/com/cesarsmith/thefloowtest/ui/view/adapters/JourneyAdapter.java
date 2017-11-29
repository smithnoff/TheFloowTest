package com.cesarsmith.thefloowtest.ui.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cesarsmith.thefloowtest.R;
import com.cesarsmith.thefloowtest.background.pojos.Journey;

import java.util.List;

/**
 * Created by Softandnet on 27/11/2017.
 */

public class JourneyAdapter extends RecyclerView.Adapter<JourneyAdapter.JourneyViewHolder> {

    Context context;

    public JourneyAdapter(Context context, List<Journey> journeyList) {
        this.context = context;
        this.journeyList = journeyList;
    }

    List<Journey> journeyList;


    @Override
    public JourneyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.item_journey_details,parent,false);
        return new JourneyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JourneyViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return journeyList.size();
    }

    class JourneyViewHolder extends RecyclerView.ViewHolder {

        public JourneyViewHolder(View itemView) {
            super(itemView);
        }
    }
}