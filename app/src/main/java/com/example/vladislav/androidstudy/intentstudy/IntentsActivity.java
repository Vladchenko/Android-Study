package com.example.vladislav.androidstudy.intentstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vladislav.androidstudy.R;

public class IntentsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intents);
        Button button = (Button) findViewById(R.id.intent_button);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.intent_with_data_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.intent_button: {
                Intent intent = new Intent (this, SimpleIntentActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.intent_with_data_button: {
                // This intent will make android to look for an activity with action
                // "com.example.vladislav.androidstudy.intent.action.INTENT_WITH_ACTION"
                // and once it finds it, runs it.
                Intent intent = new Intent ("com.example.vladislav.androidstudy.intent.action.INTENT_WITH_ACTION");
                startActivity(intent);
                break;
            }
        }
    }
}
