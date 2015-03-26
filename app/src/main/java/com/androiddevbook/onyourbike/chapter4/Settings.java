package com.androiddevbook.onyourbike.chapter4;

import android.util.Log;

/**
 * Created by cclamb on 2/28/15.
 *
 * Maintains simple settings.
 */
public final class Settings {

    private static String ME;

    private boolean isVibrateOn;

    public Settings() {}

    public boolean isVibrateOn() {
        return isVibrateOn;
    }
    public void setVibrateOn(boolean isVibrateOn) {
        Log.i(ME, "saving vibration state: " + isVibrateOn);
        this.isVibrateOn = isVibrateOn;
    }

}
