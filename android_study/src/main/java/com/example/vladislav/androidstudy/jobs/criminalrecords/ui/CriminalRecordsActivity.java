package com.example.vladislav.androidstudy.jobs.criminalrecords.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.vladislav.androidstudy.R;

public class CriminalRecordsActivity extends AppCompatActivity {

    public static final String DATABASE_NAME = "CrimesRecords";

    /**
     * Start this activity
     * @param context to start activity
     * @return  intent that starts this activity
     */
    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, CriminalRecordsActivity.class);
    }

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
                CriminalRecordListRXFragment.FRAGMENT_TAG);
//                CriminalRecordListFragment.FRAGMENT_TAG);
        if (criminalRecordFragment == null) {
//            criminalRecordFragment = CriminalRecordListFragment.newInstance();
            criminalRecordFragment = CriminalRecordListRXFragment.newInstance();
            criminalRecordFragment.setRetainInstance(true);
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, criminalRecordFragment,
                CriminalRecordListFragment.FRAGMENT_TAG)
                .commit();
    }
}
