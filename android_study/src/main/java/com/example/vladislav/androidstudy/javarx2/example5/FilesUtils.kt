package com.example.vladislav.androidstudy.javarx2.example5

import android.content.Context
import com.example.vladislav.androidstudy.kotlin.utils.createFilesDirIfAbsent
import java.io.File

/**
 * Get a file name from [url]
 */
fun extractFileNameFromUrl(url: String) = url.substring(url.lastIndexOf('/') + 1, url.length)

/**
 * Get a file name from [filePath]
 */
fun extractFileNameFromPath(filePath: String) = filePath.substring(filePath.lastIndexOf(':') + 1, filePath.length)

/**
 * Create a file path from [url], using [context]
 */
fun createFilePath(url: String, context: Context) =
    createFilesDirIfAbsent(context).path + File.pathSeparator + extractFileNameFromUrl(url)