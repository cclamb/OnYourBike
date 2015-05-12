package com.yeti.timez.utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yeti.timez.model.Route;
import com.yeti.timez.model.Trip;

/**
 * Created by cclamb on 5/12/15.
 */
public class SQLiteHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "YetiTimez.db";
    private static final int DATABASE_VERSION = 2;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() {
        getWritableDatabase();
    }

    public void create() {
        open();
    }

    public void onConfigure(SQLiteDatabase db) {
        db.execSQL("pragma foreign_keys=ON;");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Route.create(db);
        Trip.create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Trip.drop(db);
        Route.drop(db);
        onCreate(db);
    }
}
