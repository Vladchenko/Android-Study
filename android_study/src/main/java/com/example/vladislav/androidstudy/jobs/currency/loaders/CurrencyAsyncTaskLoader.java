package com.example.vladislav.androidstudy.jobs.currency.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.vladislav.androidstudy.jobs.currency.beans.CurrencyBean;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.vladislav.androidstudy.jobs.currency.CurrencyUtils.retrieveCurrencies;

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

        List<CurrencyBean> currenciesList = new ArrayList<>();
        URL url = null;

        try {
            url = new URL(sUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        InputStream in = null;
        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            // Why does it make a downloading work ?
            urlConnection.getRequestMethod();
            in = new BufferedInputStream(urlConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            currenciesList = retrieveCurrencies(in);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return currenciesList;
    }


}
