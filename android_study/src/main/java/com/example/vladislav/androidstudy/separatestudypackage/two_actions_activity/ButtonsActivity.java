package com.example.vladislav.androidstudy.separatestudypackage.two_actions_activity;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vladislav.androidstudy.R;

public class ButtonsActivity extends AppCompatActivity {

    public static final String TIME_ACTION = "com.example.vladislav.androidstudy.separatestudypackage.time";
    public static final String DATE_ACTION = "com.example.vladislav.androidstudy.separatestudypackage.date";
    private Button mTimeButton;
    private Button mDateButton;

    /**
     * Start this activity
     * @param context to start activity
     * @return  intent that starts this activity
     */
    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, ButtonsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons);
        mTimeButton = findViewById(R.id.time_button);
        mDateButton = findViewById(R.id.date_button);
        setButtonsClickListeners();
    }

    // Sets click listeners to a buttons
    private void setButtonsClickListeners() {
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(TIME_ACTION);
                startActivity(intent);
            }
        });
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(DATE_ACTION);
                startActivity(intent);
            }
        });
    }
}
