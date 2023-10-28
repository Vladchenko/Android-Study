package com.example.vladislav.androidstudy.services;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vladislav.androidstudy.R;

import static com.example.vladislav.androidstudy.Utils.showToast;

/**
 * TODO
 */
public class ServicesActivity extends AppCompatActivity {

    /**
     * On notifying an mActivity about a service's some work is done, see
     * <a href="https://stackoverflow.com/questions/4111398/notify-activity-from-service">...</a>
     * <a href="http://www.vogella.com/tutorials/AndroidServices/article.html">...</a>
     */

    private static final String LOG_TAG = "ServicesActivity";

    private boolean mBound;
    private ServiceConnection mServiceConnection;
    private final Handler messageHandler = new MessageHandler();
    private TextView mTextView;

    public class MessageHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            int state = message.arg1;
            Log.i(LOG_TAG, " argument received:" + state);
            mTextView.setText(Integer.toString(state));
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ServicesActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button mButton;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        mTextView = findViewById(R.id.service_data_received_text_view);
        mButton = findViewById(R.id.demo1_button);
        mButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ServicesDemo1Activity.class);
            intent.putExtra("isIntendedService", ((CheckBox) findViewById(R.id.intent_service_checkbox)).isChecked());
            startActivity(intent);
        });
        mButton = findViewById(R.id.demo2_button);
        mButton.setOnClickListener(v -> {
            showToast(this, "Service2 is launched.");
            startService(new Intent(this, ServiceDemo2.class));
        });
        mButton = findViewById(R.id.demo3_button);
        mButton.setOnClickListener(v -> startService(new Intent(this, ServiceDemo3.class)));
        mButton = findViewById(R.id.start_demo4_service_button);
        mButton.setOnClickListener(v -> bindService(new Intent(this, ServiceDemo4.class),
                mServiceConnection, BIND_AUTO_CREATE));
        mButton = findViewById(R.id.stop_demo4_service_button);
        mButton.setOnClickListener(v -> {
            // Get service instance and stop it.
            if (!mBound) return;
            unbindService(mServiceConnection);
            mBound = false;
        });
        mButton = findViewById(R.id.request_data_demo4_service_button);
        mButton.setOnClickListener(v -> {
            // Get service instance, request data with it and show it in a, say toast.
            // FIXME - Why is here no getServiceData method visible ?
//                mService.getServiceData
        });
        mButton = findViewById(R.id.start_demo5_service_button);
        mButton.setOnClickListener(v -> {
            Intent intent2 = new Intent(this, ServiceDemo5.class);
            intent2.putExtra("MESSENGER", new Messenger(messageHandler));
            startService(intent2);
            Log.d(LOG_TAG, "Demo5 start button clicked");
        });
        mButton = findViewById(R.id.stop_demo5_service_button);
        mButton.setOnClickListener(v -> {
            stopService(new Intent(this, ServiceDemo5.class));
            Log.d(LOG_TAG, "Demo5 stop button clicked");
        });
        // For ServiceDemo4
        mServiceConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.d(LOG_TAG, "ServicesActivity onServiceConnected");
                mBound = true;
                ((ServiceDemo4.LocalBinder) binder).getService();
            }

            public void onServiceDisconnected(ComponentName name) {
                Log.d(LOG_TAG, "ServicesActivity onServiceDisconnected");
                mBound = false;
            }
        };
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
