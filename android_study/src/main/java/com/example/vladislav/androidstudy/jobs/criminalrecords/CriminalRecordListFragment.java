package com.example.vladislav.androidstudy.jobs.criminalrecords;


import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;

import java.util.List;

import static com.example.vladislav.androidstudy.jobs.criminalrecords.CriminalRecordFragment.CRIME_DATE_KEY;
import static com.example.vladislav.androidstudy.jobs.criminalrecords.CriminalRecordFragment.CRIME_DESCRIPTION_KEY;
import static com.example.vladislav.androidstudy.jobs.criminalrecords.CriminalRecordFragment.CRIME_SOLVED_KEY;
import static com.example.vladislav.androidstudy.jobs.criminalrecords.CriminalRecordFragment.CRIME_TITLE_KEY;
import static com.example.vladislav.androidstudy.jobs.criminalrecords.CriminalRecordsActivity.DATABASE_NAME;
import static com.example.vladislav.androidstudy.jobs.criminalrecords.CriminalRecordsAdapter.DATE_FORMAT;

/**
 * Fragment representing a list of a crimes
 */
public class CriminalRecordListFragment extends Fragment implements ICrimeItemClickListener {

    public static final String FRAGMENT_TAG = CriminalRecordListFragment.class.getSimpleName();

    private FloatingActionButton mActionButton;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mEmptyListTextView;

    private DBHelper mDbHelper;

    public static CriminalRecordListFragment newInstance() { //int someInt
        CriminalRecordListFragment fragment = new CriminalRecordListFragment();
        // Dropping a database in case a previous one is obsolete
//        mDbHelper.dropTable(mDbHelper.getWritableDatabase());
//        Bundle args = new Bundle();
//        args.putInt("someInt", someInt);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDbHelper = new DBHelper(getActivity(), DATABASE_NAME);
        // Dropping a table to remove all the entries at once
//        mDbHelper.dropTable(mDbHelper.getReadableDatabase());
        // Creating a table if it doesn't exist
        mDbHelper.createTableWithColumns(mDbHelper.getWritableDatabase());
        View view = inflater.inflate(R.layout.fragment_criminal_record_list, container, false);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActionButton = (FloatingActionButton) view.findViewById(R.id.add_crime_button);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.crime_records_recycler_view);
        mEmptyListTextView = (TextView) view.findViewById(R.id.empty_crime_list_text_view);
        displayEmptyListMessage();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCriminalRecordFragment(true, null);
            }
        });
        setupRecyclerView();
        setupAddButtonForegroundColor(R.color.color_white);
    }

    // Callback method. It is invoked when a recyclerview's crime item clicked.
    @Override
    public void onCrimeItemClick(String crimeId) {
        // Get a crime entry on such id from DB and launch CriminalRecordFragment with this entry
        // in bundle.
        Crime crime = mDbHelper.getCrimeById(mDbHelper.getReadableDatabase(), crimeId);
        if (crime != null) {
            Bundle bundle = new Bundle();
            bundle.putString(CRIME_TITLE_KEY, crime.getTitle());
            bundle.putString(CRIME_DESCRIPTION_KEY, crime.getDescription());
            bundle.putString(CRIME_DATE_KEY, crime.getDate().toString());
            bundle.putString(CRIME_SOLVED_KEY, DATE_FORMAT.format(crime.getDate()));
            // Removing an existing entry, since we'll need to add a modified one and this one is
            // to stay in DB, unless we remove it here.
            mDbHelper.removeCrimeById(mDbHelper.getReadableDatabase(), crimeId);
            addCriminalRecordFragment(true, bundle);
        } else {
            // What should go here ?
        }

    }

    private void setupRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        List<Crime> crimes = mDbHelper.getCrimeData(mDbHelper.getReadableDatabase());
        mAdapter = new CriminalRecordsAdapter(crimes, this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public void addCriminalRecordFragment(boolean addToBackStack, Bundle bundle) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment criminalRecordFragment = fragmentManager.findFragmentByTag(
                CriminalRecordFragment.FRAGMENT_TAG);
        if (criminalRecordFragment == null) {
            criminalRecordFragment = CriminalRecordFragment.newInstance();
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        if (bundle != null) {
            criminalRecordFragment.setArguments(bundle);
        }
        transaction.replace(R.id.fragment_container, criminalRecordFragment,
                CriminalRecordFragment.FRAGMENT_TAG)
                .commit();
    }

    private void setupAddButtonForegroundColor(int color) {
        PorterDuff.Mode mMode = PorterDuff.Mode.SRC_ATOP;
        Drawable d = getActivity().getResources().getDrawable(R.drawable.ic_add_black_24dp);
        d.setColorFilter(getResources().getColor(color), mMode);
    }

    private void displayEmptyListMessage() {
        List<Crime> crimes = mDbHelper.getCrimeData(mDbHelper.getWritableDatabase());
        if (crimes.size() == 0) {
            mEmptyListTextView.setVisibility(View.VISIBLE);
        } else {
            mEmptyListTextView.setVisibility(View.INVISIBLE);
        }
    }

}
