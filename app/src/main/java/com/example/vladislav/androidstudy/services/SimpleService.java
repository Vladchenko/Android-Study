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
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("SimpleService","onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("SimpleService","onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("SimpleService","onBind");
//        Log.i("SimpleService", "SimpleService is bound to - " + intent.getAction());
        return null;
    }
}
