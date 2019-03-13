package com.example.vladislav.androidstudy.jobs.currency.loaders;

import android.os.AsyncTask;

import com.example.vladislav.androidstudy.jobs.currency.ICallback;
import com.example.vladislav.androidstudy.jobs.currency.beans.CurrencyBean;

import java.util.List;

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
        return new CurrencyDownloader(params[0]).getLoadedCurrencies().getCurrenciesList();
    }

    @Override
    protected void onPostExecute(List<CurrencyBean> currenciesList) {
        super.onPostExecute(currenciesList);
        mAsyncTaskCallback.loadedData(currenciesList);
    }

}
