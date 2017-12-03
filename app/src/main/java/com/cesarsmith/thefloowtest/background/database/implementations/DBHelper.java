package com.cesarsmith.thefloowtest.background.database.implementations;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Softandnet on 29/11/2017.
 */
/*This class is to create the tables for the database */
public class DBHelper extends SQLiteOpenHelper {


    private static final String name = "theFloowTest";
    private static final int version = 1;

    public DBHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Journey table creation query
        sqLiteDatabase.execSQL(DBManager.createTableJourney);
        //Places table creation query
        sqLiteDatabase.execSQL(DBManager.createTablePlaces);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
