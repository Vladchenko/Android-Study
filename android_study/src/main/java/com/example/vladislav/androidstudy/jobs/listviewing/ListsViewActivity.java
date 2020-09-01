package com.example.vladislav.androidstudy.jobs.listviewing;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.jobs.listviewing.spacestars.ListViewStarsFragment;

/**
 * This activity demonstrates operating an ArrayAdapter.
 * Logic of ArrayAdapter operating is placed in a ListViewPeopleFragment and ListViewStarsFragment.
 * ArrayAdapter is able to operate with only 1 TextView in an adapter item.
 */
public class ListsViewActivity extends AppCompatActivity {

    private Fragment mFragment;
    private String mFragmentTag = ListViewPeopleFragment.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists_viewing);
        addFragment();
    }

    // Putting fragment to activity's layout
    private void addFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        mFragment = fragmentManager.findFragmentByTag(mFragmentTag);
        if (mFragment == null) {
            mFragment = new ListViewStarsFragment();
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.list_view_fragment_container, mFragment, mFragmentTag);
        fragmentTransaction.commit();
    }
}