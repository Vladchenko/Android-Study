package com.example.vladislav.androidstudy.jobs.currency.loaders;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import com.example.vladislav.androidstudy.jobs.currency.beans.CurrencyBean;

import java.util.List;

/**
 * Created by Влад on 01.06.2018.
 */

public class CurrencyAsyncTaskLoader extends AsyncTaskLoader<List<CurrencyBean>> {

    private static String sUrl = "http://www.cbr.ru/scripts/XML_daily.asp";

    public CurrencyAsyncTaskLoader(Context context) {
        super(context);
        onForceLoad();
    }

    @Override
    protected void onStartLoading() {
        //Think of this as AsyncTask onPreExecute() method,you can start your progress bar,and at
        // the end call forceLoad();
        forceLoad();
    }

    @Override
    public List<CurrencyBean> loadInBackground() {

        //Think of this as AsyncTask doInBackground() method, here you will actually initiate
        // Network call, or any work that need to be done on background

        return new CurrencyDownloader(sUrl).getLoadedCurrencies().getCurrenciesList();
    }


}
