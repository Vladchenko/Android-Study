package com.example.vladislav.androidstudy.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Влад on 26.11.2017.
 */

public class BroadcastReceiverExample extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, ".receivers.BroadcastReceiverExample:    "
                + intent.getStringExtra("message"), Toast.LENGTH_SHORT).show();
    }
}
