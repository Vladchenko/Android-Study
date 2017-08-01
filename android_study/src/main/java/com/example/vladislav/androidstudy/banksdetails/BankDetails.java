package com.example.vladislav.androidstudy.banksdetails;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vladislav on 05.02.17.
 */

public class BankDetails implements Parcelable {

    public static boolean loaded = false;
    private String mAddress;
    private String mDistance;
    private String mName; // extra office
    private String mLatitude;
    private String mLongtitude;
    private String mWorkingHours;
    private String mPhoneNumber;
    private String mQualityControl;
    private int mID;
    private int mEstimationMark;

    protected BankDetails(Parcel in) {
        setmAddress(in.readString());
        setmName(in.readString());
        setmLatitude(in.readString());
        setmLongtitude(in.readString());
        setmDistance(in.readString());
        setmWorkingHours(in.readString());
        setmPhoneNumber(in.readString());
        setmQualityControl(in.readString());
        // -1 means there was no estimation provided for this bank.
        setmEstimationMark(-1);
    }

    public static final Creator<BankDetails> CREATOR = new Creator<BankDetails>() {
        @Override
        public BankDetails createFromParcel(Parcel in) {
            return new BankDetails(in);
        }

        @Override
        public BankDetails[] newArray(int size) {
            return new BankDetails[size];
        }
    };

    public BankDetails() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getmAddress());
        dest.writeString(getmName());
        dest.writeString(getmLatitude());
        dest.writeString(getmLongtitude());
        dest.writeString(getmDistance());
        dest.writeString(getmWorkingHours());
        dest.writeString(getmPhoneNumber());
        dest.writeString(getmQualityControl());
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(String mLatitude) {
        this.mLatitude = mLatitude;
    }

    public String getmLongtitude() {
        return mLongtitude;
    }

    public void setmLongtitude(String mLongtitude) {
        this.mLongtitude = mLongtitude;
    }

    public String getmWorkingHours() {
        return mWorkingHours;
    }

    public void setmWorkingHours(String mWorkingHours) {
        this.mWorkingHours = mWorkingHours;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getmQualityControl() {
        return mQualityControl;
    }

    public void setmQualityControl(String mQualityControl) {
        this.mQualityControl = mQualityControl;
    }

    public String getmDistance() {
        return mDistance;
    }

    public void setmDistance(String mDistance) {
        this.mDistance = mDistance;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public int getmEstimationMark() {
        return mEstimationMark;
    }

    public void setmEstimationMark(int mEstimationMark) {
        this.mEstimationMark = mEstimationMark;
    }
}