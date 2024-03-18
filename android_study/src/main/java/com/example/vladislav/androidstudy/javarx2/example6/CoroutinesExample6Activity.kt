package com.example.vladislav.androidstudy.javarx2.example6

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
import com.example.vladislav.androidstudy.javarx2.example5.FileModelsRecyclerViewAdapter
import com.example.vladislav.androidstudy.javarx2.example5.FileProgressModel
import com.example.vladislav.androidstudy.javarx2.example5.createFilePath
import com.example.vladislav.androidstudy.javarx2.example5.extractFileNameFromPath
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * This example performs a files downloading, with a progress, using Coroutines.
 * Once downloading complete, textview informs about it.
 */
class CoroutinesExample6Activity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var resultTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private var filesList = mutableListOf<FileProgressModel>()
    private lateinit var recyclerViewAdapter: FileModelsRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_java2_example5)
        initViews()
        initRecyclerView()
        button.setOnClickListener(getClickListener())
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

    private fun getClickListener() = View.OnClickListener {
        for (url in URL_LIST) {
            runDownloading(url)
        }
    }

    private fun runDownloading(url: String) {
        val filePath = createFilePath(url, this)
        // Running code on UI thread
        lifecycleScope.launch {
            // Running code on worker thread
            withContext(Dispatchers.IO) {
                FileDownloadingApiMapper().downloadFileWithProgress(
                    url,
                    filePath,
                    DownloadProgressListener({
                        updateListOnlyWithNewItems(
                            FileProgressModel(
                                extractFileNameFromPath(filePath),
                                it
                            )
                        )
                        // Switching back to UI thread, to fetch data
                        lifecycleScope.launch {
                            recyclerViewAdapter.setFileProgressModels(filesList)
                            recyclerViewAdapter.notifyDataSetChanged()
                        }
                    })
                )
            }
        }
    }

    // Feed percentage value only when it is different to a previous one, since
    // it is fed every reading of data block and doesn't change for a long time.
    private fun updateListOnlyWithNewItems(fileProgressModel: FileProgressModel) {
        var updated = false
        for (i in 0 until filesList.size) {
            if (filesList[i].fileName == fileProgressModel.fileName) {
                filesList[i] = fileProgressModel
                updated = true
            }
        }
        if (!updated) {
            filesList.add(fileProgressModel)
        }
    }

    /** @return [Intent] for CoroutinesExample6Activity, using [context] */
    fun newIntent(context: Context): Intent {
        return Intent(context, CoroutinesExample6Activity::class.java)
    }

    companion object {
        private val URL_LIST = listOf(
            "https://mp3bob.ru/download/muz/Numb_[mp3pulse.ru].mp3",
            "https://mp3bob.ru/download/muz02/Ruki_Vverkh_-_Ottepel_sample.mp3",
            "https://mp3bob.ru/download/muz/Ruki_Vverkh_-_Rasskazhi_Mne_[].mp3"
        )
    }
}