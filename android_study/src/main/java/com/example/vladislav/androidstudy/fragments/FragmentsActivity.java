package com.example.vladislav.androidstudy.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vladislav.androidstudy.R;

/**
 * Activity displaying two fragments located in its layout statically
 */
public class FragmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments_two);
    }


}
