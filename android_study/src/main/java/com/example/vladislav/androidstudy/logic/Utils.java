package com.example.vladislav.androidstudy.logic;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Влад on 30.07.2017.
 */

public class Utils {

    public static void showToast(Activity activity, String text) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
    }
}
