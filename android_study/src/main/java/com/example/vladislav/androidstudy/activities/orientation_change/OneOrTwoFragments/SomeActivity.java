package com.example.vladislav.androidstudy.activities.orientation_change.OneOrTwoFragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.vladislav.androidstudy.R;

/**
 * The idea is to make a portrait mode to display one fragment and two in a landscape mode.
 */
public class SomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_some);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Configuration newConfig = getResources().getConfiguration();
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            addFragments(true);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            addFragments(false);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // The problem here - is that android doesn't recover the state of the app. In this callback one
    // has to make it on its own.
    // For instance, android has to pick a right layout for portrait (layout folder) and landscape
    // modes (layout-land folder), but it doesn't do this in this case, so both layouts have to be
    // put to a layout folder . It also doesn't recover the activity.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_some_land);
            addFragments(true);
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_some);
            addFragments(false);
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }

    }

    private void addFragments(boolean both) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment1 fragment1 = (Fragment1)fragmentManager.findFragmentByTag("fragment1");
        Fragment2 fragment2 = (Fragment2)fragmentManager.findFragmentByTag("fragment2");
        if (fragment1 != null) {
            fragmentTransaction.remove(fragment1);
            fragment1 = null;
        }
        if (fragment2 != null) {
            fragmentTransaction.remove(fragment2);
            fragment2 = null;
        }
        if (fragment1 == null) {
            fragment1 = new Fragment1();
            fragmentTransaction.add(R.id.fragment1_layout, fragment1, "fragment1");
            fragmentTransaction.commit();
        }
        if (both) {
            if (fragment2 == null) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragment2 = new Fragment2();
                fragmentTransaction.add(R.id.fragment2_layout, fragment2, "fragment2");
                fragmentTransaction.commit();
            }
        }

    }
}
