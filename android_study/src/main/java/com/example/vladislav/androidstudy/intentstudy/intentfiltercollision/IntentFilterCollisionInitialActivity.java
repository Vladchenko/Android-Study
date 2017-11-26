package com.example.vladislav.androidstudy.intentstudy.intentfiltercollision;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vladislav.androidstudy.R;

public class IntentFilterCollisionInitialActivity extends AppCompatActivity {

    // This activity keeps a button which is to start an activity using intent which matches the
    // intent filter of 2 activity, to see what happens.
    // This is called an intent-filter collision.

//     This action has to be the same for both the activities -
//     1. for IntentFilterCollisionActivity and
//     2. for IntentFilterCollisionActivity2 in the AndroidManifest.xml file, i.e.
//     <activity android:name=".intentfiltercollision.IntentFilterCollisionActivity">
//          <intent-filter>
//               <action android:name=".intentfiltercollision.IntentFilterCollisionActivity"> </action>
//               <category android:name="android.intent.category.DEFAULT"> </category>
//          </intent-filter>
//          </activity>
//          <activity android:name=".intentfiltercollision.IntentFilterCollisionActivity2">
//              <intent-filter>
//                  <action android:name=".intentfiltercollision.IntentFilterCollisionActivity"> </action>
//                  <category android:name="android.intent.category.DEFAULT"> </category>
//              </intent-filter>
//          </activity>
    static final String action = ".intentfiltercollision.IntentFilterCollisionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_filter_collision_initial);
        getButton(R.id.run_activities_same_intents_button).setOnClickListener(
                setOnClickListenerWithChooser());
    }

    private Button getButton(int buttonId) {
        return (Button)findViewById(buttonId);
    }

    private View.OnClickListener setOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                // This action is the same as the one stated in a Manifest.xml for a
                // IntentFilterCollisionActivity and IntentFilterCollisionActivity2 activities.
                intent.setAction(action);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener setOnClickListenerWithChooser() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                // This action is the same as the one stated in a Manifest.xml for a
                // IntentFilterCollisionActivity and IntentFilterCollisionActivity2 activities.
                intent.setAction(action);
                startActivity(Intent.createChooser(intent, "This text is from a custom chooser"));
            }
        };
    }
}