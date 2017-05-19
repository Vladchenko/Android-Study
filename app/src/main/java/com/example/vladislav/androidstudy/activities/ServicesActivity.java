package com.example.vladislav.androidstudy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.example.vladislav.androidstudy.R;

public class ServicesActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
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
                intent = new Intent(this, ServicesDemo1Activity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
