package com.example.vladislav.androidstudy.jobs.currency.beans;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Contents for the root entry of a currency XML file.
 */

@Root(name="ValCurs")
public class CurrenciesContainer implements Parcelable {

    public static final String CURRENCIES_TAG = "Currency beans";

    @ElementList(entry = "Valute", inline=true)
    private List<CurrencyBean> mCurrenciesList = new ArrayList<>();

    @Attribute(name = "name")
    private String mName;

    @Attribute(name = "Date")
    private String mDate;

    public CurrenciesContainer() {}

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public List getProperties() {
        return getCurrenciesList();
    }

    public List<CurrencyBean> getCurrenciesList() {
        return mCurrenciesList;
    }

    public void setCurrenciesList(List<CurrencyBean> mCurrenciesList) {
        this.mCurrenciesList = mCurrenciesList;
    }

    public void addCurrencies(CurrenciesContainer currenciesContainer) {
        mCurrenciesList.addAll(currenciesContainer.getCurrenciesList());
    }

    public void addCurrency(CurrencyBean currencyBean) {
        mCurrenciesList.add(currencyBean);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.mCurrenciesList);
        dest.writeString(this.mName);
        dest.writeString(this.mDate);
    }

    protected CurrenciesContainer(Parcel in) {
        this.mCurrenciesList = new ArrayList<CurrencyBean>();
        in.readList(this.mCurrenciesList, CurrencyBean.class.getClassLoader());
        this.mName = in.readString();
        this.mDate = in.readString();
    }

    public static final Parcelable.Creator<CurrenciesContainer> CREATOR = new
            Parcelable.Creator<CurrenciesContainer>() {
        @Override
        public CurrenciesContainer createFromParcel(Parcel source) {
            return new CurrenciesContainer(source);
        }

        @Override
        public CurrenciesContainer[] newArray(int size) {
            return new CurrenciesContainer[size];
        }
    };
}
