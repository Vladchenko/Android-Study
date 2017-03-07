package com.example.vladislav.androidstudy;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by vladislav on 07.03.17.
 */
public class BroadcastSender {

    private static BroadcastSender ourInstance = new BroadcastSender();
    private static Context context;
    private static boolean isLocal;

    public static BroadcastSender getInstance(Context contextOuter, boolean local) {
        context = contextOuter;
        isLocal = local;
        return ourInstance;
    }

    private BroadcastSender() {
    }

    public void sendBroadcast(String string) {
        if (isLocal) {
            LocalBroadcastManager.getInstance(context).sendBroadcast(
                    new Intent().
                            setAction(ServicesActivity.BROADCAST_ID).
                            putExtra(ServicesActivity.BROADCAST_ID,
                                    context.getClass().getSimpleName() + ": " + string + "\n"));
        } else {
            context.sendBroadcast(
                    new Intent().
                            setAction(ServicesActivity.BROADCAST_ID).
                            putExtra(ServicesActivity.BROADCAST_ID,
                                    context.getClass().getSimpleName() + ": " + string + "\n"));
        }
    }
}
