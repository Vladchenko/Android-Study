package com.example.vladislav.androidstudy.services;

import static com.example.vladislav.androidstudy.services.ServiceUtils.isMyServiceRunning;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vladislav.androidstudy.R;

/**
 * Activity that show demos for services implemented in the app.
 */
public class ServicesActivity extends AppCompatActivity {

    /**
     * On notifying an activity about a service's some work is done, see
     * <a href="https://stackoverflow.com/questions/4111398/notify-activity-from-service">...</a>
     * <a href="http://www.vogella.com/tutorials/AndroidServices/article.html">...</a>
     */

    private static final String LOG_TAG = "ServicesActivity";

    private ServiceDemo4 mServiceDemo4;
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

    /**
     * Start this activity
     * @param context to start activity
     * @return  intent that starts this activity
     */
    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, ServicesActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button button;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        mTextView = findViewById(R.id.service_data_received_text_view);
        button = findViewById(R.id.demo1_button);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, ServicesDemo1Activity.class);
            intent.putExtra("isIntendedService", ((CheckBox) findViewById(R.id.intent_service_checkbox)).isChecked());
            startActivity(intent);
        });
        button = findViewById(R.id.demo2_button);
        button.setOnClickListener(v -> startService(new Intent(this, ServiceDemo2.class)));
        button = findViewById(R.id.demo3_button);
        button.setOnClickListener(v -> startService(new Intent(this, ServiceDemo3.class)));
        prepareDemoService4();
        prepareDemoService5();
    }

    private void prepareDemoService4() {
        Button button = findViewById(R.id.start_demo4_service_button);
        button.setOnClickListener(v ->
                bindService(new Intent(this, ServiceDemo4.class),
                        mServiceConnection, BIND_AUTO_CREATE));
        button = findViewById(R.id.stop_demo4_service_button);
        button.setOnClickListener(v -> {
            // Get service instance and stop it.
            if (mServiceDemo4 == null) return;
            unbindService(mServiceConnection);
        });
        button = findViewById(R.id.request_data_demo4_service_button);
        button.setOnClickListener(v ->
            // Get request service data and show it in a toast.
            Toast.makeText(this, Integer.toString(mServiceDemo4.getServiceData()), Toast.LENGTH_SHORT).show()
        );
        mServiceConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.d(LOG_TAG, "ServicesActivity onServiceConnected");
                mServiceDemo4 = ((ServiceDemo4.LocalBinder) binder).getService();
            }

            public void onServiceDisconnected(ComponentName name) {
                Log.d(LOG_TAG, "ServicesActivity onServiceDisconnected");
                mServiceDemo4 = null;
            }
        };
    }

    private void prepareDemoService5() {
        Button button;
        button = findViewById(R.id.start_demo5_service_button);
        button.setOnClickListener(v -> {
            Intent intent2 = new Intent(this, ServiceDemo5.class);
            intent2.putExtra("MESSENGER", new Messenger(messageHandler));
            startService(intent2);
            Log.d(LOG_TAG, "Demo5 start button clicked");
        });
        button = findViewById(R.id.stop_demo5_service_button);
        button.setOnClickListener(v -> {
            stopService(new Intent(this, ServiceDemo5.class));
            Log.d(LOG_TAG, "Demo5 stop button clicked");
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isMyServiceRunning(this, ServiceDemo2.class)) {
            stopService(new Intent(this, ServiceDemo2.class));
        }
        if (isMyServiceRunning(this, ServiceDemo3.class)) {
            stopService(new Intent(this, ServiceDemo3.class));
        }
        if (isMyServiceRunning(this, ServiceDemo4.class)) {
            unbindService(mServiceConnection);
        }
        if (isMyServiceRunning(this, ServiceDemo5.class)) {
            stopService(new Intent(this, ServiceDemo5.class));
        }
    }
}
