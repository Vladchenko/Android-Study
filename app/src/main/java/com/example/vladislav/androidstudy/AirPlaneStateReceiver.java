package com.example.vladislav.androidstudy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by vladislav on 10.03.17.
 *
 * This broadcast receiver makes a toast to appear whenever an airplane state is changed. This is
 * true while this app is still present in a RAM.
 *
 * To perform this operation, one has to run this app, go to settings and change an airplane mode.
 */

public class AirPlaneStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Airplane mode state changed",Toast.LENGTH_SHORT).show();
    }
}
