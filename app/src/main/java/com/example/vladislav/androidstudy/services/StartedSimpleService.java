package com.example.vladislav.androidstudy.services;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.vladislav.androidstudy.activities.BroadcastSender;

/**
 * Created by vladislav on 02.03.17.
 */

public class StartedSimpleService extends android.app.Service {

    private BroadcastSender mBroadcastSender;

    @Override
    public void onCreate() {
        super.onCreate();
        // Instantiating a broadcast sender;
        mBroadcastSender = BroadcastSender.getInstance(this);
        mBroadcastSender.sendBroadcast("onCreate");
        Log.i("StartedSimpleService", "onCreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mBroadcastSender.sendBroadcast("onStartCommand start");
        Log.i("StartedSimpleService", "onStartCommand begin");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mBroadcastSender.sendBroadcast("onStartCommand end");
        Log.i("StartedSimpleService", "onStartCommand end");
        return START_NOT_STICKY;
//        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mLocalBroadcastManager.unregisterReceiver(mBroadcastReceiver);
        mBroadcastSender.sendBroadcast("onDestroy");
        mBroadcastSender.sendBroadcast("---------------------------------------\n");
//        unregisterReceiver(mBroadcastReceiver);
//        Log.i("-","-----------------------");
    }

    // Dummy method.
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
//        Log.i("StartedSimpleService","onBind");
        return null;
    }

}