package com.example.vladislav.androidstudy;

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

import com.example.vladislav.androidstudy.services.IntentedService;
import com.example.vladislav.androidstudy.services.SimpleService;

public class ServicesActivity extends AppCompatActivity {

    Button mStartButton;
    Button mStopButton;
    Button mBindButton;
    Button mUnbindButton;
    ServiceConnection mServiceConnection;
    Class mServiceTypeClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        if (false == (Boolean)getIntent().getExtras().get("isIntendedService")) {
            Log.i("Services Activity","Simple service is used.");
            mServiceTypeClass = SimpleService.class;
        } else {
            Log.i("Services Activity","Intended service is used.");
            mServiceTypeClass = IntentedService.class;
        }

        mStartButton = (Button)findViewById(R.id.service_column1_start_button);
        assignStartButton(mStartButton);
        mStartButton = (Button)findViewById(R.id.service_column3_start_button);
        assignStartButton(mStartButton);

        mStopButton = (Button)findViewById(R.id.service_column1_stop_button);
        assignStopButton(mStopButton);
        mStopButton = (Button)findViewById(R.id.service_column3_stop_button);
        assignStopButton(mStopButton);

        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d("ServiceConnection", "MainActivity onServiceConnected");
//                bound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d("ServiceConnection", "MainActivity onServiceDisconnected");
            }
        };

        mBindButton = (Button)findViewById(R.id.service_column2_bind_button);
        assignBindButton(mBindButton);
        mBindButton = (Button)findViewById(R.id.service_column3_bind_button);
        assignBindButton(mBindButton);

        mUnbindButton = (Button)findViewById(R.id.service_column2_unbind_button);
        assignUnbindButton(mUnbindButton);
        mUnbindButton = (Button)findViewById(R.id.service_column3_unbind_button);
        assignUnbindButton(mUnbindButton);

    }

    private void assignStartButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(ServicesActivity.this, mServiceTypeClass);
                ServicesActivity.this.startService(mIntent);
            }
        });
    }

    private void assignStopButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(ServicesActivity.this, mServiceTypeClass);
                ServicesActivity.this.stopService(mIntent);
            }
        });
    }

    private void assignBindButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(ServicesActivity.this, mServiceTypeClass);
                ServicesActivity.this.bindService(mIntent, mServiceConnection, BIND_AUTO_CREATE);
            }
        });
    }

    private void assignUnbindButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(ServicesActivity.this, mServiceTypeClass);
                ServicesActivity.this.unbindService(mServiceConnection);
            }
        });
    }
}