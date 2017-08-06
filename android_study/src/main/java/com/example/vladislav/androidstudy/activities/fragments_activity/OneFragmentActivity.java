package com.example.vladislav.androidstudy.activities.fragments_activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.example.vladislav.androidstudy.R;

public class OneFragmentActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mFragmentManager.findFragmentById(R.id.one_fragment_container)
        mFragmentManager = getFragmentManager();
        setContentView(R.layout.activity_one_fragment);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.one_fragment_container, new Fragment1());
        fragmentTransaction.commit();
    }

}
