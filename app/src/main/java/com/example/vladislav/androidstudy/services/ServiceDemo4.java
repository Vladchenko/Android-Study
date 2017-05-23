package com.example.vladislav.androidstudy.services;

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
 * Implementing a bound service.
 */

public class ServiceDemo4 extends Service {

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    // Random number generator
    private final Random mGenerator = new Random();
    final String LOG_TAG = "ServiceDemo4";
    private Context context;
    boolean mBound = false;
    int value = 0;
    /**
     * Class used for the client Binder. Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public ServiceDemo4 getService() {
            // Return this instance of ServiceDemo4 so clients can call public methods
            return ServiceDemo4.this;
        }
    }

    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG, "onCreate");
        context = getApplicationContext();
        Toast.makeText(this, "Service demo4 started", Toast.LENGTH_SHORT).show();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOG_TAG, "onStartCommand");
        someTask();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
        Toast.makeText(this, "Service demo4 stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    @Nullable
    public IBinder onBind(Intent intent) {
        Log.i(LOG_TAG, "onBind");
        return mBinder;
    }

    void someTask() {
        // Service is to produce a new integer value at random and activity will require this value
        // on a user's demand.
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(10);
                        value = mGenerator.nextInt();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /** method for clients */
    public int getServiceData() {
        return value;
    }

}
