package com.example.vladislav.androidstudy.jobs.currency;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.widget.FrameLayout;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.jobs.currency.fragments.CurrencyRXLoaderFragment;
import com.example.vladislav.androidstudy.jobs.currency.workerthread.CurrencyDownloadingFragment;
import com.example.vladislav.androidstudy.jobs.currency.fragments.CurrencyAsyncTaskFragment;

public class CurrencyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        FrameLayout mFrameLayout = findViewById(R.id.currency_frame_layout);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(CurrencyAsyncTaskFragment.TAG);
        if (fragment == null) {
//            fragment = new CurrencyAsyncTaskFragment();
//            fragment = new CurrencyAsyncTaskLoaderFragment();
//            fragment = new CurrencyStartServiceFragment();
//            fragment = new CurrencyBindServiceFragment();
//            fragment = new CurrencyRXLoaderFragment();
            fragment = new CurrencyDownloadingFragment();

            fragmentManager.beginTransaction().add(
                    mFrameLayout.getId(), fragment, CurrencyAsyncTaskFragment.TAG)
                    .commit();
        }

    }
    
}
