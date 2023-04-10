package com.example.vladislav.androidstudy.javarx2.example5

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
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * This example performs a files downloading, with a progress, using JavaRx.
 * Once downloading complete, textview informs about it.
 */
class RxJava2Example5Activity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var resultTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private var filesList = mutableListOf<FileModel>()
    private lateinit var recyclerViewAdapter: FilesRecyclerViewAdapter

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
        recyclerViewAdapter = FilesRecyclerViewAdapter()
        recyclerView.adapter = recyclerViewAdapter
    }

    private fun getClickListener() = View.OnClickListener {
        runDownloading(URL_LIST)
    }

    private fun runDownloading(urlsList: List<String>) {
        val files = Observable.fromIterable(urlsList)
            // Downloading is carried out in parallel
            .flatMap { downloadUrlToFile(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                showResult("${it.fileName}=${it.progress}")
            }
            .doOnComplete {
                showResult("All files download complete")
            }
            .doOnError {
                showError(it.message ?: " Unknown error")
            }
            .subscribe(
                {
                    // This is where current file begins its downloading
                    showResult("${extractFileNameFromPath(it.fileName)} download has begun")
                },
                {
                    showError(it.message ?: " Unknown error")
                }
            )
    }

    /**
     * Downloading is performed on a worker thread and fetches the result on a UI thread.
     * //TODO This code should be put to Presenter (MVP) or ViewModel (MVVM), but not in this example
     */
    private fun downloadUrlToFile(url: String): Observable<FileModel> {
        return Observable.create { emitter ->
            val filePath = createFilePath(url, this)
            // !!! https://stackoverflow.com/questions/27687907/android-os-networkonmainthreadexception-using-rxjava-on-android
            val fileObservable = NetworkApiMapper().downloadData(url, filePath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    // Notifies that downloading has begun for a filePath
                    emitter.onNext(FileModel(filePath, 0))
                }
                .doOnNext { file ->
                    // FileModels with a new percentage values are fed here
                    updateListOnlyWithNewItems(file)
                    recyclerViewAdapter.setFilesToDownload(filesList)
                    recyclerViewAdapter.notifyDataSetChanged()
                }
                .doOnComplete {
                    showResult("Some file download complete")
                    emitter.onComplete()
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
            /** Here has to be some subscriber, else its not gonna run ! */
        }
    }

    // Feed percentage value only when it is different to a previous one, since
    // it is fed every reading of data block and doesn't change for a long time.
    private fun updateListOnlyWithNewItems(file: FileModel) {
        var updated = false
        for (i in 0 until filesList.size) {
            if (filesList[i].fileName == file.fileName) {
                filesList[i] = file
                updated = true
            }
        }
        if (!updated) {
            filesList.add(file)
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

    /**
     * @return [Intent] for RxJava2Example5Activity, using [context]
     */
    fun newIntent(context: Context): Intent {
        return Intent(context, RxJava2Example5Activity::class.java)
    }

    companion object {
        private val URL_LIST = listOf(
            "https://mp3bob.ru/download/muz/Numb_[mp3pulse.ru].mp3",
            "https://mp3bob.ru/download/muz02/Ruki_Vverkh_-_Ottepel_sample.mp3",
            "https://mp3bob.ru/download/muz/Ruki_Vverkh_-_Rasskazhi_Mne_[].mp3"
        )
    }
}