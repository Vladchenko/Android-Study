package com.example.vladislav.androidstudy.javarx2.example7

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vladislav.androidstudy.R
import com.example.vladislav.androidstudy.javarx2.example5.FileModelsRecyclerViewAdapter
import com.example.vladislav.androidstudy.javarx2.example5.FileProgressModel
import com.example.vladislav.androidstudy.javarx2.example5.createFilePath
import com.example.vladislav.androidstudy.javarx2.example5.extractFileNameFromPath
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * This example performs a files downloading, with a progress, using JavaRx.
 * Once downloading complete, textview informs about it.
 */
class JavaRxExample7Activity : AppCompatActivity() {

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
        val fileObservable = FileDownLoadingApiMapper(
            DownloadProgressListener({
                updateListOnlyWithNewItems(
                    FileProgressModel(
                        extractFileNameFromPath(filePath),
                        it
                    )
                )
                // Switching back to UI thread, to fetch data
//                lifecycleScope.launch {
                recyclerViewAdapter.setFileProgressModels(filesList)
                recyclerViewAdapter.notifyDataSetChanged()
//                }
            }
            )).downloadFileWithProgress(url, filePath)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                // Notifies that downloading has begun for a filePath
                println("doOnSubscribe")
//                emitter.onNext(FileProgressModel(filePath, 0))
            }
            .doOnNext { file ->
                // FileModels with a new percentage values are fed here
                updateListOnlyWithNewItems(file)
                recyclerViewAdapter.setFileProgressModels(filesList)
                recyclerViewAdapter.notifyDataSetChanged()
            }
            .doOnComplete {
                showResult("Some file download complete")
//                emitter.onComplete()
            }
            .doOnError { error: Throwable ->
                showError(error.message ?: "Unknown error")
                // If use following method, then undeliverable exception occurs
                // emitter.onError(error)
            }
            .subscribe(
                {
                    // FileModels with a new percentage values are fed here
                    // Don't think this callback is needed
                },
                { showError(it.message ?: "Unknown error") }
            )
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

    private fun showError(message: String) {
        resultTextView.setTextColor(getColor(R.color.colorAccent))
        resultTextView.text = message
    }

    private fun showResult(message: String) {
        resultTextView.setTextColor(getColor(R.color.color_green))
        resultTextView.text = message
    }

    /** @return [Intent] for RxJava2Example5Activity, using [context] */
    fun newIntent(context: Context): Intent {
        return Intent(context, JavaRxExample7Activity::class.java)
    }

    companion object {
        private val URL_LIST = listOf(
            "https://mp3bob.ru/download/muz/Numb_[mp3pulse.ru].mp3",
            "https://mp3bob.ru/download/muz02/Ruki_Vverkh_-_Ottepel_sample.mp3",
            "https://mp3bob.ru/download/muz/Ruki_Vverkh_-_Rasskazhi_Mne_[].mp3"
        )
    }
}