package com.example.vladislav.androidstudy.separatestudypackage;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.activities.dynamic_layout.buttons_inserting.AddButtonsActivity;
import com.example.vladislav.androidstudy.separatestudypackage.two_actions_activity.TimeDateActivity;

// This activity is made to run(test) different pieces of code.
public class TestStandActivity extends AppCompatActivity {

    public static final String mKey = "someConstant";
    public static final String mKey2 = "someConstant2";
    private static final String LOG_TAG = TestStandActivity.class.getSimpleName();
    private Handler handler;

    public static Intent newIntent(Context context) {
        return new Intent(context, TimeDateActivity.class);
    }

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
//        startSomeFragment();
//        startSomeFragmentWithBackStack();
//        startSomeActivity();
//        startActivityWithParameters();
//        startImplicitIntent();
        checkSharedPrefsOp();
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
        // The same Bundle comes in onCreate() callback
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(LOG_TAG, "onRestoreInstanceState");
        if (savedInstanceState != null) {
            Toast.makeText(this, savedInstanceState.getString(mKey), Toast.LENGTH_SHORT).show();
        }
    }

    private void startSomeFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container_temp, new FirstFragment()).commit();
        Toast.makeText(this, "Fragment started", Toast.LENGTH_SHORT).show();
    }

    private void startSomeFragmentWithTag() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // fragmentTag - is a tag that fragment can be identified by, when referred to.
        fragmentTransaction.add(R.id.fragment_container_temp, new FirstFragment(), "fragmentTag").commit();
        Toast.makeText(this, "Fragment started", Toast.LENGTH_SHORT).show();
    }

    private void startSomeFragmentWithBackStack() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Adding a fragment to backstack. When clicking back, it goes away. Without backstack,
        // clicking back would quit the app.
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.fragment_container_temp, new FirstFragment()).commit();
        Toast.makeText(this, "Fragment started", Toast.LENGTH_SHORT).show();
    }

    // These are the ways an activities could be started.
//    https://stackoverflow.com/questions/768969/passing-a-bundle-on-startactivity
    private void startSomeActivity() {

//        startActivity(new Intent(this, AddButtonsActivity.class));
        // This row fires a chooser saying - no apps can perform this action.
        startActivity(Intent.createChooser(new Intent(this, AddButtonsActivity.class),
                "Choose the app to proceed"));

        // Some extra way to start activity
//        startActivity(new Intent(this, AddButtonsActivity.class), Bundle.EMPTY);

        // One can put extra data to a bundle and pass it to a new activity, but it is the same as
        // putting this extra data to the same intent that starts activity - check
        // startActivityWithParameters() method given lower.

        // It is a Context that starts an activity. So, upper row could be replaced with 2 rows
        // next:
//        Context context = getApplicationContext();
//        context.startActivity(new Intent(this, AddButtonsActivity.class));

        // This is the way an activity from another app is started. No extra permission is required
        // in this case.
//        startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
    }

    // This method starts a choice of how the task is to be implemented.
    private void startImplicitIntent() {

        // Создаем текстовое сообщение
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Some message");
        sendIntent.setType("text/plain"); // "text/plain" MIME тип

        // Убеждаемся, что есть активити, которая может обработать интент
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(sendIntent, "Choose the app to implement the task"));
            // or without chooser
//            startActivity(sendIntent);
        }
    }

    // Listing and a methods that belong to Activity class.
    private void activitySpecificMethods() {
        findViewById(R.id.button_name_edit_text);
//        runOnUiThread(new Runnable());
//        ...
    }

    // Listing and a methods that belong to Context class.
    private void contextSpecificMethods() {

        // Some info about app
        getPackageName();
        getApplicationInfo();
        getPackageResourcePath();

        // This is how one gets an access to different app resources.
        getResources().getString(R.string.app_name);
        // Ome may omit the getResources(). since activity is a child of a app.Context and write
        // next way -
        getString(R.string.app_name);
        getAssets();

        // Content Resolver - some provider of data.
        getContentResolver();

        getMainLooper();
//        setTheme(R.id.resid);     getTheme();

        // This Shared Preferences file is for a current activity only. It's single.
        // It is accessible from any component of our app.
        // Keeps activity related data.
        // Filename is not set, comparing to a context shared pref-s file, it is set by default and
        // it is an activity name.
        getPreferences(Context.MODE_PRIVATE);
        // This Shared Preferences file is seen from a context and an activity as well. One can use
        // several files for it. It is accessible from any component of our app.
        // Keeps application related data.
        getSharedPreferences("file_name", Context.MODE_PRIVATE);
        getSharedPreferences("file_name", Context.MODE_WORLD_READABLE);
        getSharedPreferences("file_name", Context.MODE_WORLD_WRITEABLE);


        PreferenceManager.getDefaultSharedPreferences(this);
//        moveSharedPreferencesFrom(Context, "name"); // Requires API24.
//        deleteSharedPreferences("name");    // Requires API24. Why? Seems one can delete contents
        // of it, but not the very file.

//        openFileInput("filename");  // wrap with try
//        openFileOutput("filename", 1);  // wrap with try
//        deleteFile("filename");
        fileList();
//        getDataDir(""); getFilesDir();

//        openOrCreateDatabase();   also delete

//        sendBroadcast();

//        runOnUiThread();
//        ...
    }

    private boolean putDataToSharedPrefs(String fileName, String key, int value) {
        SharedPreferences sharedPreferences = getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
//        apply() was added in 2.3, it commits without returning a boolean indicating success or failure.
//        commit() returns true if the save works, false otherwise.
//        apply() was added as the Android dev team noticed that almost no one took notice of the return value, so apply is faster as it is asynchronous.
        return editor.commit();
    }

    private int getDataFromSharedPrefs(String fileName, String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, -1);
    }

    private void checkSharedPrefsOp(){
        putDataToSharedPrefs("ShPrefsFile", "gameCount", (int)(Math.random()*10));
        Log.i(LOG_TAG, "Game score is = " + getDataFromSharedPrefs("ShPrefsFile", "gameCount"));
    }

//    private void commonModel() {
//        getApplication().get
//    }
}
