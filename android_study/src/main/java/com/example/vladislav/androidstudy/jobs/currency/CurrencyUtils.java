package com.example.vladislav.androidstudy.jobs.currency;

import com.example.vladislav.androidstudy.jobs.currency.beans.CurrenciesContainer;
import com.example.vladislav.androidstudy.jobs.currency.beans.CurrencyBean;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Влад on 30.05.2018.
 */

public class CurrencyUtils {

    public static List<CurrencyBean> retrieveCurrencies(InputStream inputStream) throws Exception {
        List<CurrencyBean> currenciesList;
        CurrenciesContainer currenciesContainer = parse(inputStream);
        currenciesList = currenciesContainer.getmCurrenciesList();
        return currenciesList;
    }

    public static CurrenciesContainer parse(InputStream inputStream) throws Exception {
        Serializer serializer = new Persister();
        CurrenciesContainer currenciesContainer = null;
        currenciesContainer = serializer.read(CurrenciesContainer.class, inputStream);
        return currenciesContainer;
    }

}
