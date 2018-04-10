package com.example.vladislav.androidstudy.jobs.criminalrecords.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.jobs.criminalrecords.Crime;
import com.example.vladislav.androidstudy.jobs.criminalrecords.CrimeViewPagerAdapter;
import com.example.vladislav.androidstudy.jobs.criminalrecords.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment to display a viewpager that is to hold a CriminalRecordFragment instances
 * <p>
 * A simple {@link Fragment} subclass.
 */
public class CrimesViewPagerFragment extends Fragment {

    public static final String FRAGMENT_TAG = CriminalRecordFragment.class.getSimpleName();

    private DBHelper mDbHelper;
    private ArrayList<Crime> mCrimes;

    public CrimesViewPagerFragment() {
    }

    public static CrimesViewPagerFragment newInstance() {
        CrimesViewPagerFragment myFragment = new CrimesViewPagerFragment();
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mCrimes = args.getParcelableArrayList("Crimes");
        } else {
            mCrimes = new ArrayList<>();
            mCrimes.add(new Crime());
        }
        View view = inflater.inflate(R.layout.fragment_crimes_view_pager, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager_crime);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.setAdapter(new CrimeViewPagerAdapter(getActivity().getSupportFragmentManager(),
                mCrimes));
        return view;
    }

}
