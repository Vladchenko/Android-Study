package com.example.vladislav.androidstudy.activities.orientation_change;

import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.vladislav.androidstudy.R.*;

public class RotationActivity extends AppCompatActivity {

    private final String TAG = getClass().getCanonicalName();
    private final String KEY_INDEX = getClass().getCanonicalName();
    private EditText generatedValueEditText;
    private String mGeneratedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_rotation);

        TextView textView = (TextView) findViewById(id.textView17);
        textView.setText("Vlad");
        EditText editText = (EditText) findViewById(id.editText4);
        editText.setText("Vlad");
        textView = (TextView) findViewById(id.web_link_text_view);

        // Making a string text to look like a web-link.
        final CharSequence text = textView.getText();
        final SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new URLSpan(""), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString, TextView.BufferType.SPANNABLE);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://charlesharley.com/2012/programming/views-saving-instance-state-in-android"));
                startActivity(browserIntent);
            }
        });

        Log.i(TAG, "onCreate");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        // Saving a generated value for a case when an mActivity is recreated.
        savedInstanceState.putString(KEY_INDEX, mGeneratedValue);
    }

}
