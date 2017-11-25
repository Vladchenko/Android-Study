package com.example.vladislav.androidstudy.handler;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vladislav.androidstudy.R;

public class HandlerActivity extends AppCompatActivity {

    private Handler mHandler;
    private ProgressBar mProgressBar;
    private TextView mTextView;
    private Button mButton;
    private int mTasks = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        mProgressBar = (ProgressBar)findViewById(R.id.background_task_progressbar);
        mTextView = (TextView) findViewById(R.id.background_task_text_view);
        mButton = (Button) findViewById(R.id.run_backgroubd_button);

        // This handler updates the UI, once a message is received.
        mHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                mTextView.setText(Integer.toString(msg.what) + " out of " + mTasks + " done.");
                if (msg.what == mTasks) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mButton.setEnabled(true);
                    mTextView.setText(getString(R.string.click_to_run));
                }
            }
        };

        assignListenerToButton();
    }

    private void assignListenerToButton() {

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);
                mButton.setEnabled(false);
                mTextView.setText(0 + " out of " + mTasks + " done.");
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            for (int i = 1; i <= mTasks; i++) {
                                Thread.sleep(1000);
                                mHandler.sendEmptyMessage(i);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });
    }

}
