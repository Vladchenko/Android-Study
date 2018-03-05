package com.example.vladislav.androidstudy.jobs.banksdetails;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vladislav on 05.02.17.
 */

public class BankDetails implements Parcelable {

    public boolean loaded = false;
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

    public BankDetails() { }

    protected BankDetails(Parcel in) {
        setAddress(in.readString());
        setName(in.readString());
        setLatitude(in.readString());
        setLongtitude(in.readString());
        setDistance(in.readString());
        setWorkingHours(in.readString());
        setPhoneNumber(in.readString());
        setQualityControl(in.readString());
        // -1 means there was no estimation provided for this bank.
        setEstimationMark(-1);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getAddress());
        dest.writeString(getName());
        dest.writeString(getLatitude());
        dest.writeString(getLongtitude());
        dest.writeString(getDistance());
        dest.writeString(getWorkingHours());
        dest.writeString(getPhoneNumber());
        dest.writeString(getQualityControl());
    }

    // region getters, setters
    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String mLatitude) {
        this.mLatitude = mLatitude;
    }

    public String getLongtitude() {
        return mLongtitude;
    }

    public void setLongtitude(String mLongtitude) {
        this.mLongtitude = mLongtitude;
    }

    public String getWorkingHours() {
        return mWorkingHours;
    }

    public void setWorkingHours(String mWorkingHours) {
        this.mWorkingHours = mWorkingHours;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getQualityControl() {
        return mQualityControl;
    }

    public void setQualityControl(String mQualityControl) {
        this.mQualityControl = mQualityControl;
    }

    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String mDistance) {
        this.mDistance = mDistance;
    }

    public int getID() {
        return mID;
    }

    public void setID(int mID) {
        this.mID = mID;
    }

    public int getEstimationMark() {
        return mEstimationMark;
    }
    // endregion

    public void setEstimationMark(int mEstimationMark) {
        this.mEstimationMark = mEstimationMark;
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
}