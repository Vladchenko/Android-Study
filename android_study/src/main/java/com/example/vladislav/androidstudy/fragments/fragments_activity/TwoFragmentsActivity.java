package com.example.vladislav.androidstudy.fragments.fragments_activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.activities.ParcelableActivity;

public class TwoFragmentsActivity extends Activity {

    public static Intent newIntent(Context context) {
        return new Intent(context, TwoFragmentsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments_two);
    }

}
