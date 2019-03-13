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

import com.example.vladislav.androidstudy.Utils;
import com.example.vladislav.androidstudy.jobs.currency.ICallback;
import com.example.vladislav.androidstudy.jobs.currency.beans.CurrenciesContainer;

import static com.example.vladislav.androidstudy.jobs.currency.fragments.CurrencyStartServiceFragment.sURL;

public class CurrencyDownloadingBindService extends Service {

    private String mLink;
    private ICallback mFragment;
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private final IBinder mBinder = new LocalBinder();
    private CurrenciesContainer mCurrenciesContainer = null;
    /**
     * Target we publish for clients to send messages to
     * IncomingHandler.
     */
//    final Messenger mMessenger = new Messenger(
//            new ServiceHandler()
//    );


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        mLink = (String) intent.getExtras().get(sURL);
        Utils.showToast(this, "Service bound");

        // TODO - Work has to be done on a separate thread

        Message msg = mServiceHandler.obtainMessage();
//        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

//        try {
//            // Imitating some work
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        URL url = null;
//        try {
//            url = new URL(mLink);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        InputStream in = null;
//        HttpURLConnection urlConnection;
//        try {
//            urlConnection = (HttpURLConnection) url.openConnection();
//            // Why does it make a downloading work ?
//            urlConnection.getRequestMethod();
//            in = new BufferedInputStream(urlConnection.getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            mCurrenciesContainer = parse(in);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO Fix priority
        HandlerThread thread = new HandlerThread("ServiceBindArguments", 0);
        thread.start();
        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new CurrencyDownloadingBindService.ServiceHandler(mServiceLooper);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Utils.showToast(this, "Service unbound");
        return super.onUnbind(intent);
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

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {

            mCurrenciesContainer = new CurrencyDownloader(mLink).getLoadedCurrencies();
//            // Stop the service using the startId, so that we don't stop
//            // the service in the middle of handling another job
//            stopSelf();
//            sendBroadcastDownloadingOver();
        }
    }

}
