package com.yeti.timez.application;

import android.util.Log;

/**
 * Created by cclamb on 3/26/15.
 *
 * A simple Timer state class.
 */
public final class TimerState {

    private static String CLASS_NAME;

    private boolean isTimerRunning;
    private long startedAt;
    private long lastStopped;

    public TimerState() {
        CLASS_NAME = getClass().getName();
    }

    public void reset() {
        isTimerRunning = false;
        startedAt = System.currentTimeMillis();

        lastStopped = 0;
    }

    public void start() {
        isTimerRunning = true;
        startedAt = System.currentTimeMillis();
    }

    public void stop(){
        isTimerRunning = false;
        lastStopped = System.currentTimeMillis();
    }

    public boolean isRunning() {
        return isTimerRunning;
    }

    public long elapsedTime() {
        long timeNow;

        if (isRunning()) {
            timeNow = System.currentTimeMillis();
        } else {
            timeNow = lastStopped;
        }

        return timeNow - startedAt;
    }

    public String display() {
        String display;
        long diff;
        long seconds;
        long minutes;
        long hours;

        Log.d(CLASS_NAME, "setTimeDisplay");

        diff = elapsedTime();

        // no negative time
        if (diff < 0) {
            diff = 0;
        }

        seconds = diff / 1000;
        minutes = seconds / 60;
        hours = minutes / 60;
        seconds = seconds % 60;
        minutes = minutes % 60;

        display = String.format("%d", hours) + ":"
                + String.format("%02d", minutes) + ":"
                + String.format("%02d", seconds);

        Log.i(CLASS_NAME, "Time is " + display);

        return display;
    }

}
