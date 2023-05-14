package com.example.vladislav.androidstudy.javarx2.example7

import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object FileUtils {
    fun getFileOutputStream(fileName: String): FileOutputStream {
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)
        return try {
            FileOutputStream(file)
        } catch (e: IOException) {
            throw RuntimeException("Failed to create output stream", e)
        }
    }
}