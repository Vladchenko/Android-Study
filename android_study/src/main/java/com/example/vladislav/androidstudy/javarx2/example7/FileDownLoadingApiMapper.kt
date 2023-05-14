package com.example.vladislav.androidstudy.javarx2.example7

import com.example.vladislav.androidstudy.javarx2.example5.FileProgressModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.FileOutputStream

/**
 * TODO
 */
class FileDownLoadingApiMapper(private val listener: ProgressListener) {

    private var downloadService: DownloadService? = null

    init {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalResponse = chain.proceed(chain.request())
                originalResponse.newBuilder()
                    .body(ProgressResponseBody(originalResponse.body!!, listener))
                    .build()
            }
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        downloadService = retrofit.create(DownloadService::class.java)
    }

    fun downloadFileWithProgress(
        fileUrl: String,
        fileName: String
    ): Observable<FileProgressModel> {
        return Observable.create { emitter ->
            run {
                downloadService!!.downloadFile(fileUrl)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { responseBody ->
                        val totalFileSize = responseBody.contentLength()
                        val bufferedInputStream = responseBody.byteStream().buffered()
                        val outputStream = FileOutputStream(fileName)

                        var downloadedFileSize = 0L
                        val data = ByteArray(DEFAULT_BUFFER_SIZE)
                        val bytesRead = bufferedInputStream.read(data)

                        while (bytesRead != -1) {
                            outputStream.write(data, 0, bytesRead)
                            downloadedFileSize += bytesRead

                            val progress = (downloadedFileSize * 100 / totalFileSize).toInt()
                            // post the download progress using RxJava2
                            emitter.onNext(FileProgressModel(fileName, progress.toLong()))
                        }

                        outputStream.flush()
                        outputStream.close()
                        bufferedInputStream.close()
                        FileProgressModel(fileName, 100) // return progress 100 when download is complete
                    }
                    .onErrorReturn { throwable ->
                        throwable.printStackTrace()
                        FileProgressModel(fileName, 0) // return -1 if any error occurs during download
                    }
            }
        }
    }

    companion object {
        private const val BASE_URL = "https://mp3bob.ru/download/"
        private const val DEFAULT_BUFFER_SIZE = 4096
    }
}