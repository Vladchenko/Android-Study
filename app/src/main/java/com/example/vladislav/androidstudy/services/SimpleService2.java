package com.example.vladislav.androidstudy.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by vladislav on 02.03.17.
 */

public class SimpleService2 extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("SimpleService2","onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("SimpleService2","onDestroy");
        Log.i("-","-----------------------");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("SimpleService2","onBind");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("SimpleService2","onUnbind");
        return super.onUnbind(intent);
    }

}
