package com.example.vladislav.androidstudy.javarx2.example5

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.io.FileOutputStream

/**
 * Работа с сетью
 *
 * @author Yanchenko Vladislav
 */
class NetworkApiMapper {

    /**
     * Загружаем данные по [url] в [Response]
     */
    @Throws(IOException::class)
    fun downloadData(url: String, filePath: String): Observable<FileModel> =
        Observable.create { emitter ->
            var percentage: Long = 0
            run {
                try {
                    val request = Request.Builder()
                        .url(url)
                        .build()
                    OkHttpClient().newCall(request).execute().let {
                        println(it.headers)
                        val input = it.body?.byteStream()
                        val length: Long = it.body?.contentLength()!!

                        /**
                        contentLength() will retrieve value only when header: Content-Length in the downloading file
                        is present.
                         */

                        val output = FileOutputStream(filePath)
                        val data = ByteArray(1024)

                        // Emit FileModel for beginning of download
                        emitFileModelForPercent(emitter, filePath, 0)

                        var count: Int
                        var total: Long = 0
                        var percent: Long = 0
                        while (percent < 100) { //FIXME Lame code
                            count = input?.read(data)!!
                            total += count.toLong()
                            percent = total * 100 / length
                            output.write(data, 0, count)
                            // Since downloading is done with small data blocks, the downloaded
                            // percent is not changed every such reading. For emitter not to feed
                            // same percent value many times, next condition is implemented
                            if (percentage != percent) {
                                percentage = percent
                                emitFileModelForPercent(emitter, filePath, percent)
                            }
                        }
                        output.flush()
                        output.close()
                        input?.close()
                    }
                } catch (ex: Exception) {
                    println(ex.message)
                    ex.stackTrace.map { Log.e(TAG, it.toString()) }
                    throw ex
                }
                // This emits an event of current downloading being complete
                emitter.onComplete()
            }
        }

    private fun emitFileModelForPercent(
        emitter: ObservableEmitter<FileModel>,
        filePath: String,
        percent: Long
    ) {
        emitter.onNext(
            FileModel(
                extractFileNameFromPath(filePath),
                percent
            )
        )
    }

    companion object {
        private const val TAG = "NetworkApiMapper"
    }
}