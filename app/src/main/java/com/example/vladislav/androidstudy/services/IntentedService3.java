package com.example.vladislav.androidstudy.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * An {@link IntentedService3} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class IntentedService3 extends IntentService {

    private Handler mHandler;

    public IntentedService3() {
        super("IntendedService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        mHandler = new Handler();
        Log.i("IntendedService3","onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("IntentedService3","onStartCommand");
        mHandler = new Handler();
//        return START_NOT_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        int tm = intent.getIntExtra("time", 0);
//        String label = intent.getStringExtra("task");
        Log.i("IntentedService3", "onHandleIntent start");
        mHandler.post(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(getApplicationContext(), "Intent service performs now ...",
//                        Toast.LENGTH_SHORT).show();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                Toast.makeText(getApplicationContext(), "Intent service has finished its operation.",
//                        Toast.LENGTH_SHORT).show();
                Log.i("IntentedService3", "onHandleIntent end");
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("IntentedService3","onDestroy");
        Log.i("-","-----------------------");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("IntentedService3","onBind");
//        Log.i("SimpleService", "SimpleService is bound to - " + intent.getAction());
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("IntentedService3","onUnbind");
        return super.onUnbind(intent);
    }

}
