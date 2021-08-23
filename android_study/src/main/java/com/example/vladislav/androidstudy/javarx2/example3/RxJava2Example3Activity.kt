package com.example.vladislav.androidstudy.javarx2.example3

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.vladislav.androidstudy.R
import com.example.vladislav.androidstudy.javarx2.example4.NetworkApiMapper
import com.example.vladislav.androidstudy.javarx2.example4.ResponseSaver
import com.example.vladislav.androidstudy.kotlin.utils.createFilesDirIfAbsent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Response
import java.io.File

/**
 * This example performs a file downloading, using JavaRx.
 * Once downloading complete, progress bar stops to move and textview says where file has been downloaded.
 */
class RxJava2Example3Activity : AppCompatActivity() {

    private var button: Button? = null
    private var textView: TextView? = null
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_java2_example3)
        initViews()
        button!!.setOnClickListener {
            downloadUrlToFile(
                URL,
                FILE_NAME
            )
        }
    }

    private fun initViews() {
        textView = findViewById(R.id.rxjava2_example3_text_view)
        button = findViewById(R.id.rxjava2_example3_button)
        progressBar = findViewById(R.id.rxjava2_example3_progress_bar)
    }

    /**
     * Downloading is performed on a worker thread and fetches the result on a UI thread.
     * //TODO This code should be put to Presenter (MVP) or ViewModel (MVVM)
     */
    @SuppressLint("CheckResult")
    private fun downloadUrlToFile(url: String, fileName: String) {
        val filePath = createFilesDirIfAbsent(this).path + File.pathSeparator + fileName
        // !!! https://stackoverflow.com/questions/27687907/android-os-networkonmainthreadexception-using-rxjava-on-android
        Single.fromCallable {
            NetworkApiMapper().downloadData(url)
        }
            .map { response: Response? ->
                ResponseSaver().saveDataToFile(
                    filePath,
                    response!!
                )
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                progressBar!!.visibility = View.VISIBLE
                button!!.visibility = View.INVISIBLE
                textView!!.text = getString(R.string.downloading_by_url, url)
            }
            .doOnSuccess { unused: File? ->
                progressBar!!.visibility = View.INVISIBLE
                button!!.visibility = View.VISIBLE
            }
            .doOnError { error: Throwable ->
                progressBar!!.visibility = View.INVISIBLE
                textView!!.text = getString(R.string.download_error_message, error.message)
            }
            .subscribe(
                { file: File? -> showSuccessfulStatus(file!!) },
                { exception -> showFailedStatus(exception) }
            )
    }

    private fun showSuccessfulStatus(file: File) {
        textView!!.text = getString(R.string.file_downloaded_to_message, file.absolutePath)
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
        return Intent(context, RxJava2Example3Activity::class.java)
    }

    companion object {
        private const val URL = "https://mezzoforte.ru/s/linkin_park/numb.mp3"
        private const val FILE_NAME = "downloadedFile"
        private const val TAG = "RxJava2Example3Activity"
    }
}