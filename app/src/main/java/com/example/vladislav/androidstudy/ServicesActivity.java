package com.example.vladislav.androidstudy;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vladislav.androidstudy.services.BindIntentedService;
import com.example.vladislav.androidstudy.services.BindSimpleService;
import com.example.vladislav.androidstudy.services.BothIntentedService;
import com.example.vladislav.androidstudy.services.BothSimpleService;
import com.example.vladislav.androidstudy.services.StartedIntentService;
import com.example.vladislav.androidstudy.services.StartedSimpleService;

public class ServicesActivity extends AppCompatActivity {

    private TextView textView;
    private Button mStartButton;
    private Button mStopButton;
    private Button mBindButton;
    private Button mUnbindButton;
    private Boolean mServiceType;
    private Class mServiceTypeClass;
    private ServiceConnection mServiceConnection;
    private BroadcastReceiver mBroadcastReceiver;
    private static boolean sLocalBroadcastReceiver = false;

    //* http://stackoverflow.com/questions/4442660/android-check-if-service-is-running-via-bindservice
    public static boolean BOUND = false;
    public static String BROADCAST_ID = "AndroidStudyBroadcast";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String string = intent.getExtras().getString(ServicesActivity.BROADCAST_ID);
//                Log.i("DOES","DOES");
                if (null != string) {
                    TextView textView = (TextView) findViewById(R.id.service_log_contents_text_view);
                    textView.append(string);
//                    myscroll.FullScroll(FocusSearchDirection.Down);
                }
            }
        };

        IntentFilter mIntentFilter = new IntentFilter(ServicesActivity.BROADCAST_ID);

        // Registering broadcast receiver.
        if (issLocalBroadcastReceiver()) {
            LocalBroadcastManager.getInstance(this).registerReceiver(
                    mBroadcastReceiver,
                    mIntentFilter);
        } else {
            registerReceiver(mBroadcastReceiver, mIntentFilter);
        }

        mServiceType = (Boolean) getIntent().getExtras().get("isIntendedService");

        mStartButton = (Button) findViewById(R.id.service_column1_start_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent;
                if (false == mServiceType) {
                    mIntent = new Intent(ServicesActivity.this, StartedSimpleService.class);
                } else {
                    mIntent = new Intent(ServicesActivity.this, StartedIntentService.class);
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
                    mIntent = new Intent(ServicesActivity.this, BothSimpleService.class);
                } else {
                    mIntent = new Intent(ServicesActivity.this, BothIntentedService.class);
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
                    mIntent = new Intent(ServicesActivity.this, StartedSimpleService.class);
                } else {
                    mIntent = new Intent(ServicesActivity.this, StartedIntentService.class);
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
                    mIntent = new Intent(ServicesActivity.this, BothSimpleService.class);
                } else {
                    mIntent = new Intent(ServicesActivity.this, BothIntentedService.class);
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
                    mIntent = new Intent(ServicesActivity.this, BindSimpleService.class);
                } else {
                    mIntent = new Intent(ServicesActivity.this, BindIntentedService.class);
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
                    mIntent = new Intent(ServicesActivity.this, BothSimpleService.class);
                } else {
                    mIntent = new Intent(ServicesActivity.this, BothIntentedService.class);
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
                    mIntent = new Intent(ServicesActivity.this, BindSimpleService.class);
                } else {
                    mIntent = new Intent(ServicesActivity.this, BindIntentedService.class);
                }
                if (ServicesActivity.BOUND) {
                    ServicesActivity.this.unbindService(mServiceConnection);
                }
            }
        });
        mUnbindButton = (Button) findViewById(R.id.service_column3_unbind_button);
        mUnbindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent;
                if (false == mServiceType) {
                    mIntent = new Intent(ServicesActivity.this, BothSimpleService.class);
                } else {
                    mIntent = new Intent(ServicesActivity.this, BothIntentedService.class);
                }
                if (ServicesActivity.BOUND) {
                    ServicesActivity.this.unbindService(mServiceConnection);
                }
            }
        });

        textView = (TextView)findViewById(R.id.service_log_contents_text_view);
        // Making a text view for a log scrollable.
        textView.setMovementMethod(new ScrollingMovementMethod());

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
        if (ServicesActivity.BOUND) {
            unbindService(mServiceConnection);
            mServiceConnection = null;
        }
        // exception should not be present in a first place
        //
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!issLocalBroadcastReceiver()) {
            unregisterReceiver(mBroadcastReceiver);
        } else {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
        }
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

    public static boolean issLocalBroadcastReceiver() {
        return sLocalBroadcastReceiver;
    }

    public static void setsLocalBroadcastReceiver(boolean sLocalBroadcastReceiver) {
        ServicesActivity.sLocalBroadcastReceiver = sLocalBroadcastReceiver;
    }

}