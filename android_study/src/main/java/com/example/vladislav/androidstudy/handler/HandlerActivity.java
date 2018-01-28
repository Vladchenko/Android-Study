package com.example.vladislav.androidstudy.handler;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;

import java.util.Locale;

public class HandlerActivity extends AppCompatActivity {

    private Handler mHandler;
    private ProgressBar mProgressBar;
    private TextView mTextView;
    private Button mUITaskButton;
    private Button mUITaskSeparateThreadButton;
    private Button mUseHandlerButton;
    private Button mUseHandler2Button;
    private Button mUseHandler3Button;
    private int mTasks = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        mProgressBar = (ProgressBar) findViewById(R.id.background_task_progressbar);
        mTextView = (TextView) findViewById(R.id.background_task_text_view);
        mUITaskButton = (Button) findViewById(R.id.handler_tasks_run_on_UI_button);
        mUITaskSeparateThreadButton = (Button) findViewById(R.id.handler_tasks_run_on_UI_and_update_button);
        mUseHandlerButton = (Button) findViewById(R.id.handler_tasks_run_on_UI_and_update_button2);
        mUseHandler2Button = (Button) findViewById(R.id.handler_tasks_run_in_background_button);
        mUseHandler3Button = (Button) findViewById(R.id.handler_tasks_run_in_background_button2);

        assignListenersToButtons();
    }

    // Simulating some computations and update UI each step
    private void computeAndUpdateUI() {
        for (int i = 0; i < 10; i++) {
            // Instead of computation, let's make a delay
            try {
                // This results in UI thread freeze and not updating it every step
                Thread.sleep(400);
                mTextView.setText(String.format(Integer.toString(i), Locale.getDefault()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Simulating some computations and update UI each step in a separate thread
    private void computeAndUpdateUIThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    // Instead of computation, let's make a delay
                    try {
                        Thread.sleep(400);
                        mTextView.setText(String.format(Integer.toString(i), Locale.getDefault()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    // Handler with no separate thread for computations used
    private Handler provideHandler() {
        // This handler updates the UI, once a message is received.
        return new Handler() {
            // This method is performed on UI !
            public void handleMessage(android.os.Message msg) {
                computeAndUpdateUI();
            }
        };
    }

    // Handler with a separate thread for computations and informing a UI, by handleMessage(), once
    // the computations are done
    private Handler provideHandler2() {
        // This handler updates the UI, once a message is received.
        return new Handler() {
            // This method is performed on UI !
            public void handleMessage(android.os.Message msg) {
                mTextView.setText(Integer.toString(msg.what) + " out of " + mTasks + " done.");
                if (msg.what == mTasks) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mUseHandler2Button.setEnabled(true);
                    mTextView.setText(getString(R.string.handler_tasks_click_to_run_text));
                }
            }
        };
    }

    private Handler provideHandler3() {
        return new Handler(Looper.getMainLooper());
    }


    private void assignListenersToButtons() {

        mUITaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Since this method is executed on UI thread, it makes a UI to freeze
                computeAndUpdateUI();
            }
        });

        mUITaskSeparateThreadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Since this method is executed on separate thread, it DOESN'T make a UI to freeze,
                // but it cannot update a UI from itself and causes an exception
                //
                // FATAL EXCEPTION: Thread-108
                // android.view.ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
                computeAndUpdateUIThread();
            }
        });

        mUseHandlerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler = provideHandler();
                mProgressBar.setVisibility(View.VISIBLE);
                mUseHandlerButton.setEnabled(false);
                mTextView.setText(0 + " out of " + mTasks + " done.");
                // Wrong approach, it still freezes UI. One has to use a separate thread to send
                // messages.
                for (int i = 1; i <= mTasks; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mHandler.sendEmptyMessage(i);
                }
            }
        });

        // This approach is doing good - it uses Message
        mUseHandler2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler = provideHandler2();
                mProgressBar.setVisibility(View.VISIBLE);
                mUseHandler2Button.setEnabled(false);
                mTextView.setText(0 + " out of " + mTasks + " done.");
                // Making some computations and once they are done, informing the UI
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

        // Another approach is also doing good - it uses Runnable
        mUseHandler3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Previous handler will do just fine here
                mHandler = provideHandler3();
                mProgressBar.setVisibility(View.VISIBLE);
                mUseHandler3Button.setEnabled(false);
                mTextView.setText(0 + " out of " + mTasks + " done.");
                // Making some computations and once they are done, informing the UI
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            for (int i = 1; i <= mTasks; i++) {
                                Thread.sleep(1000);
                                final int finalI = i;
                                // One could use, say mTextView.post(...) to implement next code
                                mHandler.post(new Runnable() {
                                    // That's done on a UI thread
                                    @Override
                                    public void run() {
                                        mTextView.setText(String.format(
                                                Integer.toString(finalI), Locale.getDefault()));
                                        // Since handleMessage(msg) method is not called in this
                                        // case, one needs to do following
                                        if (finalI == mTasks) {
                                            mProgressBar.setVisibility(View.GONE);
                                            mUseHandler3Button.setEnabled(true);
                                            mTextView.setText(getString(
                                                    R.string.handler_tasks_click_to_run_text));
                                        }
                                    }
                                });
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