package com.example.vladislav.androidstudy.intents;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vladislav.androidstudy.R;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.format;

public class IntentWithActionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_with_action);
        Bundle bundle = getIntent().getExtras();
        logExtrasContents(bundle);
    }

    private static void logExtrasContents(Bundle bundle) {
        Logger logger = Logger.getGlobal();
        if (bundle != null) {
            for (String item : bundle.keySet()) {
                // Look for "global" in logcat
                logger.log(Level.INFO, format("%s=%s", item, bundle.getString(item)));
            }
        } else {
            logger.log(Level.INFO, "Bundle is null");
        }
    }
}
