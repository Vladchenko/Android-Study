package com.example.vladislav.androidstudy;

import android.app.Activity;
import android.app.ActivityManager;
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
import com.example.vladislav.androidstudy.services.IntentedService2;
import com.example.vladislav.androidstudy.services.IntentedService3;
import com.example.vladislav.androidstudy.services.SimpleService;
import com.example.vladislav.androidstudy.services.SimpleService2;
import com.example.vladislav.androidstudy.services.SimpleService3;

import java.util.List;

public class ServicesActivity extends AppCompatActivity {

    Button mStartButton;
    Button mStopButton;
    Button mBindButton;
    Button mUnbindButton;
    Boolean mServiceType;
    ServiceConnection mServiceConnection;
    Class mServiceTypeClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        mServiceType = (Boolean) getIntent().getExtras().get("isIntendedService");

        mStartButton = (Button) findViewById(R.id.service_column1_start_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent;
                if (false == mServiceType) {
                    mIntent = new Intent(ServicesActivity.this, SimpleService.class);
                } else {
                    mIntent = new Intent(ServicesActivity.this, IntentedService.class);
                }
                ServicesActivity.this.startService(mIntent);
            }
        });
        mStartButton = (Button) findViewById(R.id.service_column3_start_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent;
                if (false == mServiceType) {
                    mIntent = new Intent(ServicesActivity.this, SimpleService3.class);
                } else {
                    mIntent = new Intent(ServicesActivity.this, IntentedService3.class);
                }
                ServicesActivity.this.startService(mIntent);
            }
        });
        mStopButton = (Button) findViewById(R.id.service_column1_stop_button);
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent;
                if (false == mServiceType) {
                    mIntent = new Intent(ServicesActivity.this, SimpleService.class);
                } else {
                    mIntent = new Intent(ServicesActivity.this, IntentedService.class);
                }
                ServicesActivity.this.stopService(mIntent);
            }
        });
        mStopButton = (Button) findViewById(R.id.service_column3_stop_button);
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent;
                if (false == mServiceType) {
                    mIntent = new Intent(ServicesActivity.this, SimpleService3.class);
                } else {
                    mIntent = new Intent(ServicesActivity.this, IntentedService3.class);
                }
                ServicesActivity.this.stopService(mIntent);
            }
        });

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

        mBindButton = (Button) findViewById(R.id.service_column2_bind_button);
        mBindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent;
                if (false == mServiceType) {
                    mIntent = new Intent(ServicesActivity.this, SimpleService2.class);
                } else {
                    mIntent = new Intent(ServicesActivity.this, IntentedService2.class);
                }
                ServicesActivity.this.bindService(mIntent, mServiceConnection, BIND_AUTO_CREATE);
            }
        });
        mBindButton = (Button) findViewById(R.id.service_column3_bind_button);
        mBindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent;
                if (false == mServiceType) {
                    mIntent = new Intent(ServicesActivity.this, SimpleService3.class);
                } else {
                    mIntent = new Intent(ServicesActivity.this, IntentedService3.class);
                }
                ServicesActivity.this.bindService(mIntent, mServiceConnection, BIND_AUTO_CREATE);
            }
        });

        mUnbindButton = (Button) findViewById(R.id.service_column2_unbind_button);
        mUnbindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent;
                if (false == mServiceType) {
                    mIntent = new Intent(ServicesActivity.this, SimpleService2.class);
                } else {
                    mIntent = new Intent(ServicesActivity.this, IntentedService2.class);
                }
                ServicesActivity.this.unbindService(mServiceConnection);
            }
        });
        mUnbindButton = (Button) findViewById(R.id.service_column3_unbind_button);
        mUnbindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent;
                if (false == mServiceType) {
                    mIntent = new Intent(ServicesActivity.this, SimpleService3.class);
                } else {
                    mIntent = new Intent(ServicesActivity.this, IntentedService3.class);
                }
                ServicesActivity.this.unbindService(mServiceConnection);
            }
        });

    }

    @Override
    public void onBackPressed() {
//        ActivityManager am = (ActivityManager)this.getSystemService(Activity.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningServiceInfo> rs = am.getRunningServices(50);
//        String message = null;
//
//        for (int i=0; i<rs.size(); i++) {
//            ActivityManager.RunningServiceInfo rsi = rs.get(i);
//            rsi.service.
//        }
        super.onBackPressed();
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