package com.example.vladislav.androidstudy.jobs.currency.loaders;

import android.os.AsyncTask;

import com.example.vladislav.androidstudy.jobs.currency.ICallback;
import com.example.vladislav.androidstudy.jobs.currency.beans.CurrencyBean;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static com.example.vladislav.androidstudy.jobs.currency.CurrencyUtils.retrieveCurrencies;

/**
 * Downloading a currencies data.
 * Downloading performed using HttpURLConnection that keeps result in an InputStream.
 *
 * Created by Влад on 28.05.2018.
 */

public class CurrencyAsyncTask extends AsyncTask<String, Void, List<CurrencyBean>> {

    private ICallback mAsyncTaskCallback;

    public CurrencyAsyncTask(ICallback asyncTaskCallback) {
        this.mAsyncTaskCallback = asyncTaskCallback;
    }

    @Override
    protected List<CurrencyBean> doInBackground(String... params) {
        URL url = null;
        List<CurrencyBean> currenciesList = null;
        try {
            url = new URL(params[0]);
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

    @Override
    protected void onPostExecute(List<CurrencyBean> currenciesList) {
        super.onPostExecute(currenciesList);
        mAsyncTaskCallback.loadedData(currenciesList);
    }

}
