package com.example.vladislav.androidstudy.jobs.criminalrecords;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.vladislav.androidstudy.R;

public class CriminalRecordsActivity extends AppCompatActivity {

    public static final String DATABASE_NAME = "CrimesRecords";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criminal_records);
        addCriminalRecordFragment();
    }

    void addCriminalRecordFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment criminalRecordFragment = fragmentManager.findFragmentByTag(
                CriminalRecordFragment.FRAGMENT_TAG);
        if (criminalRecordFragment == null) {
            criminalRecordFragment = new CriminalRecordFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_container, criminalRecordFragment,
                    CriminalRecordFragment.FRAGMENT_TAG)
                    .commit();
        }
    }
}
