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
import androidx.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * This service produces an integer value and sends it to activity.
 */

public class ServiceDemo5 extends Service {

    // Random number generator
    private final Random mGenerator = new Random();
    private final String LOG_TAG = "ServiceDemo5";
    private Messenger messageHandler;
    private boolean mBound = false;
    private Context context;
    private int value = 0;
    private Thread thread;

    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG, "onCreate");
        context = getApplicationContext();
        Toast.makeText(this, LOG_TAG + " created", Toast.LENGTH_SHORT).show();
        thread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        value = mGenerator.nextInt(1000);
                        sendMessage(value);
                        TimeUnit.SECONDS.sleep(5);
                        Log.i(LOG_TAG, Integer.toString(value));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOG_TAG, "onStartCommand");

        Bundle extras = intent.getExtras();
        messageHandler = (Messenger) extras.get("MESSENGER");

        thread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        Log.i(LOG_TAG, "onDestroy");
        Toast.makeText(this, LOG_TAG + " stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    @Nullable
    public IBinder onBind(Intent intent) {
        Log.i(LOG_TAG, "onBind");
        return null;
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
