package com.example.vladislav.androidstudy.jobs.currency.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.jobs.currency.CurrencyRecyclerAdapter;
import com.example.vladislav.androidstudy.jobs.currency.ICallback;
import com.example.vladislav.androidstudy.jobs.currency.beans.CurrenciesContainer;
import com.example.vladislav.androidstudy.jobs.currency.beans.CurrencyBean;
import com.example.vladislav.androidstudy.jobs.currency.loaders.CurrencyDownloader;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Fragment that uses an AsyncTask to load currencies.
 * <p>
 * A simple {@link Fragment} subclass.
 */
public class CurrencyRXLoaderFragment extends Fragment implements ICallback {

    public static final String TAG = CurrencyRXLoaderFragment.class.getSimpleName();
    public static final String sUrl = "http://www.cbr.ru/scripts/XML_daily.asp";

    private Disposable mCurrenciesSingle;
    private CurrenciesContainer mCurrenciesContainer;
    private RecyclerView mRecyclerView;
    private CurrencyRecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private TextView mCenterMessageTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ! Check if this code could be implemented here, and not in interactor/presenter
        mCurrenciesSingle = createObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> loadedData(mCurrenciesContainer.getCurrenciesList()),
                        result -> handleError());

//        mCurrenciesSingle = createObservable()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(result -> loadedData(mCurrenciesContainer.getCurrenciesList()))
//                .doOnError(result -> System.out.println("Error"))
//                .doOnComplete(new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        System.out.println("Completed");
//                    }
//                })
//                .subscribe(result -> loadedData(mCurrenciesContainer.getCurrenciesList()));

//        createSingle()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(result -> loadedData(mCurrenciesContainer.getCurrenciesList()),
//                        result -> handleError());
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

    public void handleError() {
        System.out.println("Error");
    }

    private Observable createObservable() {
        return Observable.create(
                emitter -> {
                    Thread thread = new Thread(() -> {
                        try {
                            mCurrenciesContainer = new CurrencyDownloader(sUrl).getLoadedCurrencies();
                            emitter.onNext(mCurrenciesContainer.getCurrenciesList());
                        } catch (Exception e) {
                            emitter.onError(e);
                        }
                    });
                    // Imitating some work by putting thread to sleep
                    thread.sleep(3000);
                    thread.start();
                }
        );
    }

    private Single createSingle() {
        return Single.create(emitter -> {
            Thread thread = new Thread(() -> {
                try {
                    mCurrenciesContainer = new CurrencyDownloader(sUrl).getLoadedCurrencies();
                    emitter.onSuccess(mCurrenciesContainer.getCurrenciesList());
                } catch (Exception e) {
                    emitter.onError(e);
                }
            });
            // Imitating some work by putting thread to sleep
            thread.sleep(3000);
            thread.start();
        });
    }

}
