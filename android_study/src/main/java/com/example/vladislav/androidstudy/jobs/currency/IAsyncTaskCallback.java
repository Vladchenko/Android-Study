package com.example.vladislav.androidstudy.jobs.currency;

import com.example.vladislav.androidstudy.jobs.currency.beans.CurrencyBean;

import java.util.List;

/**
 * Created by Влад on 30.05.2018.
 */

public interface IAsyncTaskCallback {

    /**
     * Callback informs that data has been downloaded by asynctask.
     *
     * @param currenciesList list of currency beans downloaded
     */
    void loadedData(List<CurrencyBean> currenciesList);

}
