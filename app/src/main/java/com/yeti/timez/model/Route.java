package com.yeti.timez.model;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by cclamb on 5/11/15.
 */
public class Route {
    private long _id;
    public String name;
    public String notes;
    public Trip trips[];

    public static void create(SQLiteDatabase db) {
        String createTable = "create table if not exists routes ("
                + "_id integer primary key autoincrement, "
                + "name text not null, "
                + "notes text not null);";
        db.execSQL(createTable);
    }

    public static void drop(SQLiteDatabase db) {
        String dropTable = "drop table if exists routes;";
        db.execSQL(dropTable);
    }

    public void setid(int id) {
        this._id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
