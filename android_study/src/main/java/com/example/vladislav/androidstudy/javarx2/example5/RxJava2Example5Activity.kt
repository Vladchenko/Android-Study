package com.example.vladislav.androidstudy.javarx2.example5

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.vladislav.androidstudy.R
import com.example.vladislav.androidstudy.kotlin.utils.createFilesDirIfAbsent
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.File

/**
 * This example performs a file downloading, with a progress, using JavaRx.
 * Once downloading complete, textview says where file has been downloaded.
 */
class RxJava2Example5Activity : AppCompatActivity() {

    private var button: Button? = null
    private var textView: TextView? = null
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_java2_example3)
        initViews()
        button!!.setOnClickListener(getClickListener())
    }

    private fun initViews() {
        textView = findViewById(R.id.rxjava2_example3_text_view)
        button = findViewById(R.id.rxjava2_example3_button)
        progressBar = findViewById(R.id.rxjava2_example3_progress_bar)
    }

    private fun getClickListener() = View.OnClickListener {
        var i = 0
        Handler().postDelayed({
            while (i < URL_LIST.size) {
                runDownloading(URL_LIST[i])
                i++
            }
        }, 100)
    }

    private fun runDownloading(url: String) {
        downloadUrlToFile(
            url,
            url.substring(url.lastIndexOf('/') + 1, url.length - 1)
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { textView?.text = "File$1" }
            .doOnComplete { textView?.text = "File$1 downloaded" }
            .doOnError { textView?.text = it.message }
            // .subscribe { ProgressObservableEmitter() }
            .subscribe(
                { textView?.text = it.toString() },
                { textView?.text = it.message }
            )
    }

    /**
     * Downloading is performed on a worker thread and fetches the result on a UI thread.
     * //TODO This code should be put to Presenter (MVP) or ViewModel (MVVM)
     */
    @SuppressLint("CheckResult")
    private fun downloadUrlToFile(url: String, fileName: String): Observable<String> {
        return Observable.create { emitter ->
            val filePath = createFilesDirIfAbsent(this).path + File.pathSeparator + fileName
            // !!! https://stackoverflow.com/questions/27687907/android-os-networkonmainthreadexception-using-rxjava-on-android
            NetworkApiMapper().downloadData(url, filePath)
                // .map { response: Response? ->
                //     ResponseSaver().saveDataToFile(
                //         filePath,
                //         response!!
                //     )
                // }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    //TODO Tell recycler to add an item
                    // progressBar!!.visibility = View.VISIBLE
                    // button!!.visibility = View.INVISIBLE
                    // textView!!.text = getString(R.string.downloading_by_url, url)
                    emitter.onNext(filePath)
                }
                .doOnNext { text ->
                    // textView!!.text = text
                }
                .doOnComplete {
                    //TODO Tell recycler to remove progressbar in item
                    // progressBar!!.visibility = View.INVISIBLE
                    // button!!.visibility = View.VISIBLE
                    emitter.onComplete()
                }
                .doOnError { error: Throwable ->
                    // progressBar!!.visibility = View.INVISIBLE
                    // textView!!.text = getString(R.string.download_error_message, error.message)
                }
                .subscribe { ProgressObservableEmitter() }
            /** Here has to be some subscriber, else its not gonna run ! */
        }
    }

    private fun showSuccessfulStatus(file: Unit) {
        textView!!.text = getString(R.string.file_downloaded_to_message, "Done")
    }

    private fun showFailedStatus(exception: Throwable) {
        textView!!.text = exception.message
    }

    /**
     * TODO
     * @param context
     * @return
     */
    fun newIntent(context: Context): Intent {
        return Intent(context, RxJava2Example5Activity::class.java)
    }

    companion object {
        private val URL_LIST = listOf(
            "https://mp3bob.ru/download/muz/Numb_[mp3pulse.ru].mp3",
            "https://www.mp3pulse.ru/download/muz/Nickelback_-_Home_[pulse-mp3.ru].mp3",
            "https://www.mp3pulse.ru/download/muz/Nickelback_-_After_The_Rain_[pulse-mp3.ru].mp3"
        )
        private const val FILE_NAME = "downloadedFile"
        private const val TAG = "RxJava2Example3Activity"
    }
}

class SomeObserver : Observer<String> {

    override fun onNext(p0: String) {
        println(p0)
    }

    override fun onError(p0: Throwable) {
        println(p0.message)
    }

    override fun onComplete() {
        println("Done")
    }

    override fun onSubscribe(p0: Disposable) {
        println(p0.toString())
    }
}