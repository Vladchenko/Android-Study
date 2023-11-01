package com.example.vladislav.androidstudy.jobs.listviewing.recyclerview;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.vladislav.androidstudy.R;

/**
 * Created by Влад on 29.05.2018.
 */

public class CurrencyViewHolder extends RecyclerView.ViewHolder {

    private TextView mCurrencyStringCodeTextView;
    private TextView mCurrencyNumericCodeTextView;
    private TextView mCurrencyNameTextView;
    private TextView mCurrencyValueTextView;

    public CurrencyViewHolder(View itemView) {
        super(itemView);
        mCurrencyStringCodeTextView =  itemView.findViewById(R.id.currency_string_code_text_view);
        mCurrencyNumericCodeTextView =  itemView.findViewById(R.id.currency_numeric_code_text_view);
        mCurrencyNameTextView =  itemView.findViewById(R.id.currency_name_text_view);
        mCurrencyValueTextView =  itemView.findViewById(R.id.currency_value_text_view);
    }

    public TextView getCurrencyStringCodeTextView() {
        return mCurrencyStringCodeTextView;
    }

    public TextView getCurrencyNumericCodeTextView() {
        return mCurrencyNumericCodeTextView;
    }

    public TextView getCurrencyNameTextView() {
        return mCurrencyNameTextView;
    }

    public TextView getCurrencyValueTextView() {
        return mCurrencyValueTextView;
    }

}
