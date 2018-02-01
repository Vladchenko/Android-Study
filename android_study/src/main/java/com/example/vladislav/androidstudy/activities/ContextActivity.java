package com.example.vladislav.androidstudy.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;

public class ContextActivity extends AppCompatActivity {

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context);

        mTextView = (TextView) findViewById(R.id.textView22);
        demoRunOnUIThread();
    }

    private void demoContextMethods() {

        // Following methods are present in class android.content.Context;

        mTextView = (TextView) findViewById(R.id.textView22);
        // One has to run next piece of code in a separate thread
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //...
            }
        });
    }

    // This method is similar to Handler. It can work with UI thread, while being called from not
    // from a UI thread.
    private void demoRunOnUIThread() {
        // http://startandroid.ru/ru/uroki/vse-uroki-spiskom/148-urok-85-esche-neskolko-sposobov-vypolnenija-koda-v-ui-potoke.html
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText("runOnUiThread fulfilled");
                    }
                });
            }
        }).start();
    }
}
