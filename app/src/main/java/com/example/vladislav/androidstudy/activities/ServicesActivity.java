package com.example.vladislav.androidstudy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.services.ServiceDemo2;
import com.example.vladislav.androidstudy.services.ServiceDemo3;

public class ServicesActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        mButton = (Button) findViewById(R.id.demo1_button);
        mButton.setOnClickListener(this);
        mButton = (Button) findViewById(R.id.demo2_button);
        mButton.setOnClickListener(this);
        mButton = (Button) findViewById(R.id.demo3_button);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.demo1_button: {
                intent = new Intent(this, ServicesDemo1Activity.class);
                intent.putExtra("isIntendedService", ((CheckBox) findViewById(R.id.intent_service_checkbox)).isChecked());
                startActivity(intent);
                break;
            }
            case R.id.demo2_button: {
                intent = new Intent(this, ServiceDemo2.class);
                startService(intent);
                break;
            }
            case R.id.demo3_button: {
                intent = new Intent(this, ServiceDemo3.class);
                startService(intent);
                break;
            }
        }
    }
}
