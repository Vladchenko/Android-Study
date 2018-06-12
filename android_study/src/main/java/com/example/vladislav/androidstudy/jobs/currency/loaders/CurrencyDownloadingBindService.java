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

import com.example.vladislav.androidstudy.jobs.currency.ICallback;
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

public class CurrencyDownloadingBindService extends Service {

    private String mLink;
    private ICallback mFragment;
    private Looper mServiceLooper;
//    private ServiceHandler mServiceHandler;
    private final IBinder mBinder = new LocalBinder();
    private CurrenciesContainer mCurrenciesContainer = null;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        mLink = (String) intent.getExtras().get(sURL);

        // TODO - Work has to be done on a separate thread

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO Fix priority
        HandlerThread thread = new HandlerThread("ServiceStartArguments", 0);
        thread.start();
    }

    //returns the instance of the service
    public class LocalBinder extends Binder {
        public CurrencyDownloadingBindService getServiceInstance(){
            return CurrencyDownloadingBindService.this;
        }
    }

    //Here Activity register to the service as Callbacks client
    public void registerClient(ICallback fragment){
        mFragment = (ICallback)fragment;
        mFragment.loadedData(mCurrenciesContainer.getCurrenciesList());
    }

}
