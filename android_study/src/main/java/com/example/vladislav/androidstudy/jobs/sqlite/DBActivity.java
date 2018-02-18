package com.example.vladislav.androidstudy.jobs.sqlite;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vladislav.androidstudy.R;

public class DBActivity extends AppCompatActivity {

    private final String fragmentTag = "SQLiteFragment";
    private Fragment mSQLiteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        addSQLiteFragment();
    }

    void addSQLiteFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mSQLiteFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (mSQLiteFragment == null) {
            mSQLiteFragment = new SQLiteFragment();
        }
        fragmentTransaction.add(R.id.fragment_container, mSQLiteFragment);
        fragmentTransaction.commit();
    }

}
