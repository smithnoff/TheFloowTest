package com.cesarsmith.thefloowtest.ui.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cesarsmith.thefloowtest.R;
import com.cesarsmith.thefloowtest.background.pojos.Journey;
import com.cesarsmith.thefloowtest.ui.view.utils.CustomDialogs;

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
    public void onBindViewHolder(JourneyViewHolder holder, final int position) {
       holder.journeyPlace.setText(journeyList.get(position).getPlace());
       holder.journeyDate.setText("Date: "+journeyList.get(position).getDate());
       holder.journeyStart.setText(journeyList.get(position).getStartTime());
       holder.journeyEnd.setText(journeyList.get(position).getEndTime());
       holder.journeyTotal.setText(journeyList.get(position).getTotalTime());
       holder.journeyViewMap.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               CustomDialogs.dialogMapJourney((Activity)context,journeyList.get(position));
               Log.e("JOURNEY SELECTED", "onClick: "+position+" "+journeyList.get(position).getTrack() );

           }
       });


    }

    @Override
    public int getItemCount() {
        return journeyList.size();
    }

    class JourneyViewHolder extends RecyclerView.ViewHolder {
          TextView journeyPlace;
          TextView journeyDate;
          TextView journeyStart;
          TextView journeyEnd;
          TextView journeyTotal;
          Button journeyViewMap;

        public JourneyViewHolder(View itemView) {
            super(itemView);
            journeyPlace=itemView.findViewById(R.id.journey_place);
            journeyDate=itemView.findViewById(R.id.journey_date);
            journeyStart=itemView.findViewById(R.id.journey_start);
            journeyEnd=itemView.findViewById(R.id.journey_end);
            journeyTotal=itemView.findViewById(R.id.journey_total);
            journeyViewMap=itemView.findViewById(R.id.journey_map_bt);
        }
    }
}
