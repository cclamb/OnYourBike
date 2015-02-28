package com.androiddevbook.onyourbike.chapter4;

import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public final class TimerActivity extends ActionBarActivity {

    private static final long INTERVAL = 200;


    private final String ME = getClass().getName();

    private TextView counter;
    private Button start;
    private Button stop;
    private boolean isTimerRunning;
    private long startedAt;
    private long lastStopped;
    private Handler handler;
    private Runnable updateTimer;
    private Vibrator vibrate;
    private long lastSeconds;

    public TimerActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Log.d(ME, "onCreate() called.");
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                    new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build()
            );
            StrictMode.setVmPolicy(
                    new StrictMode
                            .VmPolicy
                            .Builder()
                            .detectAll()
                            .penaltyLog()
                            .penaltyDeath()
                            .build()
            );
        }
        counter = (TextView) findViewById(R.id.timer);
        start = (Button) findViewById(R.id.start_button);
        stop = (Button) findViewById(R.id.stop_button);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (isTimerRunning) {
            setHandler();
        }

        vibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrate == null) {
            Log.w(ME, "no vibe service here");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isTimerRunning) {
            clearHandler();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableButtons();
        setTimeDisplay();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickStart(View view) {
        Log.d(ME, "start clicked");
        isTimerRunning = true;
        startedAt = System.currentTimeMillis();
        enableButtons();
        setHandler();
    }

    public void onClickStop(View view) {
        Log.d(ME, "stop clicked");
        isTimerRunning = false;
        enableButtons();
        lastStopped = System.currentTimeMillis();
        setTimeDisplay();
        clearHandler();
    }

    public void onClickSettings(View view) {
        Log.d(ME, "clicked settings");
        Intent settingsIntent = new Intent(getApplicationContext(),
                SettingsActivity.class);
        startActivity(settingsIntent);
    }

    private void clearHandler() {
        handler.removeCallbacks(updateTimer);
        handler = null;
    }

    private void setHandler() {
        handler = new Handler();
        updateTimer = new Runnable() {
            @Override
            public void run() {
                setTimeDisplay();
                if (isTimerRunning) {
                    vibrateCheck();
                }
                if (handler != null) {
                    handler.postDelayed(this, INTERVAL);
                }
            }
        };
        handler.postDelayed(updateTimer, INTERVAL);
    }

    private void enableButtons() {
        Log.d(ME, "Set buttons enabled/disabled.");
        start.setEnabled(!isTimerRunning);
        stop.setEnabled(isTimerRunning);
        setTimeDisplay();
    }

    private void setTimeDisplay() {
        String display;
        long timeNow;
        long diff;
        long seconds;
        long minutes;
        long hours;

        if (isTimerRunning) {
            timeNow = System.currentTimeMillis();
        } else {
            timeNow = lastStopped;
        }

        diff = timeNow - startedAt;

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

        counter.setText(display);
    }

    protected void vibrateCheck() {
        long timeNow = System.currentTimeMillis();
        long diff = timeNow - startedAt;
        long seconds = diff / 1000;
        long minutes = seconds / 60;

        Log.d(ME, "vibrateCheck");

        seconds = seconds % 60;
        minutes = minutes % 60;

        if (vibrate != null && seconds == 0 && seconds != lastSeconds) {
            long[] once = { 0, 100 };
            long[] twice = { 0, 100, 400, 100 };
            long[] thrice = { 0, 100, 400, 100, 400, 100 };

            // every hour
            if (minutes == 0) {
                Log.i(ME, "Vibrate 3 times");
                vibrate.vibrate(thrice, -1);
            }
            // every 15 minutes
            else if (minutes % 15 == 0) {
                Log.i(ME, "Vibrate 2 time");
                vibrate.vibrate(twice, -1);
            }
            // every 5 minutes
            else if (minutes % 5 == 0) {
                Log.i(ME, "Vibrate once");
                vibrate.vibrate(once, -1);
            }
        }

        lastSeconds = seconds;
    }

    @Override
    public String toString() {
        return getClass().getName();
    }
}
