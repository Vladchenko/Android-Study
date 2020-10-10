package com.example.vladislav.androidstudy;

import android.content.Context;
import android.widget.Toast;

import com.example.vladislav.androidstudy.kotlin.demo.ExtensionFunctionsDemoKt;

import java.util.List;

/**
 * Created by Влад on 27.05.2018.
 */

public class Utils {

    public static final String TAG = Utils.class.getClass().toString();

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void printStrings(String[] list) {
        if (list != null) {
            if (list.length == 0) {
                System.out.println("Items not present");
                return;
            }
            for (String item : list) {
                System.out.println("List item: " + item);
            }
        }
    }

    public static void printList(List<String> list) {
        printStrings((String[])list.toArray());
    }

    private static void KotlinExtesionsDemo() {
        // This is how an extension function is called from Kotlin, within Java
        System.out.println(ExtensionFunctionsDemoKt.lastChar(""));
    }

}
