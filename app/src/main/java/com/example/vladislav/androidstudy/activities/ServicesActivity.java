package com.example.vladislav.androidstudy.activities;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.services.ServiceDemo2;
import com.example.vladislav.androidstudy.services.ServiceDemo3;
import com.example.vladislav.androidstudy.services.ServiceDemo4;

public class ServicesActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * On notifying an activity about a service's some work is done, see
     * https://stackoverflow.com/questions/4111398/notify-activity-from-service
     */

    private String LOG_TAG = "ServicesActivity";
    private Button mButton;
    private boolean mBound;
    private ServiceConnection mServiceConnection;
    private Service mService;

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
        mButton = (Button) findViewById(R.id.start_demo4_service_button);
        mButton.setOnClickListener(this);
        mButton = (Button) findViewById(R.id.stop_demo4_service_button);
        mButton.setOnClickListener(this);
        mButton = (Button) findViewById(R.id.request_data_demo4_service_button);
        mButton.setOnClickListener(this);
        mServiceConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(LOG_TAG, "ServicesActivity onServiceConnected");
                mBound = true;
                ServiceDemo4.LocalBinder binder = (ServiceDemo4.LocalBinder) service;
                mService = binder.getService();
            }

            public void onServiceDisconnected(ComponentName name) {
                Log.d(LOG_TAG, "ServicesActivity onServiceDisconnected");
                mBound = false;
            }
        };
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
            case R.id.start_demo4_service_button: {
                intent = new Intent(this, ServiceDemo4.class);
                bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
                break;
            }
            case R.id.request_data_demo4_service_button: {
                // Get service instance, request data with it and show it in a, say toast.
//                mService.getSe
                break;
            }
            case R.id.stop_demo4_service_button: {
                // Get service instance and stop it.
                break;
            }
        }
    }
}
