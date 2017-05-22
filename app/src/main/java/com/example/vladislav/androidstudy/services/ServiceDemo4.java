package com.example.vladislav.androidstudy.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

/**
 * Implementing a bound service.
 */

public class ServiceDemo4 extends Service {

    final String LOG_TAG = "ServiceDemo4";
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
        Toast.makeText(this, "Service demo4 performs", Toast.LENGTH_SHORT).show();
//        Thread thread = new Thread(new Runnable() {
//            public void run() {
//                for (int i = 0; i <= 5; i++) {
//                    Log.d(LOG_TAG, "i = " + i);
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                stopSelf();
//            }
//        });
    }

}
