package com.example.vladislav.androidstudy.jobs.criminalrecords;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.vladislav.androidstudy.R;

/**
 * Created by Влад on 13.03.2018.
 */

public class CriminalRecordsViewHolder extends RecyclerView.ViewHolder {

    private int mPosition;

    private TextView mCrimeTitleTextView;
    private TextView mCrimeDescriptionTextView;
    private TextView mCrimeCreationDateTextView;

    private ICrimeItemClickListener mCrimeClickListener;

    public CriminalRecordsViewHolder(View itemView, ICrimeItemClickListener fragment) {
        super(itemView);
        mCrimeTitleTextView =  itemView.findViewById(R.id.crime_title_text_view);
        mCrimeDescriptionTextView =  itemView.findViewById(R.id.crime_description_text_view);
        mCrimeCreationDateTextView =  itemView.findViewById(R.id.crime_date_text_view);
        itemView.setOnClickListener(addItemClickListener());
        mCrimeClickListener = fragment;
    }

    public void setPosition(int position) {
        mPosition = position;
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
                mCrimeClickListener.onCrimeItemClick(mPosition);
            }
        };
    }

}
