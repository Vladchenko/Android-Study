package com.example.vladislav.androidstudy.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by vladislav on 02.03.17.
 */

public class SimpleService extends android.app.Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("SimpleService","onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("SimpleService","onStartCommand");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return START_NOT_STICKY;
//        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("SimpleService","onDestroy");
        Log.i("-","-----------------------");
    }

    // Dummy method.
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
//        Log.i("SimpleService","onBind");
        return null;
    }

}
