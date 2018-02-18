package com.example.vladislav.androidstudy.jobs.sqlite;

/**
 * Bean that keeps person's information.
 * Used to keep data about people in database.
 *
 * Created by Влад on 17.02.2018.
 */
public class Person {

    private String mName;
    private String mLastName;
    private String mCellPhoneNumber;
    private String mEmail;
    private String mAddress;

    public Person(String mName, String mLastName, String mCellPhoneNumber, String mEmail,
                  String mAddress) {
        this.mName = mName;
        this.mLastName = mLastName;
        this.mCellPhoneNumber = mCellPhoneNumber;
        this.mEmail = mEmail;
        this.mAddress = mAddress;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getCellPhoneNumber() {
        return mCellPhoneNumber;
    }

    public void setCellPhoneNumber(String mCellPhoneNumber) {
        this.mCellPhoneNumber = mCellPhoneNumber;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

}
