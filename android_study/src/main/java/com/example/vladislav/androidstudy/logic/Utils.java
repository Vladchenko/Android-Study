package com.example.vladislav.androidstudy.logic;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by Влад on 30.07.2017.
 */

public class Utils {

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
