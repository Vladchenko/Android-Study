package com.example.vladislav.androidstudy.fragments.start_fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;

import com.example.vladislav.androidstudy.R;

import static com.example.vladislav.androidstudy.fragments.start_fragment.SimpleFragment.FRAGMENT_TAG;

/**
 * This activity may display:
 * - android.app.Fragment;
 * or
 * - android.support.v4.app.Fragment;
 */
public class SimpleFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_fragment);
                startFragment();
//        startSupportFragment();
    }

    void startFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        SimpleFragment fragment = (SimpleFragment)fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new SimpleFragment();
        }
        transaction.add(R.id.frame_for_fragment, fragment, FRAGMENT_TAG);
        transaction.commit();
    }

    // "support" means the one that has a backward compatibility with an older versions of android
    // API
    void startSupportFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        SimpleSupportFragment fragment = (SimpleSupportFragment)fragmentManager
                .findFragmentByTag(SimpleSupportFragment.FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new SimpleSupportFragment();
        }
        transaction.add(R.id.frame_for_fragment, new SimpleSupportFragment());
        transaction.commit();
    }
}
