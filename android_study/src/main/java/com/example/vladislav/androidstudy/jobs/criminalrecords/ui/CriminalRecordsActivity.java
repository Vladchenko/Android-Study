package com.example.vladislav.androidstudy.jobs.criminalrecords.ui;

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
        // When there is no savedInstanceState (this means app starts for the first time), then add
        // fragment with a list of a crimes, else leave it as it is
        if (savedInstanceState == null) {
            addCriminalRecordListFragment();
        }
    }

    /**
     * Добавление фрагмента со списком преступлений
     */
    public void addCriminalRecordListFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment criminalRecordFragment = fragmentManager.findFragmentByTag(
                CriminalRecordListFragment.FRAGMENT_TAG);
        if (criminalRecordFragment == null) {
            criminalRecordFragment = CriminalRecordListFragment.newInstance();
            criminalRecordFragment.setRetainInstance(true);
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, criminalRecordFragment,
                CriminalRecordListFragment.FRAGMENT_TAG)
                .commit();
    }
}
