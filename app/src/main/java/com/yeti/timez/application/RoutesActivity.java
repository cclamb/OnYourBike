package com.yeti.timez.application;

import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.yeti.timez.R;
import com.yeti.timez.model.Route;
import com.yeti.timez.model.Routes;
import com.yeti.timez.utilities.SQLiteHelper;

import java.util.List;

public class RoutesActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SQLiteHelper helper = ((YetiTimezApplication) getApplication()).getSQLiteHelper();

        SQLiteDatabase database = helper.open();
        List<Route> routes = Routes.getAll(helper, database);
        helper.close();

        ArrayAdapter<Route> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                routes
        );
        setListAdapter(adapter);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_routes, menu);
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
}