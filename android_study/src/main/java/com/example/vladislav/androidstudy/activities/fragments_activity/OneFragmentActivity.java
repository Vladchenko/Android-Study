package com.example.vladislav.androidstudy.activities.fragments_activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.vladislav.androidstudy.R;

public class OneFragmentActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentManager mFragmentManager;
    private Fragment fragment1 = new Fragment1();
    private Fragment fragment2 = new Fragment2();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getFragmentManager();
        setContentView(R.layout.activity_one_fragment);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.one_fragment_container, fragment1);
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
        if (mFragmentManager.findFragmentById(R.id.one_fragment_container) == fragment1) {
            fragmentTransaction.replace(R.id.one_fragment_container, fragment2);
        } else {
            fragmentTransaction.replace(R.id.one_fragment_container, fragment1);
        }
        fragmentTransaction.commit();
    }
}
