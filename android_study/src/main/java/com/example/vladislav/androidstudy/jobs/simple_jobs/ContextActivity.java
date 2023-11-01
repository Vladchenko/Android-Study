package com.example.vladislav.androidstudy.jobs.simple_jobs;

import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.vladislav.androidstudy.R;

import java.io.IOException;

import static android.os.Environment.getExternalStorageDirectory;
import static com.example.vladislav.androidstudy.Utils.printList;
import static com.example.vladislav.androidstudy.Utils.printStrings;

/**
 * Methods that present in android.content.Context
 */
public class ContextActivity extends AppCompatActivity {

    private TextView mTextView;
    private static final String TAG = ContextActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context);

        mTextView =  findViewById(R.id.textView22);
        demoContextMethods();
    }

    private void demoContextMethods() {

        // Following methods are present in class android.content.Context;

        // findViewById() finds and retrieves a view that is located on a current layout
        mTextView =  findViewById(R.id.textView22);

        // Here we dynamicly create a TextView. It needs a Context to be passed into, since it has
        // to know the environment it is added into (location, size)
        mTextView = new TextView(this);

        // One has to run next method in a separate thread. Contents of a run() method is run on a
        // UI thread. Check a simple example of this method in a demoRunOnUIThread() method lower
        // in this code.
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //...
            }
        });

        // Launching an activities
//        startActivity(new Intent());    // RuntimeException: Unable to start activity ComponentInfo
        // Caused by: android.content.ActivityNotFoundException: No Activity found to handle Intent {  }

//        startActivity(new Intent(), new Bundle());  // Same as previous

        mTextView.setText(getResources().getString(R.string.app_name));
        // Rather to use this one, omitting getResources()
        mTextView.setText(getString(R.string.app_name));

        try {
            // Printing files and folders present in "assets" folder
            printStrings(getAssets().list("")); // "" - default assets folder
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Printing locales
        printStrings(getAssets().getLocales());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.i(TAG, getDataDir().toString());
        }

    }

    // This method is similar to Handler. It can work with UI thread, while being called from not
    // from a UI thread.
    private void demoRunOnUIThread() {
        // http://startandroid.ru/ru/uroki/vse-uroki-spiskom/148-urok-85-esche-neskolko-sposobov-vypolnenija-koda-v-ui-potoke.html
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Once one did a work on a worker thread, one may update UI using next method
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText("runOnUiThread fulfilled");
                    }
                });
            }
        }).start();
    }
}
