package com.example.vladislav.androidstudy.jobs.criminalrecords;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Влад on 11.03.2018.
 */

public class Crime implements Parcelable {

    private String mId;
    private String mTitle;
    private String mDescription;
    private Date mDate = new Date();
    private boolean mSolved;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Crime crime = (Crime) o;

        if (mSolved != crime.mSolved) return false;
        if (!mTitle.equals(crime.mTitle)) return false;
        if (!mDescription.equals(crime.mDescription)) return false;
        return mDate.equals(crime.mDate);

    }

    @Override
    public int hashCode() {
        int result = mTitle.hashCode();
        result = 31 * result + mDescription.hashCode();
        result = 31 * result + mDate.hashCode();
        result = 31 * result + (mSolved ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Crime{" +
                "Id='" + mId + '\'' +
                ", Title='" + mTitle + '\'' +
                ", Description='" + mDescription + '\'' +
                ", Date=" + mDate +
                ", Solved=" + mSolved +
                '}';
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String initId() {
        mId = Integer.toString(hashCode());
        return mId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeString(this.mTitle);
        dest.writeString(this.mDescription);
        dest.writeLong(this.mDate != null ? this.mDate.getTime() : -1);
        dest.writeByte(this.mSolved ? (byte) 1 : (byte) 0);
    }

    public Crime() {
    }

    protected Crime(Parcel in) {
        this.mId = in.readString();
        this.mTitle = in.readString();
        this.mDescription = in.readString();
        long tmpMDate = in.readLong();
        this.mDate = tmpMDate == -1 ? null : new Date(tmpMDate);
        this.mSolved = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Crime> CREATOR = new Parcelable.Creator<Crime>() {
        @Override
        public Crime createFromParcel(Parcel source) {
            return new Crime(source);
        }

        @Override
        public Crime[] newArray(int size) {
            return new Crime[size];
        }
    };
}
