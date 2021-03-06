package com.example.vladislav.androidstudy.fragments.fragments_activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vladislav.androidstudy.R;

import static com.example.vladislav.androidstudy.Utils.showToast;

public class DynamicFragmentsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButtonAdd;
    private Button mButtonReplace;
    private Button mButtonRemove;
    private Fragment mFragment1;
    private Fragment mFragment2;
    private Fragment mFragment;
    private CheckBox mCheckBox;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    public static Intent newIntent(Context context) {
        return new Intent(context, DynamicFragmentsActivity.class);
    }

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
        // Getting the current fragment, if there is one present in the container.
        mFragment = mFragmentManager.findFragmentById(R.id.fragment_container);
        switch (v.getId()) {
            case R.id.add_button: {
                if (mFragment == null) {
                    mFragmentTransaction.add(R.id.fragment_container, mFragment1);
                    break;
                }
                if (mFragment1.isAdded()
                        && mFragment2.isAdded()) {
                    showToast(this, getString(R.string.both_fragments_added));
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
                if (mFragment != null) {
                    if (mFragment1 != null
                            && mFragment.equals(mFragment1)) {
                        mFragmentTransaction.replace(R.id.fragment_container, mFragment2);
                    }
                    if (mFragment2 != null
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
        if (mCheckBox.isChecked()) {
            mFragmentTransaction.addToBackStack(null);
        }
        mFragmentTransaction.commit();
    }
}
