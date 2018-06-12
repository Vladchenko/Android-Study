package com.example.vladislav.androidstudy.jobs.currency;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.jobs.currency.fragments.CurrencyAsyncTaskFragment;
import com.example.vladislav.androidstudy.jobs.currency.fragments.CurrencyBindServiceFragment;
import com.example.vladislav.androidstudy.jobs.currency.fragments.CurrencyStartServiceFragment;

public class CurrencyActivity extends AppCompatActivity {

    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        mFrameLayout = (FrameLayout)findViewById(R.id.currency_frame_layout);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(CurrencyAsyncTaskFragment.TAG);
        if (fragment == null) {
//            fragment = new CurrencyAsyncTaskFragment();
//            fragment = new CurrencyAsyncTaskLoaderFragment();
//            fragment = new CurrencyStartServiceFragment();
            fragment = new CurrencyBindServiceFragment();
            fragmentManager.beginTransaction().add(
                    mFrameLayout.getId(), fragment, CurrencyAsyncTaskFragment.TAG).commit();
        }

    }
    
}
