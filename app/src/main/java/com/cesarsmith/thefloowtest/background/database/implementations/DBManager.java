package com.cesarsmith.thefloowtest.background.database.implementations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.cesarsmith.thefloowtest.background.pojos.Journey;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Softandnet on 29/11/2017.
 */

public class DBManager {


    private static String journey = "journey";
    private static String _id = "id";
    private static String start_time = "start_time";
    private static String end_time = "end_time";
    private static String date = "date";
    private static String day_week = "day_week";
    private static String total_time = "total_time";
    private static String place = "place";
    private static String track = "track";
    public static final String createTableJourney = "CREATE TABLE " + journey + " ( " + _id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + start_time + " TEXT, " + end_time + " TEXT, " + date + " TEXT, " + day_week + " TEXT, " + total_time + " REAL, " + place + " TEXT," + track + " TEXT )";
    private static String places = "places";
    private static String pId = "pid";
    private static String place_name = "place_name";
    public static final String createTablePlaces = "CREATE TABLE " + places + " ( " + pId + " TEXT, " + place_name + " TEXT, PRIMARY KEY(" + pId + ") )";

    SQLiteDatabase database;
    DBHelper dbHelper;
    AsyncRetrieve asyncRetrieve;

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        asyncRetrieve = new AsyncRetrieve(context);
    }

    public void openReadDB() {
        database = dbHelper.getReadableDatabase();
    }

    public void opendWriteDB() {
        database = dbHelper.getWritableDatabase();
    }

    public void closeDB() {
        if (database != null)
            database.close();
    }

    public ContentValues setJourney(Journey userJourney) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(start_time, userJourney.getStartTime());
        contentValues.put(end_time, userJourney.getEndTime());
        contentValues.put(date, userJourney.getDate());
        contentValues.put(day_week, userJourney.getDayWeek());
        contentValues.put(total_time, userJourney.getTotalTime());
        contentValues.put(place, userJourney.getPlace());
        contentValues.put(track, PolyUtil.encode(userJourney.getTrack().getPoints()));
        return contentValues;
    }

    public void insertJourney(Journey userJourney) {
        opendWriteDB();
        if (database != null)
            database.insert(journey, null, setJourney(userJourney));
        closeDB();

    }

    public void deleteDB() {
        opendWriteDB();
        database.delete(journey, null, null);

    }


    public List<Journey> getUserJourneys() {
        List<Journey> journeyList = new ArrayList<>();
        Cursor cursor;
        openReadDB();
        cursor = database.query(journey, new String[]{"*"}, null, null, null, null, null);
        PolylineOptions polylineOptions = new PolylineOptions();
        if (cursor != null) {
            cursor.moveToLast();
            do {
                journeyList.add(new Journey(
                        cursor.getString(cursor.getColumnIndex(start_time)),
                        cursor.getString(cursor.getColumnIndex(end_time)),
                        cursor.getString(cursor.getColumnIndex(date)),
                        cursor.getString(cursor.getColumnIndex(total_time)),
                        cursor.getString(cursor.getColumnIndex(day_week)),
                        cursor.getString(cursor.getColumnIndex(place)),
                        polylineOptions.addAll(PolyUtil.decode(cursor.getString(cursor.getColumnIndex(track)))),
                        cursor.getString(cursor.getColumnIndex(_id))));
            } while (cursor.moveToPrevious());
            cursor.close();
        }


        return journeyList;

    }

    private class AsyncRetrieve extends AsyncTask<Void, Void, Void> {
        Context context;

        public AsyncRetrieve(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }


}
