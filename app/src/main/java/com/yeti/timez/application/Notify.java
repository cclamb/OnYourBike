package com.yeti.timez.application;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.yeti.timez.R;

/**
 * Created by cclamb on 5/11/15.
 */
public class Notify {

    private static String CLASS_NAME;

    private NotificationManager manager;
    private Context context;

    private static final int MESSAGE_ID = 1;

    public int smallIcon = R.drawable.ic_launcher;

    public Notify(Activity activity) {
        CLASS_NAME = getClass().getName().toString();

        manager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        context = activity.getApplicationContext();
    }

    private Notification create(String title, String message, long when) {
        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(title).setContentText(message).setWhen(when)
                .setSmallIcon(smallIcon).build();
        Log.d(CLASS_NAME, "create()");
        return notification;
    }

    public void notify(String title, String message) {
        Log.d(CLASS_NAME, "notify()");

        Notification notification = create(title, message,
                System.currentTimeMillis());

        manager.notify(MESSAGE_ID, notification);
    }

    public void notify(String title, String message, long when) {
        Log.d(CLASS_NAME, "notify()");
        Notification notification = create(title, message, when);
        manager.notify(MESSAGE_ID, notification);
    }

}
