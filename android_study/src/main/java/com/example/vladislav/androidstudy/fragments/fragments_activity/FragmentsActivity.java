package com.example.vladislav.androidstudy.fragments.fragments_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vladislav.androidstudy.R;

public class FragmentsActivity extends AppCompatActivity {


    public static Intent newIntent(Context context) {
        return new Intent(context, FragmentsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button button;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);
        button = findViewById(R.id.one_fragment_button);
        button.setOnClickListener(v -> gotoOneFragmentActivity());
        button = findViewById(R.id.two_fragments_button);
        button.setOnClickListener(v -> gotoTwoFragmentsActivity());
        button = findViewById(R.id.dynamic_fragments_button);
        button.setOnClickListener(v -> gotoDynamicFragmentsActivity());
    }

    public void gotoOneFragmentActivity() {
        Intent intent = new Intent(this, OneFragmentActivity.class);
        startActivity(intent);
    }

    public void gotoTwoFragmentsActivity() {
        Intent intent = new Intent(this, TwoFragmentsActivity.class);
        startActivity(intent);
    }

    public void gotoDynamicFragmentsActivity() {
        Intent intent = new Intent(this, DynamicFragmentsActivity.class);
        startActivity(intent);
    }
}
