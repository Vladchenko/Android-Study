package com.example.vladislav.androidstudy.jobs.listviewing.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;

/**
 * Demo adapter for demo recycler
 *
 * Created by Влад on 08.02.2019.
 */
public class DemoRecyclerView2ItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class TextViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView mTextView;
        TextViewHolder(View v) {
            super(v);
            mTextView = (TextView)v.findViewById(R.id.recycler_item_text_view);
        }
    }

    public class TextImageViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView mTextView;
        TextImageViewHolder(View v) {
            super(v);
            mTextView = (TextView)v.findViewById(R.id.recycler_item_text_view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DemoRecyclerView2ItemsAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v = null;
        switch (viewType) {
            case 0:
                // create a new view
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_view_item, parent, false);
                // set the view's size, margins, paddings and layout parameters
                return new TextViewHolder(v);
            case 1:
                // create a new view
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_view_item_2, parent, false);
                // set the view's size, margins, paddings and layout parameters
                return new TextImageViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                TextViewHolder viewHolder = (TextViewHolder)holder;
                viewHolder.mTextView.setText(mDataset[position]);
                break;
            case 1:
                TextImageViewHolder textImageViewHolder = (TextImageViewHolder)holder;
                textImageViewHolder.mTextView.setText(mDataset[position]);
                break;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
