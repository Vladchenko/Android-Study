package com.example.vladislav.androidstudy.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Влад on 27.11.2017.
 */

public class BroadcastReceiverSimple extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "BroadcastReceiverSimple received a message", Toast.LENGTH_SHORT)
                .show();
    }

}
