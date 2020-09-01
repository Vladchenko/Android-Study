package com.example.vladislav.androidstudy.activities.orientation_change;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vladislav.androidstudy.beans.Planet;

import static com.example.vladislav.androidstudy.R.id;
import static com.example.vladislav.androidstudy.R.layout;

public class RotationActivity extends AppCompatActivity {

    private static final String PARCELABLE_ID = "Id for parcelable for RotationActivity";
    private final String TAG = getClass().getCanonicalName();
    private final String KEY_INDEX = getClass().getCanonicalName();
    private EditText generatedValueEditText;
    private String mGeneratedValue;
    private Planet mPlanet;

    public static Intent newIntent(Context context, Parcelable planet) {
        Intent intent = new Intent(context, RotationActivity.class);
        intent.putExtra(PARCELABLE_ID, planet);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_rotation);

        mPlanet = getIntent().getParcelableExtra(PARCELABLE_ID);

        // Although mPlanet might have a values different to the ones received on a first activity
        // start up, (say, when it was recreated because of a rotation), these values will not be changed
        // in any activity callback, except in a onRestoreInstanceState()
        if (mPlanet != null) {
            EditText editText = (EditText) findViewById(id.generated_value_edit_text);
            editText.setText(Double.toString(mPlanet.getDistance()));
        }

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
                mPlanet.setDistance(50);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://charlesharley.com/2012/programming/views-saving-instance-state-in-android"));
                startActivity(browserIntent);
            }
        });

        Log.i(TAG, "onCreate");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPlanet = getIntent().getParcelableExtra(PARCELABLE_ID);

        if (mPlanet != null) {
            EditText editText = (EditText) findViewById(id.generated_value_edit_text);
            // Setting text in onCreate(), when activity is recreated won't have an effect
            editText.setText(Double.toString(mPlanet.getDistance()));
        }
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
        savedInstanceState.putParcelable(PARCELABLE_ID, mPlanet);
    }

}
