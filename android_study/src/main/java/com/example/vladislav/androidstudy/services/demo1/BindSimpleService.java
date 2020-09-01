package com.example.vladislav.androidstudy.services.demo1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;

import com.example.vladislav.androidstudy.activities.BroadcastSender;
import com.example.vladislav.androidstudy.services.ServicesDemo1Activity;

/**
 * Created by vladislav on 02.03.17.
 */

public class BindSimpleService extends Service {

    private BroadcastSender mBroadcastSender;

    @Override
    public void onCreate() {
        super.onCreate();
        // Instantiating a broadcast sender;
        mBroadcastSender = BroadcastSender.getInstance(this);
//        if (ServicesDemo1Activity.issLocalBroadcastReceiver()) {
//            mBroadcastSender.sendBroadcast("Local BroadcastReceiver is used.\n");
//        } else {
//            mBroadcastSender.sendBroadcast("Global BroadcastReceiver is used.\n");
//        }
        mBroadcastSender.sendBroadcast("onCreate");
        Log.i("BindSimpleService","onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ServicesDemo1Activity.bounded = false;
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
        ServicesDemo1Activity.bounded = true;
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mBroadcastSender.sendBroadcast("onUnbind");
        Log.i("BindSimpleService","onUnbind");
        return super.onUnbind(intent);
    }

}
