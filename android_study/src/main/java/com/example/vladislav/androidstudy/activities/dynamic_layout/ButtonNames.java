package com.example.vladislav.androidstudy.activities.dynamic_layout;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * To pass a names of a buttons in case of orientation change (when activity recovers)
 *
 * Created by Влад on 11.02.2018.
 */

public class ButtonNames implements Parcelable {

    private List<String> mButtonNames = new ArrayList<>();

    List<String> getButtonNames() {
        return mButtonNames;
    }

    ButtonNames(List<String> buttonList) {
        mButtonNames = buttonList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.mButtonNames);
    }

    protected ButtonNames(Parcel in) {
        this.mButtonNames = in.createStringArrayList();
    }

    public static final Creator<ButtonNames> CREATOR = new Creator<ButtonNames>() {
        @Override
        public ButtonNames createFromParcel(Parcel source) {
            return new ButtonNames(source);
        }

        @Override
        public ButtonNames[] newArray(int size) {
            return new ButtonNames[size];
        }
    };
}
