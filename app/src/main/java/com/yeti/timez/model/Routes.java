package com.yeti.timez.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yeti.timez.utilities.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cclamb on 5/15/15.
 */
public class Routes {

    static public List<Route> getAll(SQLiteHelper helper,
                                     SQLiteDatabase database) {
        List<Route> routes = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from routes", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Route route = cursorToRoute(cursor);
            routes.add(route);
            cursor.moveToNext();
        }

        cursor.close();

        return routes;
    }

    private static Route cursorToRoute(Cursor cursor) {
        Route route = new Route();
        route.setid(cursor.getInt(cursor.getColumnIndex("_id")));
        route.name = cursor.getString(cursor.getColumnIndex("name"));
        route.notes = cursor.getString(cursor.getColumnIndex("notes"));
        return route;
    }

}
