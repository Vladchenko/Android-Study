package com.example.vladislav.androidstudy.activities;

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

public class ServicesActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * On notifying an activity about a service's some work is done, see
     * https://stackoverflow.com/questions/4111398/notify-activity-from-service
     */

    private String LOG_TAG = "ServicesActivity";
    private Button mButton;
    private boolean bound;
    private ServiceConnection serviceConnection;

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
        mButton = (Button) findViewById(R.id.demo4_button);
        mButton.setOnClickListener(this);
        serviceConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.d(LOG_TAG, "ServicesActivity onServiceConnected");
                bound = true;
            }

            public void onServiceDisconnected(ComponentName name) {
                Log.d(LOG_TAG, "ServicesActivity onServiceDisconnected");
                bound = false;
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
            case R.id.demo4_button: {
                intent = new Intent(this, ServiceDemo3.class);
                bindService(intent, serviceConnection, BIND_AUTO_CREATE);
                break;
            }
        }
    }
}
