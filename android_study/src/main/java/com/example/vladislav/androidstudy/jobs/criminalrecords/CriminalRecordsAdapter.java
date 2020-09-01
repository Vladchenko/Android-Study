package com.example.vladislav.androidstudy.jobs.criminalrecords;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.vladislav.androidstudy.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Влад on 13.03.2018.
 */

public class CriminalRecordsAdapter extends RecyclerView.Adapter<CriminalRecordsViewHolder> {

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.US);

    private ICrimeItemClickListener mFragment;
    private List<Crime> mCrimes;
    private int mIndex = 0;

    public CriminalRecordsAdapter(List<Crime> crimes, ICrimeItemClickListener fragment) {
        mCrimes = crimes;
        mFragment = fragment;
    }

    @Override
    public CriminalRecordsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mIndex++;
        // create a new view
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crime_record_recycler_view_item, parent, false);
        if (mIndex % 2 == 1) {
            view.setBackgroundColor(parent.getContext().getResources().
                    getColor(R.color.color_lightblue));
        } else {
            view.setBackgroundColor(parent.getContext().getResources().
                    getColor(R.color.color_lightblue_2));
        }
        // set the view's size, margins, paddings and layout parameters
        CriminalRecordsViewHolder vh = new CriminalRecordsViewHolder(view, mFragment);
        return vh;
    }

    @Override
    public void onBindViewHolder(CriminalRecordsViewHolder holder, int position) {
        holder.setPosition(position);
        holder.getCrimeTitleTextView().setText(mCrimes.get(position).getTitle());
        holder.getCrimeDescriptionTextView().setText(mCrimes.get(position).getDescription());
        holder.getCrimeCreationDateTextView().setText(
                DATE_FORMAT.format(mCrimes.get(position).getDate()));
    }

    @Override
    public int getItemCount() {
        return mCrimes.size();
    }
}
