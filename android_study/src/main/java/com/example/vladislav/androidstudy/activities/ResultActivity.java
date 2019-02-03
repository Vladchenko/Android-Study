package com.example.vladislav.androidstudy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vladislav.androidstudy.R;

public class ResultActivity extends AppCompatActivity {

    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Button button = (Button) findViewById(R.id.go_back_button);
        mEditText = (EditText) findViewById(R.id.result_edit_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = InitialActivity.newIntent(ResultActivity.this,
                        InitialActivity.ACTIVITY_RESULT_ID, mEditText.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}
