package com.yeti.timez.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Vibrator;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yeti.timez.BuildConfig;
import com.yeti.timez.R;

public final class TimerActivity extends Activity { //ActionBarActivity {

    private static final long INTERVAL = 200;


    private final String ME = getClass().getName();

    private Notify notify;
    private TextView counter;
    private Button start;
    private Button stop;
    private TimerState timer;
    private Handler handler;
    private Runnable updateTimer;
    private Vibrator vibrate;
    private long lastSeconds;

    public TimerActivity() {
        super();
        timer = new TimerState();
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

        if (timer.isRunning()) {
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
        if (timer.isRunning()) {
            clearHandler();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableButtons();
        counter.setText(timer.display());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_timer, menu);
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case R.id.menu_settings:
                onClickSettings(null);
                return true;
            case R.id.menu_routes:
                onClickedRoutes();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onClickedRoutes() {
        Log.d(ME, "clickedRoutes");
        Intent routes = new Intent(getApplicationContext(), RoutesActivity.class);
        startActivity(routes);
    }

    public void onClickStart(View view) {
        Log.d(ME, "start clicked");
        timer.start();
        enableButtons();
        setHandler();
    }

    public void onClickStop(View view) {
        Log.d(ME, "stop clicked");
        timer.stop();
        enableButtons();
        counter.setText(timer.display());
        clearHandler();
    }

    public void onClickSettings(View view) {
        Log.d(ME, "clicked settings");
        Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
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
                counter.setText(timer.display());
                if (timer.isRunning()) {
                    vibrateCheck();
                    notifyCheck();
                }

                if (handler != null) {
                    handler.postDelayed(this, INTERVAL);
                }
            }
        };
        handler.postDelayed(updateTimer, INTERVAL);
    }

    private void notifyCheck() {
        long diff = timer.elapsedTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        Log.d(ME, "notifyCheck");

        if (minutes % 15 == 0 && seconds == 0 && seconds != lastSeconds) {
            String title = getResources().getString(R.string.time_title);
            String message = getResources().getString(R.string.time_running_message);

            if (hours == 0 && minutes == 0) {
                message = getResources().getString(R.string.time_start_message);
            }

            message = String.format(message, hours, minutes);

            notify.notify(title, message);
        }

        lastSeconds = seconds;
    }

    private void enableButtons() {
        Log.d(ME, "Set buttons enabled/disabled.");
        start.setEnabled(!timer.isRunning());
        stop.setEnabled(timer.isRunning());
        counter.setText(timer.display());
    }

    protected void vibrateCheck() {
        long diff = timer.elapsedTime();
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
