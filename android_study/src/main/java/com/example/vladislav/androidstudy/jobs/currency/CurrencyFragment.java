package com.example.vladislav.androidstudy.jobs.currency;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.jobs.currency.beans.CurrencyBean;

import java.io.InputStream;
import java.util.List;

import static com.example.vladislav.androidstudy.jobs.currency.CurrencyUtils.retrieveCurrencies;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrencyFragment extends Fragment implements IAsyncTaskCallback {

    public static final String TAG = CurrencyFragment.class.getSimpleName();

    private String mUrl = "http://www.cbr.ru/scripts/XML_daily.asp";
    private CurrencyAsyncTask mCurrencyAsyncTask;
    private RecyclerView mRecyclerView;
    private CurrencyRecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrencyAsyncTask = new CurrencyAsyncTask(this);
        mCurrencyAsyncTask.execute(mUrl);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_currency, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.currency_recycler_view);
        mProgressBar = (ProgressBar) view.findViewById(R.id.currency_progress_bar);
        return view;
    }

    @Override
    public void loadedData(List<CurrencyBean> currenciesList) {
        mAdapter = new CurrencyRecyclerAdapter(currenciesList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(mAdapter);
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}
