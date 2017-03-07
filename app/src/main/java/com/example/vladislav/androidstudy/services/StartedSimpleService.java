package com.example.vladislav.androidstudy.services;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.vladislav.androidstudy.BroadcastSender;
import com.example.vladislav.androidstudy.ServicesActivity;

/**
 * Created by vladislav on 02.03.17.
 */

public class StartedSimpleService extends android.app.Service {

    private LocalBroadcastManager mLocalBroadcastManager;
    private BroadcastReceiver mBroadcastReceiver;
    private BroadcastSender mBroadcastSender;

    @Override
    public void onCreate() {
        super.onCreate();
        // Instantiating a broadcast sender;
        mBroadcastSender = BroadcastSender.getInstance(this, ServicesActivity.issLocalBroadcastReceiver());
        if (ServicesActivity.issLocalBroadcastReceiver()) {
            mBroadcastSender.sendBroadcast("Local BroadcastReceiver is used.\n");
        } else {
            mBroadcastSender.sendBroadcast("Global BroadcastReceiver is used.\n");
        }
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

//    private void sendingBroadcast(String string) {
//        if (!ServicesActivity.issLocalBroadcastReceiver()) {
//            sendBroadcast(
//                    new Intent().
//                            setAction(ServicesActivity.BROADCAST_ID).
//                            putExtra(ServicesActivity.BROADCAST_ID,
//                                    this.getClass().getSimpleName() + ": " + string + "\n"));
//        } else {
//            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent().
//                    setAction(ServicesActivity.BROADCAST_ID).
//                    putExtra(ServicesActivity.BROADCAST_ID,
//                            this.getClass().getSimpleName() + ": " + string + "\n"));
//        }
//    }

}
