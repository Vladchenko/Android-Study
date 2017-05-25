package com.example.vladislav.androidstudy.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by vladislav on 18.05.17.
 * Description is at @strings/service_demo2_description
 */

public class ServiceDemo2 extends Service {

    final String LOG_TAG = "ServiceDemo2";

    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG, "onCreate");
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
        try {
            Toast.makeText(this, "Service demo2 performs", Toast.LENGTH_SHORT).show();
            Thread.sleep(5000);
            Toast.makeText(this, "Service demo2 is over", Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopSelf();
    }
}
