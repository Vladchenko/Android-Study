package com.example.vladislav.androidstudy.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Implementing a bound service.
 * Once it starts, it generates a new int value. Clients can request it on demand.
 */
public class ServiceDemo4 extends Service {

    private static final String LOG_TAG = "ServiceDemo4";

    int mValue = 0;
    boolean mIsBound = false;
    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG, "onCreate");
        Toast.makeText(this, LOG_TAG + " started", Toast.LENGTH_SHORT).show();
    }

    @Override
    @Nullable
    public IBinder onBind(Intent intent) {
        Log.i(LOG_TAG, "onBind");
        mIsBound = true;
        someTask();
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOG_TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsBound = false;
        Log.i(LOG_TAG, "onDestroy");
        Toast.makeText(this, LOG_TAG + " stopped", Toast.LENGTH_SHORT).show();
    }

    private void someTask() {
        // Service is to produce a new integer value at random and activity will require this value
        // on a user's demand.
        final Random random = new Random();
        new Thread(new Runnable() {
            public void run() {
                while (mIsBound) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        mValue = random.nextInt();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * Provide value for clients
     */
    public int getServiceData() {
        return mValue;
    }

    /**
     * Class used for the client Binder. Since we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public ServiceDemo4 getService() {
            // Return this instance of ServiceDemo4 so clients can call public methods
            return ServiceDemo4.this;
        }
    }
}
