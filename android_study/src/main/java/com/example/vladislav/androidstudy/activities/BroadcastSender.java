package com.example.vladislav.androidstudy.activities;

import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.vladislav.androidstudy.services.ServicesDemo1Activity;

/**
 * Created by vladislav on 07.03.17.
 */
public class BroadcastSender {

    private static BroadcastSender ourInstance = new BroadcastSender();
    private static Context mContext;

    public static BroadcastSender getInstance(Context contextOuter) {
        mContext = contextOuter;
        return ourInstance;
    }

    private BroadcastSender() {
    }

    public void sendBroadcast(String string) {
        Intent intent = new Intent().
                setAction(ServicesDemo1Activity.BROADCAST_ID).
                putExtra(ServicesDemo1Activity.BROADCAST_ID,
                        mContext.getClass().getSimpleName() + ": " + string + "\n");
        switch (ServicesDemo1Activity.broadcastKind) {
            case LOCAL: {
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                break;
            }
            case GLOBAL: {
                mContext.sendBroadcast(intent);
                break;
            }
            case PRIORITIZED: {
                mContext.sendOrderedBroadcast(intent, null);
                break;
            }
        }
    }
}
