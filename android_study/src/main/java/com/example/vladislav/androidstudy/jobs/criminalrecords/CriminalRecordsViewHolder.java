package com.example.vladislav.androidstudy.jobs.criminalrecords;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.jobs.criminalrecords.ui.CriminalRecordListFragment;

/**
 * Created by Влад on 13.03.2018.
 */

public class CriminalRecordsViewHolder extends RecyclerView.ViewHolder {

    private String mId;

    private TextView mCrimeTitleTextView;
    private TextView mCrimeDescriptionTextView;
    private TextView mCrimeCreationDateTextView;

    private ICrimeItemClickListener crimeClickListener;

    public CriminalRecordsViewHolder(View itemView, CriminalRecordListFragment fragment) {
        super(itemView);
        mCrimeTitleTextView = (TextView) itemView.findViewById(R.id.crime_title_text_view);
        mCrimeDescriptionTextView = (TextView) itemView.findViewById(R.id.crime_description_text_view);
        mCrimeCreationDateTextView = (TextView) itemView.findViewById(R.id.crime_date_text_view);
        itemView.setOnClickListener(addItemClickListener());
        crimeClickListener = fragment;
    }

    public void setId(String id) {
        mId = id;
    }

    public TextView getCrimeTitleTextView() {
        return mCrimeTitleTextView;
    }

    public TextView getCrimeDescriptionTextView() {
        return mCrimeDescriptionTextView;
    }

    public TextView getCrimeCreationDateTextView() {
        return mCrimeCreationDateTextView;
    }

    private AdapterView.OnClickListener addItemClickListener() {
        return new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                crimeClickListener.onCrimeItemClick(mId);
            }
        };
    }

}
