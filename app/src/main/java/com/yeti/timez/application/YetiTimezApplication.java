package com.yeti.timez.application;

import android.app.Application;

import com.yeti.timez.utilities.SQLiteHelper;

/**
 * Created by cclamb on 2/28/15.
 *
 * The entry point for the yeti com.yeti.com.yeti.timez application.
 */
public final class YetiTimezApplication extends Application {

    static private Settings settings;

    public Settings getSettings() {
        if (settings == null) settings = new Settings();
        return settings;
    }

    @Override
    public void onCreate() {
        SQLiteHelper helper = new SQLiteHelper(getApplicationContext());
        super.onCreate();
        helper.create();
    }
}
