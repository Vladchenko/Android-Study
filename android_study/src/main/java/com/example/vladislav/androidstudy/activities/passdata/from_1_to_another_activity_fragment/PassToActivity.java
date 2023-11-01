package com.example.vladislav.androidstudy.activities.passdata.from_1_to_another_activity_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vladislav.androidstudy.R;

import static com.example.vladislav.androidstudy.activities.passdata.from_1_to_another_activity_fragment.PassFromActivity.mKey;

/**
 * This activity is meant to receive data to
 */
public class PassToActivity extends AppCompatActivity {

    MyParcelable2 mMyParcelable2;

    /**
     * Start this activity
     * @param context to start activity
     * @return  intent that starts this activity
     */
    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, PassToActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_to);
        getData();
    }

    private void getData() {
        Intent intent = getIntent();
        TextView textView = findViewById(R.id.text_from_another_activity_text_view);
        String string = intent.getExtras().getString(mKey);
        mMyParcelable2 = intent.getParcelableExtra(mKey);
        if (string != null) {
            textView.append(string);
        }
    }
}
