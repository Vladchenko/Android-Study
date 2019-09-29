package com.example.vladislav.androidstudy.intents.intentfiltercollision;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.javarx2.example2.BooksActivity;

public class IntentFilterCollisionInitialActivity extends AppCompatActivity {

    // This activity keeps a button which is to start an activity using intent that matches the
    // intent filter of 2 activities, to see what happens.
    // This is called an intent-filter collision.

//     This action has to be the same for both the activities -
//      1. for IntentFilterCollisionActivity and
//
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
    static final String action = ".intentfiltercollision.IntentFilterCollisionActivity";

    public static Intent newIntent(Context context) {
        return new Intent(context, IntentFilterCollisionInitialActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_filter_collision_initial);
        getButton(R.id.run_activities_same_intents_button).setOnClickListener(
                setOnClickListener());
        getButton(R.id.run_activities_same_intents_button_2).setOnClickListener(
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
                // This action is the same as the one stated in a AndroidManifest.xml for a
                // IntentFilterCollisionActivity and IntentFilterCollisionActivity2 activities.
                intent.setAction(action);
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