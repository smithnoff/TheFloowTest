package com.cesarsmith.thefloowtest.ui.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cesarsmith.thefloowtest.R;
import com.cesarsmith.thefloowtest.background.pojos.Journey;
import com.cesarsmith.thefloowtest.ui.view.utils.CustomDialogs;

import java.util.List;
import java.util.logging.Handler;

/**
 * Created by Softandnet on 27/11/2017.
 */
/*Journey list item class adapter for recyclerview in User journeys Module*/
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
    public void onBindViewHolder(final JourneyViewHolder holder, int position) {

       holder.journeyPlace.setText(journeyList.get(position).getPlace());
       holder.journeyDate.setText(journeyList.get(position).getDayWeek()+": "+journeyList.get(position).getDate());
       holder.journeyStart.setText(journeyList.get(position).getStartTime());
       holder.journeyEnd.setText(journeyList.get(position).getEndTime());
       holder.journeyTotal.setText(journeyList.get(position).getTotalTime());


       //onclick listener to start dialog with map journey view
       holder.journeyViewMap.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               android.os.Handler handler=new android.os.Handler();
               handler.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       CustomDialogs.dialogMapJourney((Activity)context,journeyList.get(holder.getAdapterPosition()));

                   }
               },150);


           }
       });

    }

    @Override
    public int getItemCount() {
        return journeyList.size();
    }


    /*View holder class for item journey*/
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
