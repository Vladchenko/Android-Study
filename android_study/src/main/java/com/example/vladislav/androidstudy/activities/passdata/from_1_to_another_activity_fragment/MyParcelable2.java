package com.example.vladislav.androidstudy.activities.passdata.from_1_to_another_activity_fragment;

/**
 * Created by Влад on 20.11.2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Shriram Shri Shrikumar
 *
 * A basic object that can be parcelled to
 * transfer between objects
 *
 */
public class MyParcelable2 implements Parcelable {

    private String mStrValue;
    private Integer mIntValue;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mStrValue);
        dest.writeValue(this.mIntValue);
    }

    public MyParcelable2() {
    }

    protected MyParcelable2(Parcel in) {
        this.mStrValue = in.readString();
        this.mIntValue = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<MyParcelable2> CREATOR = new Creator<MyParcelable2>() {
        @Override
        public MyParcelable2 createFromParcel(Parcel source) {
            return new MyParcelable2(source);
        }

        @Override
        public MyParcelable2[] newArray(int size) {
            return new MyParcelable2[size];
        }
    };
}
