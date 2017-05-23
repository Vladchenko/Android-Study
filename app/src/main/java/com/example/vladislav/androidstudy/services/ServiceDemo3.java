package com.example.vladislav.androidstudy.services;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * This service emulates doing some task in a separate thread. In fact it sleeps for 5 seconds total.
 */

public class ServiceDemo3 extends Service {

    final String LOG_TAG = "ServiceDemo3";
    private Context context;


    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG, "onCreate");
        context = getApplicationContext();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOG_TAG, "onStartCommand");
        someTask();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    @Override
    @Nullable
    public IBinder onBind(Intent intent) {
        Log.i(LOG_TAG, "onBind");
        return null;
    }

    void someTask() {

        Toast.makeText(this, "Service demo3 performs", Toast.LENGTH_SHORT).show();

        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i <= 5; i++) {
                    Log.d(LOG_TAG, "i = " + i);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stopSelf();
            }
        }).start();

        // Next, we need to know if a service did its job and notify a service about it.
        // There are several ways of doing it:
        //  ... Describe the ways ...
        // We'll do this in a ServiceDemo4.
    }

}
