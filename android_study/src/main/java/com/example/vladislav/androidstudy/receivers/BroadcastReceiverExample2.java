package com.example.vladislav.androidstudy.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Влад on 27.11.2017.
 */

public class BroadcastReceiverExample2 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "BroadcastReceiverExample2 received a message",
                Toast.LENGTH_SHORT).show();
    }

}
