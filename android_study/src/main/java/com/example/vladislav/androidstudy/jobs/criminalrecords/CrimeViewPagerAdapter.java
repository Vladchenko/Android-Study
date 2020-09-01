package com.example.vladislav.androidstudy.jobs.criminalrecords;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.vladislav.androidstudy.jobs.criminalrecords.ui.CriminalRecordFragment;

import java.util.ArrayList;
import java.util.List;

import static com.example.vladislav.androidstudy.jobs.criminalrecords.CriminalRecordsAdapter.DATE_FORMAT;

/**
 * Adapter for a ViewPager
 *
 * Created by Влад on 08.04.2018.
 */

public class CrimeViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Crime> mCrimes;

    // Maybe, this is not a good idea to get all the crimes here and much better to get only one by Id
    // But for now, we'll go on with it.
    public CrimeViewPagerAdapter(FragmentManager fragmentManager, ArrayList<Crime> crimes) {
        super(fragmentManager);
        mCrimes = crimes;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(CriminalRecordFragment.CRIME_ID, mCrimes.get(position).getId());
        bundle.putString(CriminalRecordFragment.CRIME_TITLE_KEY, mCrimes.get(position).getTitle());
        bundle.putString(CriminalRecordFragment.CRIME_DESCRIPTION_KEY, mCrimes.get(position).getDescription());
        bundle.putString(CriminalRecordFragment.CRIME_DATE_KEY, DATE_FORMAT.format(mCrimes.get(position).getDate()));
        bundle.putBoolean(CriminalRecordFragment.CRIME_SOLVED_KEY, mCrimes.get(position).isSolved());
        CriminalRecordFragment criminalRecordFragment = CriminalRecordFragment.newInstance();
        criminalRecordFragment.setArguments(bundle);
        return criminalRecordFragment;
    }

    @Override
    public int getCount() {
        return mCrimes.size();
    }

}
