package com.example.vladislav.androidstudy.activities.passdata.from_1_to_another_activity_fragment;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Влад on 20.11.2017.
 */

class MyParcelable implements Parcelable {

    private int mData;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mData);
    }

    public static final Parcelable.Creator<MyParcelable> CREATOR
            = new Parcelable.Creator<MyParcelable>() {
        public MyParcelable createFromParcel(Parcel in) {
            return new MyParcelable(in);
        }

        public MyParcelable[] newArray(int size) {
            return new MyParcelable[size];
        }
    };

    // TODO Read next link
    // https://teamtreehouse.com/community/why-should-we-use-a-private-constructor
    MyParcelable(Parcel in) {
        mData = in.readInt();
    }

}
