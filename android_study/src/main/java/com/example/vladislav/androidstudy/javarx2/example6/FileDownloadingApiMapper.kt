package com.example.vladislav.androidstudy.javarx2.example6

import okhttp3.OkHttpClient
import okhttp3.Request
import okio.buffer
import okio.sink
import retrofit2.Retrofit
import java.io.BufferedOutputStream
import java.io.FileOutputStream

/**
 * File downloading using Retrofit and Kotlin Coroutines
 */
class FileDownloadingApiMapper {

    /** Download file on [fileUrl], [filePath], having a progress fetch using [progressListener] */
    suspend fun downloadFileWithProgress(
        fileUrl: String,
        filePath: String,
        progressListener: DownloadProgressListener
    ) {
        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor { chain ->
                val originalResponse = chain.proceed(chain.request())
                originalResponse
                    .newBuilder()
                    .body(ProgressResponseBody(originalResponse.body, progressListener))
                    .build()
            }
            .build()

        val retrofit = Retrofit.Builder()
            // FIXME This is a base url for files to be downloaded from.
            //  What if one wishes to download from different servers ? It should be dynamic.
            .baseUrl("https://mp3bob.ru/download/")
            .client(okHttpClient)
            .build()

        val downloadService = retrofit.create(DownloadService::class.java)

        val request = Request.Builder()
            .url(fileUrl)
            .header("Range", "bytes=${progressListener.totalBytesRead}-")
            .build()

        downloadService.downloadFile(request.url.toString(), request.header("Range")).apply {
            this.let { responseBody ->
                val outputStream = FileOutputStream(filePath)
                val bufferedOutputStream = BufferedOutputStream(outputStream)
                val bufferedSink = bufferedOutputStream.sink().buffer()

                val progressSource = ProgressResponseBody(responseBody, progressListener)
                bufferedSink.writeAll(progressSource.source())

                bufferedSink.flush()
                bufferedSink.close()
            }
        }
    }
}