package com.example.vladislav.androidstudy.jobs.currency.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.jobs.currency.CurrencyRecyclerAdapter;
import com.example.vladislav.androidstudy.jobs.currency.beans.CurrencyBean;
import com.example.vladislav.androidstudy.jobs.currency.loaders.CurrencyAsyncTaskLoader;

import java.util.List;

/**
 * Fragment that uses an AsyncTaskLoader to load currencies
 * <p>
 * Created by Влад on 01.06.2018.
 */

public class CurrencyAsyncTaskLoaderFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<List<CurrencyBean>> {

    public static final String TAG = CurrencyAsyncTaskLoaderFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private CurrencyRecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private TextView mCenterMessageTextView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getLoaderManager().initLoader(0, null, this);
        getLoaderManager().getLoader(0).startLoading();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_currency, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.currency_recycler_view);
        mProgressBar = (ProgressBar) view.findViewById(R.id.currency_progress_bar);
        mCenterMessageTextView = (TextView) view.findViewById(R.id.center_message_text_view);
        return view;
    }

    @Override
    public Loader<List<CurrencyBean>> onCreateLoader(int id, Bundle args) {
        return new CurrencyAsyncTaskLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<CurrencyBean>> loader, List<CurrencyBean> data) {
        if (data == null) {
            mCenterMessageTextView.setVisibility(View.VISIBLE);
            mCenterMessageTextView.setText(R.string.currencies_absent_message);
        } else {
            mCenterMessageTextView.setVisibility(View.INVISIBLE);
            mAdapter = new CurrencyRecyclerAdapter(data);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            mRecyclerView.setAdapter(mAdapter);
        }
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<List<CurrencyBean>> loader) {

    }
}
