package com.example.vladislav.androidstudy.kotlin.filesdownloading.task2.data

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.example.vladislav.androidstudy.kotlin.utils.getNameFromUrl

/**
 * FileDownloader implementation
 *
 * @param context android.content.Context
 */
class FileDownloaderImpl(val context: Context) : FileDownloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadFile(url: String): Long {
        val fileName = url.getNameFromUrl()
        val request = DownloadManager.Request(url.toUri())
            .setTitle(fileName)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "$DOWNLOAD_FOLDER_NAME/$fileName"
            )
        return downloadManager.enqueue(request)
    }

    companion object {
        private const val DOWNLOAD_FOLDER_NAME = "AndroidStudyDownloadFolder"
    }
}