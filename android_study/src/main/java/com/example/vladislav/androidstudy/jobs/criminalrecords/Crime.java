package com.example.vladislav.androidstudy.jobs.criminalrecords;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Влад on 11.03.2018.
 */

public class Crime {

    private String mTitle;
    private String mDescription;
    private Date mDate;
    private boolean mSolved;

    public void Crime() {
        mDate = new Date();
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

}
