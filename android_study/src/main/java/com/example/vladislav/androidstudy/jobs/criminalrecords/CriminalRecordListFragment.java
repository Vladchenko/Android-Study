package com.example.vladislav.androidstudy.jobs.criminalrecords;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vladislav.androidstudy.R;

/**
 * Fragment representing a list of a crimes
 */
public class CriminalRecordListFragment extends Fragment {

    private FloatingActionButton actionButton;

    public CriminalRecordListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_criminal_record_list, container, false);
        actionButton = (FloatingActionButton) view.findViewById(R.id.add_crime_button);
        // Inflate the layout for this fragment
        return view;
    }

}
