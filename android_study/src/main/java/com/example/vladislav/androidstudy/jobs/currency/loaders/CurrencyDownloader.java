package com.example.vladislav.androidstudy.jobs.currency.loaders;

import com.example.vladislav.androidstudy.jobs.currency.beans.CurrenciesContainer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.vladislav.androidstudy.jobs.currency.CurrencyUtils.parse;

public class CurrencyDownloader {

    private String mLink;
    private CurrenciesContainer mCurrenciesContainer = null;

    public CurrencyDownloader(String link) {
        mLink = link;
    }

    public CurrenciesContainer getLoadedCurrencies() {
        URL url = null;
        try {
            url = new URL(mLink);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        InputStream in = null;
        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            // Why the absence of a following row makes a downloading not work ?
            urlConnection.getRequestMethod();
            in = new BufferedInputStream(urlConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mCurrenciesContainer = parse(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mCurrenciesContainer;
    }

}
