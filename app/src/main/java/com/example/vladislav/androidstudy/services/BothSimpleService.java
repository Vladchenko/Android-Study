package com.example.vladislav.androidstudy.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.vladislav.androidstudy.activities.BroadcastSender;
import com.example.vladislav.androidstudy.activities.ServicesActivity;

/**
 * Created by vladislav on 02.03.17.
 */

public class BothSimpleService extends Service {

    private BroadcastSender mBroadcastSender;

    @Override
    public void onCreate() {
        super.onCreate();
        // Instantiating a broadcast sender;
        mBroadcastSender = BroadcastSender.getInstance(this);
        mBroadcastSender.sendBroadcast("onCreate");
        Log.i("BothSimpleService", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mBroadcastSender.sendBroadcast("onStartCommand started");
        Log.i("BothSimpleService", "onStartCommand started");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mBroadcastSender.sendBroadcast("onStartCommand end");
        Log.i("BothSimpleService", "onStartCommand end");
        return START_NOT_STICKY;
//        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBroadcastSender.sendBroadcast("onDestroy");
        mBroadcastSender.sendBroadcast("-----------------------");
        Log.i("BothSimpleService", "onDestroy");
        Log.i("-", "-----------------------");
        ServicesActivity.bounded = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mBroadcastSender.sendBroadcast("onBind");
        Log.i("BothSimpleService", "onBind");
        ServicesActivity.bounded = true;
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mBroadcastSender.sendBroadcast("onUnbind");
        Log.i("BothSimpleService", "onUnbind");
        return super.onUnbind(intent);
    }

}
