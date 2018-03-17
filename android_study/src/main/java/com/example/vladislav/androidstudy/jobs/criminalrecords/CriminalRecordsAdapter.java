package com.example.vladislav.androidstudy.jobs.criminalrecords;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vladislav.androidstudy.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Влад on 13.03.2018.
 */

public class CriminalRecordsAdapter extends RecyclerView.Adapter<CriminalRecordsViewHolder> {

    private List<Crime> mCrimes;
    private int mIndex=0;
    public static final DateFormat mDateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.US);

    public CriminalRecordsAdapter(List<Crime> crimes) {
        mCrimes = crimes;
    }

    @Override
    public CriminalRecordsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mIndex++;
        // create a new view
        View view;
        if (mIndex%2 == 0) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.crime_record_recycler_view_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.crime_record_recycler_view_item_2, parent, false);
        }
        // set the view's size, margins, paddings and layout parameters
        CriminalRecordsViewHolder vh = new CriminalRecordsViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(CriminalRecordsViewHolder holder, int position) {
        holder.getCrimeTitleTextView().setText(mCrimes.get(position).getTitle());
        holder.getCrimeDescriptionTextView().setText(mCrimes.get(position).getDescription());
        holder.getCrimeCreationDateTextView().setText(
                mDateFormat.format(mCrimes.get(position).getDate()));
    }

    @Override
    public int getItemCount() {
        return mCrimes.size();
    }
}
