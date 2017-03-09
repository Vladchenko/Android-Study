package com.example.vladislav.androidstudy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by vladislav on 07.03.17.
 */
public class BroadcastSender {

    private static BroadcastSender ourInstance = new BroadcastSender();
    private static Context context;

    public static BroadcastSender getInstance(Context contextOuter) {
        context = contextOuter;
        return ourInstance;
    }

    private BroadcastSender() {
    }

    public void sendBroadcast(String string) {
        Intent intent = new Intent().
                setAction(ServicesActivity.BROADCAST_ID).
                putExtra(ServicesActivity.BROADCAST_ID,
                        context.getClass().getSimpleName() + ": " + string + "\n");
        switch (ServicesActivity.broadcastKind) {
            case LOCAL: {
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                break;
            }
            case GLOBAL: {
                context.sendBroadcast(intent);
                break;
            }
            case PRIORITIZED: {
                context.sendOrderedBroadcast(intent, ServicesActivity.BROADCAST_ID);
                break;
            }
        }
    }
}
