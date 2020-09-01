package com.example.vladislav.androidstudy.jobs.currency.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.jobs.currency.CurrencyRecyclerAdapter;
import com.example.vladislav.androidstudy.jobs.currency.ICallback;
import com.example.vladislav.androidstudy.jobs.currency.RectangularItemDecoration;
import com.example.vladislav.androidstudy.jobs.currency.beans.CurrenciesContainer;
import com.example.vladislav.androidstudy.jobs.currency.beans.CurrencyBean;
import com.example.vladislav.androidstudy.jobs.currency.loaders.CurrencyDownloadingStartService;

import java.util.List;

import static com.example.vladislav.androidstudy.jobs.currency.beans.CurrenciesContainer.CURRENCIES_TAG;

/**
 * Created by Влад on 02.06.2018.
 */

public class CurrencyStartServiceFragment extends Fragment implements ICallback {

    public static final String sURL = "Url to download data from";
    public static final String TAG = CurrencyAsyncTaskFragment.class.getSimpleName();
    public static final String BROADCAST_ACTION = "com.example.vladislav.androidstudy.jobs.currency.loaders.CurrencyDownloadingStartService";

    private RecyclerView mRecyclerView;
    private CurrencyRecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private TextView mCenterMessageTextView;
    private CurrencyBroadcastReceiver mBroadcastReceiver;
    private CurrencyDownloadingStartService mService;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBroadcastReceiver = new CurrencyBroadcastReceiver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_currency, container, false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.currency_recycler_view);
        mRecyclerView.addItemDecoration(new RectangularItemDecoration(getActivity()));
        mProgressBar = (ProgressBar) view.findViewById(R.id.currency_progress_bar);
        mCenterMessageTextView = (TextView) view.findViewById(R.id.center_message_text_view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent serviceIntent = new Intent(getActivity(), CurrencyDownloadingStartService.class);
        serviceIntent.setAction(BROADCAST_ACTION);
        serviceIntent.putExtra(sURL, "http://www.cbr.ru/scripts/XML_daily.asp");
        getActivity().startService(serviceIntent);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerBroadcastReceiver();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterBroadcastReceiver();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Intent intent = new Intent(getActivity(), CurrencyDownloadingStartService.class);
        intent.putExtra(sURL, "http://www.cbr.ru/scripts/XML_daily.asp");
        getActivity().stopService(intent);
    }

    @Override
    public void loadedData(List<CurrencyBean> currenciesList) {
        if (currenciesList == null) {
            mCenterMessageTextView.setVisibility(View.VISIBLE);
            mCenterMessageTextView.setText(R.string.currencies_absent_message);
        } else {
            mCenterMessageTextView.setVisibility(View.INVISIBLE);
            mAdapter = new CurrencyRecyclerAdapter(currenciesList);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
        }
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void registerBroadcastReceiver() {
        getActivity().registerReceiver(mBroadcastReceiver, getIntentFilterForBroadcastReceiver());
    }

    private void unregisterBroadcastReceiver() {
        getActivity().unregisterReceiver(mBroadcastReceiver);
    }

    private static IntentFilter getIntentFilterForBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        return intentFilter;
    }

    class CurrencyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            CurrenciesContainer container = getCurrenciesList(intent);
            if (container == null) {
                loadedData(null);
            } else {
                loadedData(container.getCurrenciesList());
            }
        }

        private CurrenciesContainer getCurrenciesList(Intent intent) {
            CurrenciesContainer currenciesContainer = (CurrenciesContainer)intent.getExtras()
                    .get(CURRENCIES_TAG);
            return currenciesContainer;
        }

    }
}
