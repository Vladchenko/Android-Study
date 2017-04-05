package com.example.vladislav.androidstudy;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.vladislav.androidstudy.R.*;

public class RotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_rotation);
        TextView textView = (TextView) findViewById(R.id.textView17);
        textView.setText("Vlad");
        EditText editText = (EditText) findViewById(id.editText4);
        editText.setText("Vlad");
        textView = (TextView) findViewById(R.id.textView18);

        // Making a string text to look like a web-link.
        final CharSequence text = textView.getText();
        final SpannableString spannableString = new SpannableString( text );
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
    }
}
