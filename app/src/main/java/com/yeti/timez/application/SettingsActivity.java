package com.yeti.timez.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.yeti.timez.R;


public final class SettingsActivity extends Activity {//ActionBarActivity {

    private final String ME = getClass().getName();

    private CheckBox vibrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        vibrate = (CheckBox) findViewById(R.id.vibrate_checkbox);
        Settings settings = ((YetiTimezApplication) getApplication()).getSettings();
        vibrate.setChecked(settings.isVibrateOn());
        setupActionBar();
    }

    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            //ActionBar actionBar = getSupportActionBar();
            android.app.ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Settings settings = ((YetiTimezApplication) getApplication()).getSettings();
        settings.setVibrateOn(vibrate.isChecked());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                gotoHome();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void vibrateChanged(View view) {
        Toaster toast = new Toaster(getApplicationContext());

        if (vibrate.isChecked()) {
            toast.make(R.string.vibrate_on);
        } else {
            toast.make(R.string.vibrate_off);
        }
    }

    public void goBack(View view) {
        finish();
    }

    private void gotoHome() {
        Log.d(ME, "gotoHome");

        Intent timer = new Intent(getApplicationContext(), TimerActivity.class);
        timer.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(timer);
    }
}
