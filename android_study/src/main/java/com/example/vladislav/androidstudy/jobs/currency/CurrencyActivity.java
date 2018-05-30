package com.example.vladislav.androidstudy.jobs.currency;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.vladislav.androidstudy.R;

public class CurrencyActivity extends AppCompatActivity {

    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        mFrameLayout = (FrameLayout)findViewById(R.id.currency_frame_layout);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(CurrencyFragment.TAG);
        if (fragment == null) {
            fragment = new CurrencyFragment();
            fragmentManager.beginTransaction().add(mFrameLayout.getId(), fragment, CurrencyFragment.TAG).commit();
        }

    }
}
