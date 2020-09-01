package com.example.vladislav.androidstudy.services.demo1;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import androidx.annotation.Nullable;
import android.util.Log;

import com.example.vladislav.androidstudy.activities.BroadcastSender;
import com.example.vladislav.androidstudy.services.ServicesDemo1Activity;

import java.io.FileDescriptor;

/**
 * An {@link BothIntentedService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class BothIntentedService extends IntentService {

    private BroadcastSender mBroadcastSender;

    public BothIntentedService() {
        super("IntendedService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Instantiating a broadcast sender;
        mBroadcastSender = BroadcastSender.getInstance(this);
        mBroadcastSender.sendBroadcast("onCreate");
        Log.i("BothIntentedService","onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Without invoking super, method onHandleIntent(...) isn't called.
        super.onStartCommand(intent, flags, startId);
        mBroadcastSender.sendBroadcast("onStartCommand");
        Log.i("BothIntentedService","onStartCommand");
        return START_NOT_STICKY;
//        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mBroadcastSender.sendBroadcast("onHandleIntent start");
        Log.i("BothIntentedService", "onHandleIntent start");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mBroadcastSender.sendBroadcast("onHandleIntent end");
        Log.i("BothIntentedService", "`onHandleIntent end");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBroadcastSender.sendBroadcast("onDestroy");
        mBroadcastSender.sendBroadcast("-----------------------");
        Log.i("BothIntentedService","onDestroy");
        Log.i("-","-----------------------");
        ServicesDemo1Activity.bounded = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mBroadcastSender.sendBroadcast("onBind");
        Log.i("BothIntentedService","onBind");
        ServicesDemo1Activity.bounded = true;
//        Log.i("StartedSimpleService", "StartedSimpleService is bound to - " + intent.getAction());
        return new IBinder() {
            @Override
            public String getInterfaceDescriptor() throws RemoteException {
                return null;
            }

            @Override
            public boolean pingBinder() {
                return false;
            }

            @Override
            public boolean isBinderAlive() {
                return false;
            }

            @Override
            public IInterface queryLocalInterface(String descriptor) {
                return null;
            }

            @Override
            public void dump(FileDescriptor fd, String[] args) throws RemoteException {

            }

            @Override
            public void dumpAsync(FileDescriptor fd, String[] args) throws RemoteException {

            }

            @Override
            public boolean transact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                return false;
            }

            @Override
            public void linkToDeath(DeathRecipient recipient, int flags) throws RemoteException {

            }

            @Override
            public boolean unlinkToDeath(DeathRecipient recipient, int flags) {
                return false;
            }
        };
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mBroadcastSender.sendBroadcast("onUnbind");
        Log.i("BothIntentedService","onUnbind");
        return super.onUnbind(intent);
    }

}
