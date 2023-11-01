package com.example.vladislav.androidstudy.fragments.fragments_activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vladislav.androidstudy.R;

public class OneFragmentActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentManager mFragmentManager;
    private final Fragment mFragment1 = new Fragment1();
    private final Fragment mFragment2 = new Fragment2();

    /**
     * Start this activity
     * @param context to start activity
     * @return  intent that starts this activity
     */
    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, OneFragmentActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getFragmentManager();
        setContentView(R.layout.activity_one_fragment);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.one_fragment_container, mFragment1);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Do NOT attach onClickListener() in OnCreate(), since in this method, views are not yet created.
        LinearLayout layout = (LinearLayout)findViewById(R.id.one_fragment_layout);
        layout.setOnClickListener(this);    // Operates just as an onTouchListener() does.
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (mFragmentManager.findFragmentById(R.id.one_fragment_container) == mFragment1) {
            fragmentTransaction.replace(R.id.one_fragment_container, mFragment2);
        } else {
            fragmentTransaction.replace(R.id.one_fragment_container, mFragment1);
        }
        fragmentTransaction.commit();
    }
}
