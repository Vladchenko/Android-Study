package com.example.vladislav.androidstudy.services;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.fragments.fragments_activity.DynamicFragmentsActivity;

import static com.example.vladislav.androidstudy.Utils.showToast;

public class ServicesActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * On notifying an mActivity about a service's some work is done, see
     * https://stackoverflow.com/questions/4111398/notify-activity-from-service
     * http://www.vogella.com/tutorials/AndroidServices/article.html
     */

    private static String LOG_TAG = "ServicesActivity";
    private Button mButton;
    private boolean mBound;
    private ServiceConnection mServiceConnection;
    private Service mService;
    private static Handler messageHandler = new MessageHandler();
    private static TextView textView;

    public static class MessageHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            int state = message.arg1;
            Log.i(LOG_TAG, " argument received:" + Integer.toString(state));
            textView.setText(Integer.toString(state));
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ServicesActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        textView = (TextView) findViewById(R.id.service_data_received_text_view);
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
        mButton = (Button) findViewById(R.id.start_demo5_service_button);
        mButton.setOnClickListener(this);
        mButton = (Button) findViewById(R.id.stop_demo5_service_button);
        mButton.setOnClickListener(this);
        // For ServiceDemo4
        mServiceConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.d(LOG_TAG, "ServicesActivity onServiceConnected");
                mBound = true;
                mService = ((ServiceDemo4.LocalBinder) binder).getService();
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
                showToast(this, "Service2 is launched.");
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
                // FIXME - Why is here no getServiceData method visible ?
//                mService.getServiceData
                break;
            }
            case R.id.stop_demo4_service_button: {
                // Get service instance and stop it.
                if (!mBound) return;
                unbindService(mServiceConnection);
                mBound = false;
                break;
            }
            case R.id.stop_demo5_service_button: {
                stopService(new Intent(this, ServiceDemo5.class));
                Log.d(LOG_TAG, "Demo5 stop button clicked");
                break;
            }
            case R.id.start_demo5_service_button: {
                Intent intent2 = new Intent(this, ServiceDemo5.class);
                intent2.putExtra("MESSENGER", new Messenger(messageHandler));
                startService(intent2);
//                Log.d(LOG_TAG, "Demo5 start button clicked");
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, ServiceDemo2.class));
        stopService(new Intent(this, ServiceDemo3.class));
        unbindService(mServiceConnection);
        stopService(new Intent(this, ServiceDemo5.class));
    }
}
