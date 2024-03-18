package com.example.vladislav.androidstudy.kotlin.filesdownloading.task2.presentation

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.vladislav.androidstudy.R
import com.example.vladislav.androidstudy.kotlin.filesdownloading.FilesUrls.Companion.URL_LIST
import com.example.vladislav.androidstudy.kotlin.filesdownloading.task2.data.FileDownloaderImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

/**
 * Activity to demonstrate an [android.app.DownloadManager] operating.
 */
class DownloadManagerDemoActivity : AppCompatActivity() {

    private lateinit var downloadButton: Button
    private lateinit var descriptionTextView: TextView
    private lateinit var openDownloadsScreenButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_downloadmanager)
        initViewsAndButtons()
    }

    private fun initViewsAndButtons() {
        downloadButton = findViewById(R.id.run_downloading_button)
        openDownloadsScreenButton = findViewById(R.id.open_downloads_button)
        descriptionTextView = findViewById(R.id.description_text_view)
        downloadButton.setOnClickListener(downloadButtonClickListener)
        openDownloadsScreenButton.setOnClickListener(openDownloadsScreenButtonClickListener)
    }

    private val downloadButtonClickListener = View.OnClickListener {
        val downloader = FileDownloaderImpl(this)
        URL_LIST.forEach {
            downloader.downloadFile(it)
            lifecycleScope.launch(Dispatchers.IO) {
                getProgress().collect { // FIXME - this progress is not updated fast enough
                    println(it)
                }
            }
        }
    }

    private val openDownloadsScreenButtonClickListener = View.OnClickListener {
        val downloadsViewer = DownloadsViewImpl(this)
        downloadsViewer.viewDownloadsFolder()
    }

    private fun getProgress(): Flow<String> {
        return flow {
            val downloadManager = getSystemService(DownloadManager::class.java)
            var status: Int = DownloadManager.STATUS_RUNNING
            while (status == DownloadManager.STATUS_RUNNING) {
                val query = DownloadManager.Query()
                val cursor = downloadManager.query(query)
                if (cursor.moveToFirst()) {
                    val titleColumnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_TITLE)
                    val statusColumnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                    status = cursor.getInt(statusColumnIndex)
                    val totalSizeIndex =
                        cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)
                    val downloadedIndex =
                        cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
                    val title = cursor.getString(titleColumnIndex)
                    val totalSize = cursor.getInt(totalSizeIndex)
                    val downloaded = cursor.getInt(downloadedIndex)
                    var progress = 0.0
                    if (totalSize != -1) {
                        progress = downloaded * 100.0 / totalSize
                    }
                    emit(title + progress)  //FIXME This progress is not updated in timely manner
                }
                cursor.close()
            }
        }
    }

    /** @return [Intent] for CoroutinesFilesDownloadingDemoActivity, using [context] */
    fun newIntent(context: Context): Intent {
        return Intent(context, DownloadManagerDemoActivity::class.java)
    }
}