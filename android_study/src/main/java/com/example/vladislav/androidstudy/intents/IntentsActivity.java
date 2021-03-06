package com.example.vladislav.androidstudy.intents;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.intents.intentfiltercollision.IntentFilterCollisionInitialActivity;
import com.example.vladislav.androidstudy.receivers.BroadcastReceiverExample2;
import com.example.vladislav.androidstudy.receivers.BroadcastReceiverSimple;

import static com.example.vladislav.androidstudy.Utils.showToast;

public class IntentsActivity extends AppCompatActivity implements View.OnClickListener {

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiverSimple();
    BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiverExample2();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intents);
        Button button = (Button) findViewById(R.id.intent_button);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.intent_with_collision_button);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.intent_with_action_button);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.intent_with_data_same_action_button);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.intent_with_data_button);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.intent_google_maps_button);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.intent_send_broadcast_button);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.intent_web_link_button);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.intent_dial_button);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.intent_map_button);
        button.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(".receivers.BroadcastReceiverSimple");
        // Although this receiver is registered in an androidmanifest.xml, this program registration
        // overrides the manifest.
        registerReceiver(mBroadcastReceiver, intentFilter);

        // Following broadcast receiver is registered only programmatically.
        intentFilter = new IntentFilter();
        intentFilter.addAction(".receivers.BroadcastReceiverExample2");
        registerReceiver(mBroadcastReceiver2, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mBroadcastReceiver);
        unregisterReceiver(mBroadcastReceiver2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.intent_button: {

                // This method won't do, since doesn't resolve into any activity
                // One gets following error
                // RuntimeException: Unable to start activity ComponentInfo {....}:
                // ...ActivityNotFoundException: No Activity found to handle Intent {  }
                // startActivity(new Intent());

                Intent intent = new Intent(this, SimpleIntentActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.intent_with_collision_button: {
                Intent intent = new Intent(this, IntentFilterCollisionInitialActivity.class);
                startActivity(intent);
                break;
            }
            // Since there are 2 activities with the intent-filer that matches this intent, there
            // will be a chooser popped up asking to choose a needed intent.
            case R.id.intent_with_data_same_action_button: {
                Intent intent = new Intent("com.example.vladislav.androidstudy.intent.action.INTENT_WITH_ACTION_COMMON");
                startActivity(intent);
                break;
            }
            case R.id.intent_with_action_button: {
                // This intent will make android to look for an activity with action
                // "com.example.vladislav.androidstudy.intent.action.INTENT_WITH_ACTION"
                // and once it finds it, runs a component which intent-filter matches it.
                Intent intent = new Intent("com.example.vladislav.androidstudy.intent.action.INTENT_WITH_ACTION");
                startActivity(intent);
                break;
            }
            case R.id.intent_with_data_button: {
                // This intent will make android to look for an activity with action
                // "com.example.vladislav.androidstudy.intent.action.INTENT_WITH_ACTION"
                // and once it finds it, runs it.
                Intent intent = new Intent("com.example.vladislav.androidstudy.intent.action.INTENT_WITH_ACTION");
                intent.putExtra("Name", "Vlad");
                intent.putExtra("Lastname", "Yanchenko");
                startActivity(intent);
                break;
            }
            // Running an intent that calls google maps
            case R.id.intent_google_maps_button: {
                // Create a Uri from an intent string. Use the result to create an Intent.
                Uri gmmIntentUri = Uri.parse("google.streetview:cbll=46.414382,10.013988");
                // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                // Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");
                // Attempt to start an activity that can handle the Intent
                PackageManager packageManager = getPackageManager();
                if (mapIntent.resolveActivity(packageManager) != null) {
                    startActivity(mapIntent);
                } else {
                    showToast(this, "Intent cannot be resolved. Maybe google maps not present on the device.");
                }
                break;
            }
            case R.id.intent_web_link_button: {
                Intent intentWeb = new Intent(Intent.ACTION_VIEW);
                intentWeb.setData(Uri.parse("http://developer.android.com"));
                startActivity(intentWeb);
                break;
            }
            case R.id.intent_send_broadcast_button: {
                // Sending a broadcast
                Intent intent = new Intent();
                intent.setAction(".receivers.BroadcastReceiverExample2");
                intent.putExtra("message", "This is some message sent in broadcast");
                sendBroadcast(intent);
                break;
            }
            case R.id.intent_dial_button: {
                // Dialing a number
                Intent dialIntent = new Intent();
                dialIntent.setAction(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:88005553535"));
                startActivity(dialIntent);
                break;
            }
            case R.id.intent_map_button: {
                // Displaying a map
                Intent mapIntent = new Intent();
                mapIntent.setAction(Intent.ACTION_VIEW);
                mapIntent.setData(Uri.parse("geo:55.754283,37.62002"));
                // Attempt to start an activity that can handle the Intent
                PackageManager packageManager = getPackageManager();
                if (mapIntent.resolveActivity(packageManager) != null) {
                    startActivity(mapIntent);
                } else {
                    showToast(this, "Intent cannot be resolved. Maybe map app is not present in the phone.");
                }
                break;
            }
        }
    }

}
