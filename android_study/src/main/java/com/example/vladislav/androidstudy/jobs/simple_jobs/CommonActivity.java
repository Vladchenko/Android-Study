package com.example.vladislav.androidstudy.jobs.simple_jobs;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.activities.dynamic_layout.DynamicLayoutFragment;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This activity is to display a fragment.
 * It is common to a several fragments.
 */
public class CommonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        } else {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Actionbar is absent");
        }

//        addImageFragment();
        addWidgetsFragment();
//        addDynamicLayoutFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                // Or one could use NavUtils.navigateUpFromSameTask(this);, but in this case,
                // one needs to define a parent activity -
                // https://developer.android.com/training/implementing-navigation/ancestral.html#SpecifyParent
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addImageFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(ImageFragment.FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new ImageFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, fragment, ImageFragment.FRAGMENT_TAG);
            fragmentTransaction.commit();
        }
    }

    private void addWidgetsFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(WidgetsFragment.FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new WidgetsFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, fragment, WidgetsFragment.FRAGMENT_TAG);
            fragmentTransaction.commit();
        }
    }

    private void addDynamicLayoutFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(DynamicLayoutFragment.FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new DynamicLayoutFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, fragment, DynamicLayoutFragment.FRAGMENT_TAG);
            fragmentTransaction.commit();
        }
    }
}
