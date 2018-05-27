package com.example.vladislav.androidstudy;

import java.util.List;

/**
 * Created by Влад on 27.05.2018.
 */

public class Utils {

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

}
