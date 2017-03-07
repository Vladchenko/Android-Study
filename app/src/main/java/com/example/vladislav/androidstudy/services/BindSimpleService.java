package com.example.vladislav.androidstudy.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.vladislav.androidstudy.BroadcastSender;
import com.example.vladislav.androidstudy.ServicesActivity;

/**
 * Created by vladislav on 02.03.17.
 */

public class BindSimpleService extends Service {

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
        Log.i("BindSimpleService","onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ServicesActivity.BOUND = false;
        mBroadcastSender.sendBroadcast("onDestroy");
        mBroadcastSender.sendBroadcast("-----------------------");
        Log.i("BindSimpleService","onDestroy");
        Log.i("-","-----------------------");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mBroadcastSender.sendBroadcast("onBind");
        Log.i("BindSimpleService","onBind");
        ServicesActivity.BOUND = true;
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mBroadcastSender.sendBroadcast("onUnbind");
        Log.i("BindSimpleService","onUnbind");
        return super.onUnbind(intent);
    }

}
