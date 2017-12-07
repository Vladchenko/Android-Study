package com.example.vladislav.androidstudy.activities.startfragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vladislav.androidstudy.R;

// This activity shows how a fragment could be started.
public class StartFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_fragment);
        startSupportFragment();
    }

    void startFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame_for_fragment, new SimpleFragment());
        transaction.commit();
    }

    void startSupportFragment() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame_for_fragment, new SimpleSupportFragment());
        transaction.commit();
    }
}
