package com.example.vladislav.androidstudy.services.demo1;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.vladislav.androidstudy.activities.BroadcastSender;

/**
 * An {@link StartedIntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class StartedIntentService extends IntentService {

    private BroadcastSender mBroadcastSender;

    public StartedIntentService() {
        super("StartedIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Instantiating a broadcast sender;
        mBroadcastSender = BroadcastSender.getInstance(this);
        mBroadcastSender.sendBroadcast("onCreate");
        Log.i("StartedIntentService","onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Without invoking super, method onHandleIntent(...) isn't called.
        super.onStartCommand(intent, flags, startId);
        mBroadcastSender.sendBroadcast("onStartCommand");
        Log.i("StartedIntentService","onStartCommand");
//        return START_NOT_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("StartedIntentService", "onHandleIntent start");
        mBroadcastSender.sendBroadcast("onHandleIntent start");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mBroadcastSender.sendBroadcast("onHandleIntent end");
        Log.i("StartedIntentService", "onHandleIntent end");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBroadcastSender.sendBroadcast("onDestroy");
        mBroadcastSender.sendBroadcast("-----------------------");
        Log.i("StartedIntentService","onDestroy");
        Log.i("-","-----------------------");
    }

}
