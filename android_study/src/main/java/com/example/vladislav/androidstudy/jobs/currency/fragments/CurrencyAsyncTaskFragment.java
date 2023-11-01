package com.example.vladislav.androidstudy.jobs.currency.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.jobs.currency.CurrencyRecyclerAdapter;
import com.example.vladislav.androidstudy.jobs.currency.ICallback;
import com.example.vladislav.androidstudy.jobs.currency.beans.CurrencyBean;
import com.example.vladislav.androidstudy.jobs.currency.loaders.CurrencyAsyncTask;

import java.util.List;

/**
 * Fragment that uses an AsyncTask to load currencies.
 *
 * A simple {@link Fragment} subclass.
 */
public class CurrencyAsyncTaskFragment extends Fragment implements ICallback {

    public static final String TAG = CurrencyAsyncTaskFragment.class.getSimpleName();
    public static final String sUrl = "http://www.cbr.ru/scripts/XML_daily.asp";

    private CurrencyAsyncTask mCurrencyAsyncTask;
    private RecyclerView mRecyclerView;
    private CurrencyRecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private TextView mCenterMessageTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrencyAsyncTask = new CurrencyAsyncTask(this);
        mCurrencyAsyncTask.execute(sUrl);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_currency, container, false);
        mRecyclerView =  view.findViewById(R.id.currency_recycler_view);
        mProgressBar =  view.findViewById(R.id.currency_progress_bar);
        mCenterMessageTextView =  view.findViewById(R.id.center_message_text_view);
        return view;
    }

    @Override
    public void loadedData(List<CurrencyBean> currenciesList) {
        if (currenciesList == null) {
            mCenterMessageTextView.setVisibility(View.VISIBLE);
            mCenterMessageTextView.setText(R.string.currencies_absent_message);
        } else {
            mCenterMessageTextView.setVisibility(View.INVISIBLE);
            mAdapter = new CurrencyRecyclerAdapter(currenciesList);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            mRecyclerView.setAdapter(mAdapter);
        }
        mProgressBar.setVisibility(View.INVISIBLE);
    }

}
