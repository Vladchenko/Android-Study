package com.example.vladislav.androidstudy.fragments.fragments_activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vladislav.androidstudy.R;

public class FragmentsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);
        mButton = (Button) findViewById(R.id.one_fragment_button);
        mButton.setOnClickListener(this);
        mButton = (Button) findViewById(R.id.two_fragments_button);
        mButton.setOnClickListener(this);
        mButton = (Button) findViewById(R.id.dynamic_fragments_button);
        mButton.setOnClickListener(this);
    }

    public void gotoOneFragmentActivity() {
        Intent intent = new Intent(this, OneFragmentActivity.class);
        startActivity(intent);
    }

    public void gotoTwoFragmentsActivity() {
        Intent intent = new Intent(this, TwoFragmentsActivity.class);
        startActivity(intent);
    }

    public void gotoDynamicFragmentsActivity() {
        Intent intent = new Intent(this, DynamicFragmentsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.one_fragment_button: {
                gotoOneFragmentActivity();
                break;
            }
            case R.id.two_fragments_button: {
                gotoTwoFragmentsActivity();
                break;
            }
            case R.id.dynamic_fragments_button: {
                gotoDynamicFragmentsActivity();
                break;
            }
        }
    }
}
