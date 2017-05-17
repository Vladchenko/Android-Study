package com.example.vladislav.androidstudy.activities.asynctask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.intentstudy.IntentsActivity;

public class AsyncTasksActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_tasks);

        mButton = (Button) findViewById(R.id.asynctask1_button);
        mButton.setOnClickListener(this);

        mButton = (Button) findViewById(R.id.asynctask2_button);
        mButton.setOnClickListener(this);

        mButton = (Button) findViewById(R.id.asynctask3_button);
        mButton.setOnClickListener(this);

        mButton = (Button) findViewById(R.id.asynctask4_button);
        mButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.asynctask1_button: {
                intent = new Intent(this, AsyncTask1Activity.class);
                startActivity(intent);
                break;
            }
            case R.id.asynctask2_button: {
                intent = new Intent(this, AsyncTask2Activity.class);
                startActivity(intent);
                break;
            }
            case R.id.asynctask3_button: {
                intent = new Intent(this, AsyncTask3Activity.class);
                startActivity(intent);
                break;
            }
            case R.id.asynctask4_button: {
                intent = new Intent(this, AsyncTask4Activity.class);
                startActivity(intent);
                break;
            }
        }
    }

}
