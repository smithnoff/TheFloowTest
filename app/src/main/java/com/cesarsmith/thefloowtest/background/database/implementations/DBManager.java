package com.cesarsmith.thefloowtest.background.database.implementations;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.cesarsmith.thefloowtest.background.pojos.Journey;

/**
 * Created by Softandnet on 29/11/2017.
 */

public class DBManager {


    private static String journey="journey";
    private static String _id="id";
    private static String start_time="start_time";
    private static String end_time="end_time";
    private static String date="date";
    private static String day_week="day_week";
    private static String total_time="total_time";
    private static String place="place";
    private static String track="track";
    public static final String createTableJourney="CREATE TABLE "+journey+" ( "+_id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+start_time+" TEXT, "+end_time+" TEXT, "+date+" TEXT, "+day_week+" TEXT, "+total_time+" REAL, "+place+" TEXT,"+track+" TEXT )";
    private static String places="places";
    private static String pId="pid";
    private static String place_name="place_name";
    public static final String createTablePlaces="CREATE TABLE "+places+" ( "+pId+" TEXT, "+place_name+" TEXT, PRIMARY KEY("+pId+") )";

    SQLiteDatabase database;
    DBHelper dbHelper;

    public DBManager(Context context) {
        dbHelper=new DBHelper(context);
        database=dbHelper.getWritableDatabase();
    }
    public void openReadDB(){
     database=dbHelper.getReadableDatabase();
    }
    public void opendWriteDB(){
        database=dbHelper.getWritableDatabase();
    }
    public void closeDB(){
        if (database!=null)
        database.close();
    }

    public ContentValues setJourney(Journey userJourney){
        ContentValues contentValues=new ContentValues();





        return contentValues;
    }

    public void insertJourney(Journey userJourney){
        opendWriteDB();
        database.insert(journey,null,setJourney(userJourney));
        closeDB();

    }


}
