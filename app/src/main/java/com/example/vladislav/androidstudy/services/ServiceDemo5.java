package com.example.vladislav.androidstudy.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *
 */

public class ServiceDemo5 extends Service {

    // Random number generator
    private final Random mGenerator = new Random();
    final String LOG_TAG = "ServiceDemo5";
    private Context context;
    boolean mBound = false;
    int value = 0;

    private Messenger messageHandler;

    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG, "onCreate");
        context = getApplicationContext();
        Toast.makeText(this, "Service demo5 started", Toast.LENGTH_SHORT).show();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOG_TAG, "onStartCommand");

        Bundle extras = intent.getExtras();
        messageHandler = (Messenger) extras.get("MESSENGER");

        someTask();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
        Toast.makeText(this, LOG_TAG + " stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    @Nullable
    public IBinder onBind(Intent intent) {
        Log.i(LOG_TAG, "onBind");
        return null;
    }

    void someTask() {
        // Service is to produce a new integer value at random and activity will require this value
        // on a user's demand.
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(10);
                        value = mGenerator.nextInt();
                        sendMessage(value);
                        Log.i(LOG_TAG, Integer.toString(value));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    // Sending message to an activity.
    public void sendMessage(int value) {
        Message message = Message.obtain();
        message.arg1 = value;
        try {
            messageHandler.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
