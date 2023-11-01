package com.example.vladislav.androidstudy.intents;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.intents.intentfiltercollision.IntentFilterCollisionInitialActivity;
import com.example.vladislav.androidstudy.jobs.simple_jobs.LayoutingActivity;
import com.example.vladislav.androidstudy.receivers.BroadcastReceiverExample2;
import com.example.vladislav.androidstudy.receivers.BroadcastReceiverSimple;

import static com.example.vladislav.androidstudy.Utils.showToast;

public class IntentsActivity extends AppCompatActivity {

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiverSimple();
    BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiverExample2();

    /**
     * Start this activity
     * @param context to start activity
     * @return  intent that starts this activity
     */
    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, IntentsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intents);
        Button button = findViewById(R.id.not_registered_activity_button);
        // This method won't do, since doesn't resolve into any activity
        // One gets following error
        // RuntimeException: Unable to start activity ComponentInfo {....}:
        // ...ActivityNotFoundException: No Activity found to handle Intent {  }
        button.setOnClickListener(v -> startActivity(new Intent(this, NotRegisteredActivity.class)));
        button = findViewById(R.id.intent_button);
        button.setOnClickListener(v -> startActivity(new Intent(this, SimpleIntentActivity.class)));
        button = findViewById(R.id.intent_with_collision_button);
        button.setOnClickListener(v -> startActivity(new Intent(this, IntentFilterCollisionInitialActivity.class)));
        button = findViewById(R.id.intent_with_action_button);
        // This intent will make android to look for an activity with action
        // "com.example.vladislav.androidstudy.intent.action.INTENT_WITH_ACTION"
        // and once it finds it, starts it.
        button.setOnClickListener(v -> startActivity(new Intent("com.example.vladislav.androidstudy.intent.action.INTENT_WITH_ACTION")));
        button = findViewById(R.id.intent_with_data_same_action_button);
        button.setOnClickListener(v -> startActivity(new Intent("com.example.vladislav.androidstudy.intent.action.INTENT_WITH_ACTION_COMMON")));
        button = findViewById(R.id.intent_with_data_button);
        // This intent will make android to look for an activity with action
        // "com.example.vladislav.androidstudy.intent.action.INTENT_WITH_ACTION"
        // and once it finds it, runs it.
        button.setOnClickListener(v -> {
            Intent intent = new Intent("com.example.vladislav.androidstudy.intent.action.INTENT_WITH_ACTION");
            intent.putExtra("Name", "Vlad");
            intent.putExtra("Lastname", "Yanchenko");
            startActivity(intent);
        });
        button = findViewById(R.id.intent_google_maps_button);
        button.setOnClickListener(v -> {
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
        });
        button = findViewById(R.id.intent_send_broadcast_button);
        button.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(".receivers.BroadcastReceiverExample2");
            intent.putExtra("message", "This is some message sent in broadcast");
            sendBroadcast(intent);
        });
        button = findViewById(R.id.intent_web_link_button);
        button.setOnClickListener(v -> {
            Intent intentWeb = new Intent(Intent.ACTION_VIEW);
            intentWeb.setData(Uri.parse("http://developer.android.com"));
            startActivity(intentWeb);
        });
        button = findViewById(R.id.intent_dial_button);
        button.setOnClickListener(v -> {
            Intent dialIntent = new Intent();
            dialIntent.setAction(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:88005553535"));
            startActivity(dialIntent);
        });
        button = findViewById(R.id.intent_map_button);
        button.setOnClickListener(v -> {
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
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(".receivers.BroadcastReceiverSimple");
        // Although this receiver is registered in an androidmanifest.xml, following registration
        // overrides the manifest's one.
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
}
