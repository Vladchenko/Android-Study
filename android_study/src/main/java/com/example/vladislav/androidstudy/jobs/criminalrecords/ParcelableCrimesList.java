package com.example.vladislav.androidstudy.jobs.criminalrecords;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Влад on 08.04.2018.
 */

public class ParcelableCrimesList extends ArrayList<Crime> implements Parcelable {

    private static final long serialVersionUID = 663585476779879096L;

    protected ParcelableCrimesList(Parcel in) {
        this();
        readFromParcel(in);
    }

    public ParcelableCrimesList() {
    }

    private void readFromParcel(Parcel in) {
        this.clear();

        // First we have to read the list size
        int size = in.readInt();

        for (int i = 0; i < size; i++) {
            Crime r = new Crime(in);
            this.add(r);
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ParcelableCrimesList> CREATOR = new Creator<ParcelableCrimesList>() {
        @Override
        public ParcelableCrimesList createFromParcel(Parcel in) {
            return new ParcelableCrimesList(in);
        }

        @Override
        public ParcelableCrimesList[] newArray(int size) {
            return new ParcelableCrimesList[size];
        }
    };

}

