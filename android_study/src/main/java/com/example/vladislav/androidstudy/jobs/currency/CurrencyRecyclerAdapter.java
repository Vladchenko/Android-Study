package com.example.vladislav.androidstudy.jobs.currency;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.jobs.currency.beans.CurrencyBean;
import com.example.vladislav.androidstudy.jobs.listviewing.recyclerview.CurrencyViewHolder;

import java.util.List;

/**
 * Created by Влад on 29.05.2018.
 */

public class CurrencyRecyclerAdapter extends RecyclerView.Adapter<CurrencyViewHolder> {

    private List<CurrencyBean> mCurrenciesList;

    public CurrencyRecyclerAdapter(List<CurrencyBean> list) {
        mCurrenciesList = list;
    }

    @Override
    public CurrencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.currency_recyclerview_item, parent, false);
        CurrencyViewHolder viewHolder = new CurrencyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CurrencyViewHolder holder, int position) {
        holder.getCurrencyNameTextView().setText(mCurrenciesList.get(position).getName());
        holder.getCurrencyNumericCodeTextView().setText(mCurrenciesList.get(position).getNumericCode());
        holder.getCurrencyStringCodeTextView().setText(mCurrenciesList.get(position).getCharacterCode());
        holder.getCurrencyValueTextView().setText(mCurrenciesList.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return mCurrenciesList.size();
    }
}
