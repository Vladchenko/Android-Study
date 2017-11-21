package com.example.vladislav.androidstudy;

import android.app.Application;
import android.content.res.Configuration;
import android.widget.Toast;

/**
 * Created by Влад on 18.11.2017.
 */

// One needs to set next row in an AndroidManifest.xml
// android:name="com.example.vladislav.androidstudy.MyApplication"
// in an <application> node
//
// to use this instance of Application.

// To read
// https://github.com/codepath/android_guides/wiki/Understanding-the-Android-Application-Class
    // https://stackoverflow.com/questions/18002227/why-extend-an-application-class
    // https://stackoverflow.com/questions/3826905/singletons-vs-application-context-in-android
    // https://developer.android.com/reference/android/app/Application.html
    // http://www.programering.com/a/MzM4MDNwATU.html

    // Normally, developers do not extend this class
public class MyApplication extends Application {

    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, getResources().getText(R.string.application_created_text),
                Toast.LENGTH_SHORT).show();
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Toast.makeText(this, getResources().getText(R.string.low_memory_text),
                Toast.LENGTH_SHORT).show();
    }

}
