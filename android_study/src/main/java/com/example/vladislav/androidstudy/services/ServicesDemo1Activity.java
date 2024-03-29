package com.example.vladislav.androidstudy.services;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.services.demo1.BindIntentedService;
import com.example.vladislav.androidstudy.services.demo1.BindSimpleService;
import com.example.vladislav.androidstudy.services.demo1.BothIntentedService;
import com.example.vladislav.androidstudy.services.demo1.BothSimpleService;
import com.example.vladislav.androidstudy.services.demo1.StartedIntentService;
import com.example.vladislav.androidstudy.services.demo1.StartedSimpleService;

import static android.content.IntentFilter.SYSTEM_HIGH_PRIORITY;
import static android.content.IntentFilter.SYSTEM_LOW_PRIORITY;

/**
 * TODO Add javadoc and refine a code
 */
public class ServicesDemo1Activity extends AppCompatActivity {

    private Boolean mServiceType;
    private Class mServiceTypeClass;
    private ServiceConnection mServiceConnection;
    private BroadcastReceiver mBroadcastReceiver;
    private BroadcastReceiver mBroadcastReceiver2;  // Used in case an ordered broadcast is sent.

    public enum BroadcastKind {LOCAL, GLOBAL, PRIORITIZED}
    public static BroadcastKind broadcastKind = BroadcastKind.LOCAL;
//    private static boolean sLocalBroadcastReceiver = false;

    //* http://stackoverflow.com/questions/4442660/android-check-if-service-is-running-via-bindservice
    public static boolean bounded = false;
    public static final String BROADCAST_ID = "AndroidStudyBroadcast";
//    public static String BROADCAST_ID2 = "AndroidStudyBroadcast2";

    /**
     * Start this activity
     * @param context to start activity
     * @return  intent that starts this activity
     */
    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, ServicesDemo1Activity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_demo1);

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String string = intent.getExtras().getString(ServicesDemo1Activity.BROADCAST_ID);
                if (null != string) {
                    TextView textView = findViewById(R.id.service_log_contents_text_view);
                    textView.append(string);
                }
            }
        };

        IntentFilter mIntentFilter = new IntentFilter(ServicesDemo1Activity.BROADCAST_ID);

        // Registering broadcast receiver.
        switch (broadcastKind) {
            case LOCAL: {
                LocalBroadcastManager.getInstance(this).registerReceiver(
                        mBroadcastReceiver,
                        mIntentFilter);
                break;
            }
            case GLOBAL: {
                registerReceiver(mBroadcastReceiver, mIntentFilter);
                break;
            }
            case PRIORITIZED: {
                mBroadcastReceiver2 = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String string = intent.getExtras().getString(ServicesDemo1Activity.BROADCAST_ID);
                        if (null != string) {
                            TextView textView = findViewById(R.id.service_log_contents_text_view);
                            textView.append("BR2:" + string);
                        }
                    }
                };
                mIntentFilter.setPriority(SYSTEM_HIGH_PRIORITY);
                registerReceiver(mBroadcastReceiver, mIntentFilter);
                mIntentFilter = new IntentFilter(ServicesDemo1Activity.BROADCAST_ID);
                mIntentFilter.setPriority(SYSTEM_LOW_PRIORITY);
                registerReceiver(mBroadcastReceiver2, mIntentFilter);
                break;
            }
        }

        // Setting a buttons' click listeners.
        setListeners();

        TextView textView = findViewById(R.id.service_log_contents_text_view);
        // Making a text view for a log scrollable.
        textView.setMovementMethod(new ScrollingMovementMethod());

    }

    @Override
    public void onBackPressed() {
        if (ServicesDemo1Activity.bounded) {
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
        switch (broadcastKind) {
            case LOCAL: {
                LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
                break;
            }
            case GLOBAL: {
                unregisterReceiver(mBroadcastReceiver);
                break;
            }
            case PRIORITIZED: {
                unregisterReceiver(mBroadcastReceiver);
                unregisterReceiver(mBroadcastReceiver2);
                break;
            }
        }
    }

    private void assignStartButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(ServicesDemo1Activity.this, mServiceTypeClass);
                ServicesDemo1Activity.this.startService(mIntent);
            }
        });
    }

    private void assignStopButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(ServicesDemo1Activity.this, mServiceTypeClass);
                ServicesDemo1Activity.this.stopService(mIntent);
            }
        });
    }

    private void assignBindButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(ServicesDemo1Activity.this, mServiceTypeClass);
                ServicesDemo1Activity.this.bindService(mIntent, mServiceConnection, BIND_AUTO_CREATE);
            }
        });
    }

    private void assignUnbindButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(ServicesDemo1Activity.this, mServiceTypeClass);
                ServicesDemo1Activity.this.unbindService(mServiceConnection);
            }
        });
    }

    private void setListeners() {
        Button mBindButton;
        Button mStopButton;
        Button mStartButton;

        mServiceType = (Boolean) getIntent().getExtras().get("isIntendedService");

        mStartButton =  findViewById(R.id.service_column1_start_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent;
                if (false == mServiceType) {
                    mIntent = new Intent(ServicesDemo1Activity.this, StartedSimpleService.class);
                } else {
                    mIntent = new Intent(ServicesDemo1Activity.this, StartedIntentService.class);
                }
                ServicesDemo1Activity.this.startService(mIntent);
            }
        });
        mStartButton =  findViewById(R.id.service_column3_start_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent;
                if (false == mServiceType) {
                    mIntent = new Intent(ServicesDemo1Activity.this, BothSimpleService.class);
                } else {
                    mIntent = new Intent(ServicesDemo1Activity.this, BothIntentedService.class);
                }
                ServicesDemo1Activity.this.startService(mIntent);
            }
        });
        mStopButton =  findViewById(R.id.service_column1_stop_button);
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent;
                if (false == mServiceType) {
                    mIntent = new Intent(ServicesDemo1Activity.this, StartedSimpleService.class);
                } else {
                    mIntent = new Intent(ServicesDemo1Activity.this, StartedIntentService.class);
                }
                ServicesDemo1Activity.this.stopService(mIntent);
            }
        });
        mStopButton =  findViewById(R.id.service_column3_stop_button);
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent;
                if (false == mServiceType) {
                    mIntent = new Intent(ServicesDemo1Activity.this, BothSimpleService.class);
                } else {
                    mIntent = new Intent(ServicesDemo1Activity.this, BothIntentedService.class);
                }
                ServicesDemo1Activity.this.stopService(mIntent);
            }
        });

        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d("ServiceConnection", "TestStandActivity onServiceConnected");
