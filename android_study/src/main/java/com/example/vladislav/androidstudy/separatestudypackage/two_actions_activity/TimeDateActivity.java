package com.example.vladislav.androidstudy.separatestudypackage.two_actions_activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.vladislav.androidstudy.separatestudypackage.two_actions_activity.ButtonsActivity.DATE_ACTION;
import static com.example.vladislav.androidstudy.separatestudypackage.two_actions_activity.ButtonsActivity.TIME_ACTION;

/**
 * This activity displays time or date. It depends on an action that came in an intent.
 */
public class TimeDateActivity extends AppCompatActivity {

    private TextView mInscriptionTextView;
    private TextView mValueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_date);
        mInscriptionTextView = (TextView) findViewById(R.id.inscription_text_view);
        mValueTextView = (TextView) findViewById(R.id.value_text_view);
        displayRespectiveData(getIntent());
    }

    // Displays time or date, depending on an action
    private void displayRespectiveData(Intent intent) {
        String action = intent.getAction();
        String format = null;
        switch (action) {
            case TIME_ACTION :
                mInscriptionTextView.setText("Time");
                format = "HH:mm:ss";
                break;
            case DATE_ACTION :
                mInscriptionTextView.setText("Date");
                format = "dd.MM.yyyy";
                break;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String datetime = sdf.format(new Date(System.currentTimeMillis()));
        mValueTextView.setText(datetime);
    }
}
