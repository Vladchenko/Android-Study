package com.example.vladislav.androidstudy.jobs.criminalrecords.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.jobs.criminalrecords.Crime;
import com.example.vladislav.androidstudy.jobs.criminalrecords.CrimeViewPagerAdapter;

import java.util.ArrayList;

import static com.example.vladislav.androidstudy.jobs.criminalrecords.ui.CriminalRecordListFragment.CRIMES_LIST;
import static com.example.vladislav.androidstudy.jobs.criminalrecords.ui.CriminalRecordListFragment.CRIME_POSITION;

/**
 * Fragment to display a viewpager that is to hold a CriminalRecordFragment instances
 * <p>
 * A simple {@link Fragment} subclass.
 */
public class CrimesViewPagerFragment extends Fragment {

    public static final String FRAGMENT_TAG = CriminalRecordFragment.class.getSimpleName();

//    private DBHelper mDbHelper;
    private ArrayList<Crime> mCrimes;
    private int position;

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
            mCrimes = args.getParcelableArrayList(CRIMES_LIST);
            position = args.getInt(CRIME_POSITION);
        } else {
            mCrimes = new ArrayList<>();
            mCrimes.add(new Crime());
        }
        View view = inflater.inflate(R.layout.fragment_crimes_view_pager, container, false);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager_crime);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.setAdapter(new CrimeViewPagerAdapter(getActivity().getSupportFragmentManager(),
                mCrimes));
        viewPager.setCurrentItem(position);
        return view;
    }

}
