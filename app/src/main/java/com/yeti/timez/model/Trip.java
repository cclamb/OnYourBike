package com.yeti.timez.model;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by cclamb on 5/11/15.
 */
public class Trip {
    private long _id;
    public long timeStarted;
    public long timeTaken;

    public static void create(SQLiteDatabase db) {
        String createTable = "create table if not exists trips ("
                + "_id integer primary key autoincrement, "
                + "route_id integer references routes(_id), "
                + "timeStarted integer not null, "
                + "timeTaken integer not null);";
        db.execSQL(createTable);
    }

    public static void drop(SQLiteDatabase db) {
        String dropTable = "drop table if exists trips;";
        db.execSQL(dropTable);
    }
}
