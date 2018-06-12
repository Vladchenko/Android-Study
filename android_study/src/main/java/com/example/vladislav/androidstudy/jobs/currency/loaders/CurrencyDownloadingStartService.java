package com.example.vladislav.androidstudy.jobs.currency.loaders;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;

import com.example.vladislav.androidstudy.jobs.currency.beans.CurrenciesContainer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.vladislav.androidstudy.jobs.currency.CurrencyUtils.parse;
import static com.example.vladislav.androidstudy.jobs.currency.beans.CurrenciesContainer.CURRENCIES_TAG;
import static com.example.vladislav.androidstudy.jobs.currency.fragments.CurrencyStartServiceFragment.BROADCAST_ACTION;
import static com.example.vladislav.androidstudy.jobs.currency.fragments.CurrencyStartServiceFragment.sURL;

/**
 * Created by Влад on 02.06.2018.
 */

public class CurrencyDownloadingStartService extends Service {

    private String mLink;
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private CurrenciesContainer mCurrenciesContainer = null;

    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread thread = new HandlerThread("ServiceStartArguments", 0);
        thread.start();
        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);

    }

    //returns the instance of the service
    public class LocalBinder extends Binder {
        public CurrencyDownloadingStartService getServiceInstance(){
            return CurrencyDownloadingStartService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        mLink = (String) intent.getExtras().get(sURL);

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendBroadcastDownloadingOver() {
        Intent intent = new Intent();
        intent.putExtra(CURRENCIES_TAG, mCurrenciesContainer);
        intent.setAction(BROADCAST_ACTION);
        sendBroadcast(intent);
    }

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {

            URL url = null;
            try {
                url = new URL(mLink);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            InputStream in = null;
            HttpURLConnection urlConnection;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                // Why does it make a downloading work ?
                urlConnection.getRequestMethod();
                in = new BufferedInputStream(urlConnection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                mCurrenciesContainer = parse(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1);
            sendBroadcastDownloadingOver();
        }
    }

}
