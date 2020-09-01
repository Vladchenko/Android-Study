package com.example.vladislav.androidstudy.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vladislav.androidstudy.R;

import static android.app.Activity.RESULT_OK;

public class ResultActivity extends AppCompatActivity {

    EditText mEditText;

    public static Intent newIntent(Context context) {
        return new Intent(context, ResultActivity.class);
    }

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
