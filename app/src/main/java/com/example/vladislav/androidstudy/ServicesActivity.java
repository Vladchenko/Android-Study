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

import com.example.vladislav.androidstudy.services.SimpleService;

public class ServicesActivity extends AppCompatActivity {

    Button startButton;
    Button stopButton;
    Button bindButton;
    Button unbindButton;
    ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        startButton = (Button)findViewById(R.id.service_column1_start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(ServicesActivity.this, SimpleService.class);
                ServicesActivity.this.startService(mIntent);
            }
        });
        stopButton = (Button)findViewById(R.id.service_column1_stop_button);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(ServicesActivity.this, SimpleService.class);
                ServicesActivity.this.stopService(mIntent);
            }
        });
        serviceConnection = new ServiceConnection() {
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
        bindButton = (Button)findViewById(R.id.service_column2_bind_button);
        bindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(ServicesActivity.this, SimpleService.class);
                ServicesActivity.this.bindService(mIntent, serviceConnection, BIND_AUTO_CREATE);
            }
        });
        unbindButton = (Button)findViewById(R.id.service_column2_unbind_button);
        unbindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(ServicesActivity.this, SimpleService.class);
                ServicesActivity.this.unbindService(serviceConnection);
            }
        });
    }
}