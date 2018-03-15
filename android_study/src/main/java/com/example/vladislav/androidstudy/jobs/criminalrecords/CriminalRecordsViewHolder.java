package com.example.vladislav.androidstudy.jobs.criminalrecords;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;

/**
 * Created by Влад on 13.03.2018.
 */

public class CriminalRecordsViewHolder extends RecyclerView.ViewHolder {

    private TextView mCrimeTitleTextView;
    private TextView mCrimeDescriptionTextView;
    private TextView mCrimeCreationDateTextView;

    public CriminalRecordsViewHolder(View itemView) {
        super(itemView);
        mCrimeTitleTextView = (TextView)itemView.findViewById(R.id.crime_title_text_view);
        mCrimeDescriptionTextView = (TextView)itemView.findViewById(R.id.crime_description_text_view);
        mCrimeCreationDateTextView = (TextView)itemView.findViewById(R.id.crime_date_text_view);
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

}
