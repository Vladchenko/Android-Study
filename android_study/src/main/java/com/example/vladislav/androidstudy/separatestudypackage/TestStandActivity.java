package com.example.vladislav.androidstudy.separatestudypackage;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.activities.AddButtonsActivity;

// This activity is made to run(test) different pieces of code.
public class TestStandActivity extends AppCompatActivity {

    public static final String mKey = "someConstant";
    public static final String mKey2 = "someConstant2";
    private static final String LOG_TAG = TestStandActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//        startSomeFragment();
        startSomeActivity();
//        startActivityWithParameters();
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
    }

    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume ");
    }

    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    // This callback will not work before API 21.
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString(mKey, "value");
        Log.d(LOG_TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState, outPersistentState);
    }

    // This callback is fired when activity is recreated, for example when screen is rotated.
    // https://stackoverflow.com/questions/12793069/android-onsaveinstancestate-not-being-called-from-activity
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(LOG_TAG, "onRestoreInstanceState");
        if (savedInstanceState != null) {
            Toast.makeText(this, savedInstanceState.getString(mKey), Toast.LENGTH_SHORT).show();
        }
    }

    private void makeDelay() {

        // The point was to make some delay. before a UI thread could run some action.

//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(4000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.start();
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private void startSomeFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Adding a fragment to backstack. When pushing back, it goes away.
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.fragment_container_temp, new FirstFragment()).commit();
        Toast.makeText(this, "Fragment started", Toast.LENGTH_SHORT).show();
    }

    // These are the ways an activities could be started.
//    https://stackoverflow.com/questions/768969/passing-a-bundle-on-startactivity
    private void startSomeActivity() {

//        startActivity(new Intent(this, AddButtonsActivity.class));

        // Some extra way to start activity
//        startActivity(new Intent(this, AddButtonsActivity.class), Bundle.EMPTY);

        // One can put extra data to a bundle and pass it to a new activity, but it is the same as
        // putting this extra data to the same intent that starts activity - check
        // startActivityWithParameters() method given lower.

        // It is a Context that starts an activity. So, upper row could be replaced with 2 rows
        // lower:
//        Context context = getApplicationContext();
//        context.startActivity(new Intent(this, AddButtonsActivity.class));

        // This is the way an activity from another app is started. No extra permission is required
        // in this case.
        startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
    }


    // Passing parameters using putExtra() method from Intent.
//    https://stackoverflow.com/questions/768969/passing-a-bundle-on-startactivity
    private void startActivityWithParameters() {

        String stringParam = "String Parameter";
        Integer intParam = 5;

        Intent i = new Intent(this, AddButtonsActivity.class);

        i.putExtra(mKey, stringParam);
        i.putExtra(mKey2, intParam);

        startActivity(i);

        // These parameters could be used in a called activity like following:
        // Bundle bundle = i.getExtras();
        // String value = bundle.get(mKey);
        // or
        // String value = getIntent().getExtras().getString(mKey)
    }


    // Passing parameters using Bundle from Intent.
//    https://stackoverflow.com/questions/768969/passing-a-bundle-on-startactivity
    private void startActivityWithParameters2() {

        String value = "someValue";
        Intent intent = new Intent(this, AddButtonsActivity.class);
        Bundle extras = intent.getExtras();
        extras.putString(mKey, value);
        startActivity(intent);

        // These parameters could be used in a called activity like following:
        // Bundle bundle = i.getExtras();
        // String value = bundle.get(mKey);
        // or
        // String value = getIntent().getExtras().getString(mKey)
    }


    // Passing parameters creating a new Bundle.
    private void startActivityWithParameters3() {

        String value = "someValue";
        Intent intent = new Intent(this, AddButtonsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(mKey, value);
        intent.putExtras(bundle);
        startActivity(intent);

        // These parameters could be used in a called activity like following:
        // Bundle bundle = i.getExtras();
        // String value = bundle.get(mKey);
        // or
        // String value = getIntent().getExtras().getString(mKey)
    }


    private void startActivityWithParcelable() {
        // Starting an activity with passing some data into it, using bundle.
        Bundle bundle = new Bundle();
        bundle.putParcelable("VALUE", new MyParcelable2());
        startActivity(new Intent(this, AddButtonsActivity.class), bundle);
    }


//    private void commonModel() {
//        getApplication().get
//    }
}
