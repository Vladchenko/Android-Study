package com.example.vladislav.androidstudy.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * An {@link IntentedService2} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class IntentedService2 extends IntentService {

    private Handler mHandler;

    public IntentedService2() {
        super("IntendedService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        mHandler = new Handler();
        Log.i("IntendedService","onCreate");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("IntentedService2", "onHandleIntent start");
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
                Log.i("IntentedService2", "onHandleIntent end");
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("IntentedService2","onDestroy");
        Log.i("-","-----------------------");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("IntentedService2","onBind");
//        Log.i("SimpleService", "SimpleService is bound to - " + intent.getAction());
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("IntentedService2","onUnbind");
        return super.onUnbind(intent);
    }

}
