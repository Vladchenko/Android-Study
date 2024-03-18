package com.example.vladislav.androidstudy.kotlin.filesdownloading.task2.presentation

import android.app.DownloadManager
import android.content.Context
import android.content.Intent

/**
 * [DownloadsView] implementation.
 */
class DownloadsViewImpl(val context: Context) : DownloadsView {

    override fun viewDownloadsFolder() {
        context.startActivity(
            Intent().apply {
                action = DownloadManager.ACTION_VIEW_DOWNLOADS
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        )
    }
}