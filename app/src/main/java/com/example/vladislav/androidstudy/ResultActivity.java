package com.example.vladislav.androidstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        // Is there no way of referring to an activity itself
//        RelativeLayout layout = (RelativeLayout) findViewById(R.layout.activity_result);
//        Why is here no textView and button ?
        Button button = (Button) findViewById(R.id.go_back_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name", "Vlad");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}
