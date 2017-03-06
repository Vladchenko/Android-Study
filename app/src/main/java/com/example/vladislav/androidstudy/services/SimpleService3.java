package com.example.vladislav.androidstudy.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by vladislav on 02.03.17.
 */

public class SimpleService3 extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("SimpleService3","onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("SimpleService3","onStartCommand");
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
        Log.i("SimpleService3","onDestroy");
        Log.i("-","-----------------------");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("SimpleService3","onBind");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("SimpleService3","onUnbind");
        return super.onUnbind(intent);
    }

}
