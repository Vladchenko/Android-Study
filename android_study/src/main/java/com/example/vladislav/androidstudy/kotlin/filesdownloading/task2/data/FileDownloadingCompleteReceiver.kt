package com.example.vladislav.androidstudy.kotlin.filesdownloading.task2.data

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.widget.Toast
import com.example.vladislav.androidstudy.R
import com.example.vladislav.androidstudy.kotlin.utils.getNameFromUrl


/**
 * Receiving broadcasts on files downloading statuses
 */
class FileDownloadingCompleteReceiver : BroadcastReceiver() {

    private lateinit var downloadManager: DownloadManager

    override fun onReceive(context: Context?, intent: Intent?) {
        downloadManager = context?.getSystemService(DownloadManager::class.java)!!
        if (intent?.action == DOWNLOAD_COMPLETE_ACTION) {
            val query = DownloadManager.Query().setFilterById()
            val cursor = downloadManager.query(query)
            if (cursor.moveToFirst()) {
                val statusColumnIndex: Int = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                val status = cursor.getInt(statusColumnIndex)
                actOnStatus(status, cursor, context)
            }
        }
    }

    private fun actOnStatus(status: Int, cursor: Cursor, context: Context?) {
        when (status) {
            DownloadManager.STATUS_SUCCESSFUL -> {
                val uri =
                    Uri.parse(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)))
                Toast.makeText(
                    context,
                    "${uri.toString().getNameFromUrl()} downloaded successfully",
                    Toast.LENGTH_LONG
                ).show()
                println(uri.toString().getNameFromUrl())
            }

            DownloadManager.STATUS_FAILED -> Toast.makeText(
                context,
                R.string.download_error_message,
                Toast.LENGTH_LONG
            ).show()

            else -> Toast.makeText(context, "status is $status", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private const val DOWNLOAD_COMPLETE_ACTION = "android.intent.action.DOWNLOAD_COMPLETE"
    }
}