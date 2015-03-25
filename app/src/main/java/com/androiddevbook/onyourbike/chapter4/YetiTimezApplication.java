package com.androiddevbook.onyourbike.chapter4;

import android.app.Application;

/**
 * Created by cclamb on 2/28/15.
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
