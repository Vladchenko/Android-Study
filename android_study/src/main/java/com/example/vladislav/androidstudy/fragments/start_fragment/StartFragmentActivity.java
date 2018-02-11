package com.example.vladislav.androidstudy.fragments.start_fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vladislav.androidstudy.R;

// This activity shows how a fragment could be started.
public class StartFragmentActivity extends AppCompatActivity {

    // Used as a key to put and get a string from bundle
    public static final String mKey = "key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_fragment);
//        startFragment();
//        startSupportFragment();
    }

    void startFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame_for_fragment, new SimpleFragment());
        transaction.commit();
    }

    // "support" means the one that has a backward compatibility with an older versiona of android
    // API
    void startSupportFragment() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame_for_fragment, new SimpleSupportFragment());
        transaction.commit();
    }

}
