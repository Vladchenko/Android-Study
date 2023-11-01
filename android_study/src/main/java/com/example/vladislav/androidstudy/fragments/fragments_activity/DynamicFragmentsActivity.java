package com.example.vladislav.androidstudy.fragments.fragments_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.vladislav.androidstudy.R;

import static com.example.vladislav.androidstudy.Utils.showToast;

public class DynamicFragmentsActivity extends AppCompatActivity {

    private Fragment mFragment;
    private CheckBox mCheckBox;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    /**
     * Start this activity
     * @param context to start activity
     * @return  intent that starts this activity
     */
    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, DynamicFragmentsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fragment mFragment2;
        Fragment mFragment1;
        Button mButtonRemove;
        Button mButtonReplace;
        Button mButtonAdd;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments_dynamic);
        mFragment1 = new Fragment();
        mFragment2 = new Fragment();
        mButtonAdd = findViewById(R.id.add_button);
        mButtonAdd.setOnClickListener(v -> {
            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            // Getting the current fragment, if there is one present in the container.
            mFragment = mFragmentManager.findFragmentById(R.id.fragment_container);
            if (mFragment == null) {
                mFragmentTransaction.add(R.id.fragment_container, mFragment1);
                return;
            }
            if (mFragment1.isAdded()
                    && mFragment2.isAdded()) {
                showToast(this, getString(R.string.both_fragments_added));
                return;
            }
            if (mFragment.equals(mFragment1)) {
                mFragmentTransaction.add(R.id.fragment_container, mFragment2);
                return;
            }
            if (mFragment.equals(mFragment2)) {
                mFragmentTransaction.add(R.id.fragment_container, mFragment1);
            }
            if (mCheckBox.isChecked()) {
                mFragmentTransaction.addToBackStack(null);
            }
            mFragmentTransaction.commit();
        });
        mButtonReplace = findViewById(R.id.replace_button);
        mButtonReplace.setOnClickListener(v -> {
            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            // Getting the current fragment, if there is one present in the container.
            mFragment = mFragmentManager.findFragmentById(R.id.fragment_container);
            if (mFragment != null) {
                if (mFragment.equals(mFragment1)) {
                    mFragmentTransaction.replace(R.id.fragment_container, mFragment2);
                }
                if (mFragment.equals(mFragment2)) {
                    mFragmentTransaction.replace(R.id.fragment_container, mFragment1);
                }
            }
            if (mCheckBox.isChecked()) {
                mFragmentTransaction.addToBackStack(null);
            }
            mFragmentTransaction.commit();
        });
        mButtonRemove = findViewById(R.id.remove_button);
        mButtonRemove.setOnClickListener(v -> {
            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            // Getting the current fragment, if there is one present in the container.
            mFragment = mFragmentManager.findFragmentById(R.id.fragment_container);
            mFragmentTransaction.remove(mFragment1);
            mFragmentTransaction.remove(mFragment2);
            if (mCheckBox.isChecked()) {
                mFragmentTransaction.addToBackStack(null);
            }
            mFragmentTransaction.commit();
        });
        mCheckBox = findViewById(R.id.backstack_checkbox);
    }
}
