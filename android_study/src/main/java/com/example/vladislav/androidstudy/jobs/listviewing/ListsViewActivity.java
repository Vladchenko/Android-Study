package com.example.vladislav.androidstudy.jobs.listviewing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.jobs.listviewing.spacestars.ListViewStarsFragment;
import com.example.vladislav.androidstudy.jobs.simple_jobs.LayoutingActivity;

/**
 * This activity demonstrates operating an ArrayAdapter.
 * Logic of ArrayAdapter operating is placed in a ListViewPeopleFragment and ListViewStarsFragment.
 * ArrayAdapter is able to operate with only 1 TextView in an adapter item.
 */
public class ListsViewActivity extends AppCompatActivity {

    private final String mFragmentTag = ListViewPeopleFragment.class.toString();

    /**
     * Start this activity
     * @param context to start activity
     * @return  intent that starts this activity
     */
    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, ListsViewActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists_viewing);
        addFragment();
    }

    // Putting fragment to activity's layout
    private void addFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(mFragmentTag);
        if (fragment == null) {
            fragment = new ListViewStarsFragment();
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.list_view_fragment_container, fragment, mFragmentTag);
        fragmentTransaction.commit();
    }
}