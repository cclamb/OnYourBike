package com.androiddevbook.onyourbike.chapter4;

/**
 * Created by cclamb on 2/28/15.
 */
public final class Settings {

    private static String ME;

    private boolean isVibrateOn;

    public Settings() {
        ME = getClass().getName();
    }

    public boolean isVibrateOn() {
        return isVibrateOn;
    }

    public void setVibrateOn(boolean isVibrateOn) {
        this.isVibrateOn = isVibrateOn;
    }

}
