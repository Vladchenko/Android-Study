package com.example.vladislav.androidstudy.jobs.currency.fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.jobs.currency.CurrencyRecyclerAdapter;
import com.example.vladislav.androidstudy.jobs.currency.ICallback;
import com.example.vladislav.androidstudy.jobs.currency.RectangularItemDecoration;
import com.example.vladislav.androidstudy.jobs.currency.beans.CurrencyBean;
import com.example.vladislav.androidstudy.jobs.currency.loaders.CurrencyDownloadingBindService;

import java.util.List;

/**
 * Created by Влад on 02.06.2018.
 */

public class CurrencyBindServiceFragment extends Fragment implements ICallback {

    public static final String sURL = "Url to download data from";
    public static final String TAG = CurrencyAsyncTaskFragment.class.getSimpleName();
    public static final String BROADCAST_ACTION =
            "com.example.vladislav.androidstudy.jobs.currency.loaders.CurrencyDownloadingStartService";

    private RecyclerView mRecyclerView;
    private CurrencyRecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private TextView mCenterMessageTextView;
//    private CurrencyBroadcastReceiver mBroadcastReceiver;
    private CurrencyDownloadingBindService mService;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        mBroadcastReceiver = new CurrencyBroadcastReceiver();
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

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        Intent serviceIntent = new Intent(getActivity(), CurrencyDownloadingBindService.class);
        serviceIntent.setAction(BROADCAST_ACTION);
        serviceIntent.putExtra(sURL, "http://www.cbr.ru/scripts/XML_daily.asp");
        getActivity().bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE); //Binding to the service!
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Intent intent = new Intent(getActivity(), CurrencyDownloadingBindService.class);
        intent.putExtra(sURL, "http://www.cbr.ru/scripts/XML_daily.asp");
        getActivity().unbindService(mConnection);
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

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
//            Toast.makeText(getActivity(), "onServiceConnected called", Toast.LENGTH_SHORT).show();
            // We've binded to LocalService, cast the IBinder and get LocalService instance
            CurrencyDownloadingBindService.LocalBinder binder =
                    (CurrencyDownloadingBindService.LocalBinder) service;
            mService = binder.getServiceInstance(); //Get instance of your service!
            //Activity registers in the service as client for callbacks!
            mService.registerClient(CurrencyBindServiceFragment.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
//            Toast.makeText(getActivity(), "onServiceDisconnected called", Toast.LENGTH_SHORT).show();
        }
    };

}
