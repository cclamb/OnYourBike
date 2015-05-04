package com.androiddevbook.onyourbike.chapter4;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by cclamb on 3/26/15.
 *
 * Displays toast.
 */
public final class Toaster {

    private static String CLASS_NAME;
    private final static int TOAST_DURATION = Toast.LENGTH_SHORT;
    private final Context context;

    public Toaster(Context context) {
        CLASS_NAME = getClass().getName();
        this.context = context;
    }

    public void make(int resource) {
        Log.d(CLASS_NAME, "Toaster.make(.)");
        Toast toast = Toast.makeText(context, resource, TOAST_DURATION);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }
}
