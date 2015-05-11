package com.yeti.timez.application;

import android.app.Application;

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

//    public void setSettings(Settings settings) {
//        this.settings = settings;
//    }
}
