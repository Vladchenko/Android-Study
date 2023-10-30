package com.example.vladislav.androidstudy.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.concurrent.TimeUnit;

/**
 * This service emulates doing some task in a separate thread.
 */
public class ServiceDemo3 extends Service {

    private static final String LOG_TAG = "ServiceDemo3";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOG_TAG, "onStartCommand");
        // someTask();
        someTask2();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
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

    /**
     * This method should show a toast before some operation and after it, but both the toasts are shown
     * before service operation is finished. One of the ways to get around this - someTask2().
     * Left this task as a bad example.
     */
    private void someTask() {
        Toast.makeText(this, LOG_TAG + "has begun performing", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, LOG_TAG + "has finished performing", Toast.LENGTH_SHORT).show();
    }

    private void someTask2() {
        Toast.makeText(ServiceDemo3.this, "ServiceDemo3 has begun performing", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after delay
                Toast.makeText(ServiceDemo3.this, "ServiceDemo3 has finished performing", Toast.LENGTH_SHORT).show();
            }
        }, 5000);
    }
}
