package com.example.vladislav.androidstudy.jobs.criminalrecords;


import android.graphics.Color;
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

import com.example.vladislav.androidstudy.R;

import java.util.List;

import static com.example.vladislav.androidstudy.jobs.criminalrecords.CriminalRecordsActivity.DATABASE_NAME;

/**
 * Fragment representing a list of a crimes
 */
public class CriminalRecordListFragment extends Fragment {

    public static final String FRAGMENT_TAG = CriminalRecordListFragment.class.getSimpleName();

    private FloatingActionButton mActionButton;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DBHelper mDbHelper;

    public CriminalRecordListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDbHelper = new DBHelper(getActivity(), DATABASE_NAME);
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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCriminalRecordFragment();
            }
        });
        setupRecyclerView();
        setupAddButtonForegroundColor(R.color.color_white);
    }

    private void setupRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        List<Crime> crimes = mDbHelper.getCrimeData(mDbHelper.getReadableDatabase());
        mAdapter = new CriminalRecordsAdapter(crimes);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void addCriminalRecordFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment criminalRecordFragment = fragmentManager.findFragmentByTag(
                CriminalRecordFragment.FRAGMENT_TAG);
        if (criminalRecordFragment == null) {
            criminalRecordFragment = new CriminalRecordFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, criminalRecordFragment,
                    CriminalRecordFragment.FRAGMENT_TAG)
                    .commit();
        }
    }

    private void setupAddButtonForegroundColor(int color) {
        PorterDuff.Mode mMode = PorterDuff.Mode.SRC_ATOP;
        Drawable d = getActivity().getResources().getDrawable(R.drawable.ic_add_black_24dp);
        d.setColorFilter(getResources().getColor(color),mMode);
    }
}