//                bound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d("ServiceConnection", "TestStandActivity onServiceDisconnected");
            }
        };

        mBindButton =  findViewById(R.id.service_column2_bind_button);
        mBindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent;
                if (false == mServiceType) {
                    mIntent = new Intent(ServicesDemo1Activity.this, BindSimpleService.class);
                } else {
                    mIntent = new Intent(ServicesDemo1Activity.this, BindIntentedService.class);
                }
                ServicesDemo1Activity.this.bindService(mIntent, mServiceConnection, BIND_AUTO_CREATE);
            }
        });
        mBindButton =  findViewById(R.id.service_column3_bind_button);
        mBindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent;
                if (false == mServiceType) {
                    mIntent = new Intent(ServicesDemo1Activity.this, BothSimpleService.class);
                } else {
                    mIntent = new Intent(ServicesDemo1Activity.this, BothIntentedService.class);
                }
                ServicesDemo1Activity.this.bindService(mIntent, mServiceConnection, BIND_AUTO_CREATE);
            }
        });

        Button unbindButton =  findViewById(R.id.service_column2_unbind_button);
        unbindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent;
                if (false == mServiceType) {
                    mIntent = new Intent(ServicesDemo1Activity.this, BindSimpleService.class);
                } else {
                    mIntent = new Intent(ServicesDemo1Activity.this, BindIntentedService.class);
                }
                if (ServicesDemo1Activity.bounded) {
                    ServicesDemo1Activity.this.unbindService(mServiceConnection);
                }
            }
        });
        unbindButton =  findViewById(R.id.service_column3_unbind_button);
        unbindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent;
                if (false == mServiceType) {
                    mIntent = new Intent(ServicesDemo1Activity.this, BothSimpleService.class);
                } else {
                    mIntent = new Intent(ServicesDemo1Activity.this, BothIntentedService.class);
                }
                if (ServicesDemo1Activity.bounded) {
                    ServicesDemo1Activity.this.unbindService(mServiceConnection);
                }
            }
        });
    }

}