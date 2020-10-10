package com.example.vladislav.androidstudy.kotlin.utils

import android.content.Context
import java.io.File

/**
 * Files utils.
 *
 * @author Yanchenko Vladislav
 * @since 14.03.2021
 */

/**
 * Creates default files directory
 */
fun createFilesDirIfAbsent(context: Context): File {
    val dir = File(context.filesDir.path)
    if (!dir.exists()) {
        dir.mkdir()
        println("Directory $dir has been created")
    } else {
        println("Directory $dir already exists")
    }
    return dir
}
