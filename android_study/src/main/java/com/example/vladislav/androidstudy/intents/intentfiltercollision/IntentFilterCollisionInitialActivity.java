package com.example.vladislav.androidstudy.intents.intentfiltercollision;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vladislav.androidstudy.R;

/**
 * This activity keeps a button which is to start an activity using intent that matches
 * the intent filter of 2 activities, to see what happens. This is called an intent-filter collision.
 */
public class IntentFilterCollisionInitialActivity extends AppCompatActivity {

//     For collision to happen, action has to be the same for both the activities -
//      1. for IntentFilterCollisionActivity and
//      2. for IntentFilterCollisionActivity2 in the AndroidManifest.xml file, i.e.
//     <activity android:name=".intentfiltercollision.IntentFilterCollisionActivity">
//          <intent-filter>
//               <action android:name=".intentfiltercollision.IntentFilterCollisionActivity"> </action>
//               <category android:name="android.intent.category.DEFAULT"> </category>
//          </intent-filter>
//     </activity>
//     <activity android:name=".intentfiltercollision.IntentFilterCollisionActivity2">
//          <intent-filter>
//              <action android:name=".intentfiltercollision.IntentFilterCollisionActivity"> </action>
//              <category android:name="android.intent.category.DEFAULT"> </category>
//          </intent-filter>
//     </activity>
//     When collision occurs, bottomsheet is to popup
    static final String ACTION = ".intentfiltercollision.IntentFilterCollisionActivity";

    public static Intent newIntent(Context context) {
        return new Intent(context, IntentFilterCollisionInitialActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_filter_collision_initial);
        getButton(R.id.run_activities_same_intents_button).setOnClickListener(setOnClickListener());
        getButton(R.id.run_activities_same_intents_button_2).setOnClickListener(setOnClickListenerWithChooser());

    }

    private Button getButton(int buttonId) {
        return (Button) findViewById(buttonId);
    }

    private View.OnClickListener setOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                // This action is the same as the one stated in a Manifest.xml for a
                // IntentFilterCollisionActivity and IntentFilterCollisionActivity2 activities.
                intent.setAction(ACTION);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener setOnClickListenerWithChooser() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                // This action is the same as the one stated in a AndroidManifest.xml for a
                // IntentFilterCollisionActivity and IntentFilterCollisionActivity2 activities.
                intent.setAction(ACTION);
                // Category attribute is not set and it is considered to be
                // <category android:name="android.intent.category.DEFAULT"> </category> by default
                // and be the same for both the activities -
                // IntentFilterCollisionActivity and IntentFilterCollisionActivity2.
                // It is explained in
                // https://stackoverflow.com/questions/5727828/what-is-the-purpose-of-android-intent-category-default
                startActivity(Intent.createChooser(intent, "This text is from a custom chooser"));
            }
        };
    }
}