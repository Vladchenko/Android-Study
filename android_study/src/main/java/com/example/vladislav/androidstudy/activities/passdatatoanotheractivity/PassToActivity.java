package com.example.vladislav.androidstudy.activities.passdatatoanotheractivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;

import static com.example.vladislav.androidstudy.activities.passdatatoanotheractivity.PassFromActivity.mKey;

public class PassToActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_to);
        getData();
    }

    private void getData() {
        Intent intent = getIntent();
        TextView textView = (TextView)findViewById(R.id.text_from_another_activity_text_view);
        String string = intent.getExtras().getString(mKey);
        textView.append(string);
    }
}
