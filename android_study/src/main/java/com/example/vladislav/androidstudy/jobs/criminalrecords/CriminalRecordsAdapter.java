package com.example.vladislav.androidstudy.jobs.criminalrecords;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vladislav.androidstudy.R;

import java.util.List;

/**
 * Created by Влад on 13.03.2018.
 */

public class CriminalRecordsAdapter extends RecyclerView.Adapter<CriminalRecordsViewHolder> {

    private List<Crime> mCrimes;

    public CriminalRecordsAdapter(List<Crime> crimes) {
        mCrimes = crimes;
    }

    @Override
    public CriminalRecordsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crime_record_recycler_view_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        CriminalRecordsViewHolder vh = new CriminalRecordsViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CriminalRecordsViewHolder holder, int position) {
        holder.getCrimeTitleTextView().setText(mCrimes.get(position).getTitle());
        holder.getCrimeDescriptionTextView().setText(mCrimes.get(position).getDescription());
        holder.getCrimeCreationDateTextView().setText(mCrimes.get(position).getDate().toString());
    }

    @Override
    public int getItemCount() {
        return mCrimes.size();
    }
}
