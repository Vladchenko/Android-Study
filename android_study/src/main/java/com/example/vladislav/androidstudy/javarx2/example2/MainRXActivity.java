package com.example.vladislav.androidstudy.javarx2.example2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vladislav.androidstudy.R;

public class MainRXActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, MainRXActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reactive_x);
    }
}
