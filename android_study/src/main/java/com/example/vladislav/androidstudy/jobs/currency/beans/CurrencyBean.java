package com.example.vladislav.androidstudy.jobs.currency.beans;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Created by vladislav on 20.03.17.
 */

@Element(name="Valute")
public class CurrencyBean implements Parcelable {

    /**
     * Temporary message to know the contents of an currency entry read from a web.
     *
     * <Valute ID="R01589">
     *      <NumCode>960</NumCode>
     *      <CharCode>XDR</CharCode>
     *      <Nominal>1</Nominal>
     *      <Name>��� (����������� ����� �������������)</Name>
     *      <Value>78,5631</Value>
     * </Valute>
     */

    @Attribute(name="ID")
    private String mID;              // say, "R01589"
    @Element(name="NumCode")
    private String mNumericCode;        // say, 960
    @Element(name="CharCode")
    private String mCharacterCode;   // say, XDR
    @Element(name="Name")
    private String mName;            // no idea what's the name of this currency.
    @Element(name="Value")
    private String mValue;           // say, 78,5631
    @Element(name="Nominal")
    private double mNominal;         // say, 1

    public CurrencyBean() {
    }

    public String getID() {
        return mID;
    }

    public void setID(String ID) {
        this.mID = ID;
    }

    public String getNumericCode() {
        return mNumericCode;
    }

    public void setNumericCode(String numericCode) {
        this.mNumericCode = numericCode;
    }

    public String getCharacterCode() {
        return mCharacterCode;
    }

    public void setCharacterCode(String characterCode) {
        this.mCharacterCode = characterCode;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        this.mValue = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mID);
        dest.writeString(this.mNumericCode);
        dest.writeString(this.mCharacterCode);
        dest.writeString(this.mName);
        dest.writeString(this.mValue);
        dest.writeDouble(this.mNominal);
    }

    protected CurrencyBean(Parcel in) {
        this.mID = in.readString();
        this.mNumericCode = in.readString();
        this.mCharacterCode = in.readString();
        this.mName = in.readString();
        this.mValue = in.readString();
        this.mNominal = in.readDouble();
    }

    public static final Parcelable.Creator<CurrencyBean> CREATOR = new Parcelable.Creator<CurrencyBean>() {
        @Override
        public CurrencyBean createFromParcel(Parcel source) {
            return new CurrencyBean(source);
        }

        @Override
        public CurrencyBean[] newArray(int size) {
            return new CurrencyBean[size];
        }
    };
}
