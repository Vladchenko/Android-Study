package com.example.vladislav.androidstudy.kotlin.filesdownloading.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vladislav.androidstudy.R
import com.example.vladislav.androidstudy.javarx2.example5.createFilePath
import com.example.vladislav.androidstudy.kotlin.filesdownloading.network.DownloadState
import com.example.vladislav.androidstudy.kotlin.filesdownloading.network.FileDownloadApiMapper
import com.example.vladislav.androidstudy.kotlin.filesdownloading.utils.FileDownloadUtils.updateListOnlyWithNewItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Activity to display a files being downloaded.
 */
class CoroutinesFilesDownloadingDemoActivity : AppCompatActivity() {

    private var filesCount = 0
    private var isDownloading = false
    private lateinit var button: Button
    private lateinit var resultTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private var filesList = mutableListOf<DownloadState>()
    private lateinit var recyclerViewAdapter: FileModelsRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_java2_example5)
        initViews()
        initRecyclerView()
        button.setOnClickListener(clickListener)
    }

    private fun initViews() {
        resultTextView = findViewById(R.id.resultTextView)
        button = findViewById(R.id.run_downloading_button)
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerViewAdapter = FileModelsRecyclerViewAdapter()
        recyclerView.adapter = recyclerViewAdapter
    }

    private val clickListener = View.OnClickListener {
        if (!isDownloading) {
            // This coroutine will be destroyed when this activity destroyed,
            // since it is attached to lifecycle of this activity
            lifecycleScope.launch {
                downloadFiles(URL_LIST)
            }
        }
        isDownloading = true
    }

    private suspend fun downloadFiles(urls: List<String>) {
        val urlToFilePathMap = urls.associateWith { createFilePath(it, this) }
        urlToFilePathMap.entries.forEach { urlToFilePathEntry ->
            CoroutineScope(Dispatchers.IO).launch {
                FileDownloadApiMapper().saveFile(urlToFilePathEntry).collect { downloadState ->
                    processState(downloadState)
                    // Updating UI on a main thread
                    withContext(Dispatchers.Main) {// Dispatchers should be provided using dependency injection
                        recyclerViewAdapter.setFileProgressModels(filesList)
                        recyclerViewAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private suspend fun processState(downloadState: DownloadState) {
        when (downloadState) {
            is DownloadState.Downloading -> {
                updateListOnlyWithNewItems(filesList, downloadState)
            }

            is DownloadState.Failed -> {
                showError(downloadState.error?.message ?: "Some unknown error while downloading")
            }

            is DownloadState.Finished -> {
                filesCount++
                if (filesCount == URL_LIST.size) {
                    isDownloading = false
                    // Dispatchers should be provided using dependency injection
                    withContext(Dispatchers.Main) {
                        showResult("All files downloading complete")
                    }
                }
            }
        }
    }

    private fun showError(message: String) {
        resultTextView.setTextColor(getColor(R.color.colorAccent))
        resultTextView.text = message
    }

    private fun showResult(message: String) {
        resultTextView.setTextColor(getColor(R.color.color_green))
        resultTextView.text = message
    }

    /** @return [Intent] for CoroutinesFilesDownloadingDemoActivity, using [context] */
    fun newIntent(context: Context): Intent {
        return Intent(context, CoroutinesFilesDownloadingDemoActivity::class.java)
    }

    companion object {
        private val URL_LIST = listOf(
            "https://mp3bob.ru/download/muz/Numb_[mp3pulse.ru].mp3",
            "https://mp3bob.ru/download/muz02/Ruki_Vverkh_-_Ottepel_sample.mp3",
            "https://mp3bob.ru/download/muz/Ruki_Vverkh_-_Rasskazhi_Mne_[].mp3"
        )
    }
}