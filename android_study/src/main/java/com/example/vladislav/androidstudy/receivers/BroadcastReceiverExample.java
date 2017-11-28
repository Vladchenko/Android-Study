package com.example.vladislav.androidstudy.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Влад on 26.11.2017.
 */

public class BroadcastReceiverExample extends BroadcastReceiver {

    // Action of an intent that sends broadcast message to this receiver should have the same value
    // as an intent-filter's action of this broadcast receiver in an androidmanifest file.
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, ".receivers.BroadcastReceiverExample:    "
                + intent.getStringExtra("message"), Toast.LENGTH_SHORT).show();
    }
}
