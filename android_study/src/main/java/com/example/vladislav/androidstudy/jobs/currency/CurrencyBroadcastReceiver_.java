package com.example.vladislav.androidstudy.jobs.currency;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.vladislav.androidstudy.jobs.currency.beans.CurrenciesContainer;
import com.example.vladislav.androidstudy.jobs.currency.beans.CurrencyBean;

import java.util.List;

import static com.example.vladislav.androidstudy.jobs.currency.beans.CurrenciesContainer.CURRENCIES_TAG;

/**
 * Created by Влад on 05.06.2018.
 */

public class CurrencyBroadcastReceiver_ extends BroadcastReceiver {

    private List<CurrencyBean> mCurrenciesList;

    @Override
    public void onReceive(Context context, Intent intent) {
        mCurrenciesList = getCurrenciesList(intent);
    }

    private List<CurrencyBean> getCurrenciesList(Intent intent) {
        CurrenciesContainer currenciesContainer = (CurrenciesContainer)intent.getExtras().get(CURRENCIES_TAG);
        return currenciesContainer.getCurrenciesList();
    }

    public List<CurrencyBean> getCurrenciesList() {
        return mCurrenciesList;
    }
}
