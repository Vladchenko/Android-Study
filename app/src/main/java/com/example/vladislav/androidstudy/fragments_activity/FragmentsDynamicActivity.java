package com.example.vladislav.androidstudy.fragments_activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.fragments_activity.Fragment1;
import com.example.vladislav.androidstudy.fragments_activity.Fragment2;

public class FragmentsDynamicActivity extends AppCompatActivity implements View.OnClickListener,
        Fragment1.OnFragmentInteractionListener,
        Fragment2.OnFragmentInteractionListener {

    Button mButtonAdd;
    Button mButtonReplace;
    Button mButtonRemove;
    Fragment mFragment1;
    Fragment mFragment2;
    Fragment mFragment;
    CheckBox mCheckBox;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments_dynamic);
        mFragment1 = new Fragment1();
        mFragment2 = new Fragment2();
        mButtonAdd = (Button) findViewById(R.id.add_button);
        mButtonAdd.setOnClickListener(this);
        mButtonReplace = (Button) findViewById(R.id.replace_button);
        mButtonReplace.setOnClickListener(this);
        mButtonRemove = (Button) findViewById(R.id.remove_button);
        mButtonRemove.setOnClickListener(this);
        mCheckBox = (CheckBox) findViewById(R.id.backstack_checkbox);
    }

    @Override
    public void onClick(View v) {
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        // Getting the current fragment
        mFragment = mFragmentManager.findFragmentById(R.id.fragment_container);
        switch (v.getId()) {
            case R.id.add_button: {
                if (null == mFragment) {
                    mFragmentTransaction.add(R.id.fragment_container, mFragment1);
                    break;
                }
                if (mFragment1.isAdded()
                        && mFragment2.isAdded()) {
                    break;
                }
                if (mFragment.equals(mFragment1)) {
                    mFragmentTransaction.add(R.id.fragment_container, mFragment2);
                    break;
                }
                if (mFragment.equals(mFragment2)) {
                    mFragmentTransaction.add(R.id.fragment_container, mFragment1);
                    break;
                }
                break;
            }
            case R.id.replace_button: {
                if (null != mFragment) {
                    if (null != mFragment1
                            && mFragment.equals(mFragment1)) {
                        mFragmentTransaction.replace(R.id.fragment_container, mFragment2);
                    }
                    if (null != mFragment2
                            && mFragment.equals(mFragment2)) {
                        mFragmentTransaction.replace(R.id.fragment_container, mFragment1);
                    }
                }
                break;
            }
            case R.id.remove_button: {
                mFragmentTransaction.remove(mFragment1);
                mFragmentTransaction.remove(mFragment2);
                break;
            }
        }
        if (mCheckBox.isChecked()) mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
