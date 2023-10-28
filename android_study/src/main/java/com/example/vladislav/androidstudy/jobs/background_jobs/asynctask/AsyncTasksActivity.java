package com.example.vladislav.androidstudy.jobs.background_jobs.asynctask;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vladislav.androidstudy.R;

public class AsyncTasksActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button button;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_tasks);

        button = findViewById(R.id.asynctask1_button);
        button.setOnClickListener(v -> startActivity(AsyncTask1Activity.newIntent(this)));

        button = findViewById(R.id.asynctask2_button);
        button.setOnClickListener(v -> startActivity(new Intent(this, AsyncTask2Activity.class)));

        button = findViewById(R.id.asynctask3_button);
        button.setOnClickListener(v -> startActivity(new Intent(this, AsyncTask3Activity.class)));

        button = findViewById(R.id.asynctask4_button);
        button.setOnClickListener(v -> startActivity(new Intent(this, AsyncTask4Activity.class)));

    }
}
