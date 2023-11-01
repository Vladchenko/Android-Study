package com.example.vladislav.androidstudy.fragments.fragments_activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.activities.ParcelableActivity;

public class TwoFragmentsActivity extends Activity {

    /**
     * Start this activity
     * @param context to start activity
     * @return  intent that starts this activity
     */
    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, TwoFragmentsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments_two);
    }

}
