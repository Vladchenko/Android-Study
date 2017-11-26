package com.example.vladislav.androidstudy.intentstudy;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.intentstudy.intentfiltercollision.IntentFilterCollisionActivity;
import com.example.vladislav.androidstudy.intentstudy.intentfiltercollision.IntentFilterCollisionInitialActivity;
import com.example.vladislav.androidstudy.logic.Utils;

public class IntentsActivity extends AppCompatActivity implements View.OnClickListener {

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.intent_button: {
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
                    Utils.showToast(this, "Intent cannot be resolved");
                }
            }
            case R.id.intent_send_broadcast_button: {
                Intent intent = new Intent();
                intent.setAction("com.toxy.LOAD_URL");
                intent.putExtra("message", "This is some message sent in broadcast");
                sendBroadcast(intent);
            }
        }
    }
}
