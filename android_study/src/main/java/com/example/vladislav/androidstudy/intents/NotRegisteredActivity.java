package com.example.vladislav.androidstudy.intents;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vladislav.androidstudy.R;

/**
 * To show behaviour of a not registered activity.
 */
public class NotRegisteredActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_intent);
    }
}
