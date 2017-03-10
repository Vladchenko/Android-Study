package com.example.vladislav.androidstudy.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.vladislav.androidstudy.BroadcastSender;
import com.example.vladislav.androidstudy.ServicesActivity;

/**
 * An {@link BindIntentedService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class BindIntentedService extends IntentService {

    private BroadcastSender mBroadcastSender;

    public BindIntentedService() {
        super("IntendedService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Instantiating a broadcast sender;
        mBroadcastSender = BroadcastSender.getInstance(this);
        mBroadcastSender.sendBroadcast("onCreate");
        Log.i("BindIntentedService","onCreate");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mBroadcastSender.sendBroadcast("onHandleIntent start");
        Log.i("BindIntentedService", "onHandleIntent start");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mBroadcastSender.sendBroadcast("onHandleIntent end");
        Log.i("BindIntentedService", "onHandleIntent end");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBroadcastSender.sendBroadcast("onDestroy");
        mBroadcastSender.sendBroadcast("-----------------------");
        Log.i("BindIntentedService","onDestroy");
        Log.i("-","-----------------------");
        ServicesActivity.bounded = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mBroadcastSender.sendBroadcast("onBind");
        Log.i("BindIntentedService","onBind");
        ServicesActivity.bounded = true;
        sendBroadcast(new Intent().putExtra(ServicesActivity.BROADCAST_ID,"onCreate"));
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mBroadcastSender.sendBroadcast("onUnbind");
        Log.i("BindIntentedService","onUnbind");
        return super.onUnbind(intent);
    }


}
