package com.cesarsmith.thefloowtest.background.pojos;

import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by Softandnet on 27/11/2017.
 */
/*Jouney model data*/
public class Journey {

    private  String jId;
    private  String startTime;
    private  String endTime;
    private  String date;
    private  String totalTime;
    private  String dayWeek;
    private String place;
    private PolylineOptions track;



    public Journey(String startTime, String endTime, String date, String totalTime, String dayWeek, String place, PolylineOptions track, String jId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.totalTime = totalTime;
        this.dayWeek = dayWeek;
        this.place = place;
        this.track = track;
        this.jId=jId;

    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public PolylineOptions getTrack() {
        return track;
    }

    public void setTrack(PolylineOptions track) {
        this.track = track;
    }
    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getDayWeek() {
        return dayWeek;
    }

    public void setDayWeek(String dayWeek) {
        this.dayWeek = dayWeek;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
    public String getjId() {
        return jId;
    }

    public void setjId(String jId) {
        this.jId = jId;
    }
}
