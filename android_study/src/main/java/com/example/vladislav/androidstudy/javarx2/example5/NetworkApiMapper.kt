package com.example.vladislav.androidstudy.javarx2.example5

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
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
    fun downloadData(url: String, filePath: String): Observable<String> =
        Observable.create { emitter ->
            run {
                try {
                    val request = Request.Builder()
                        .url(url)
                        .build()
                    OkHttpClient().newCall(request).execute().let {
                        println(it.headers())
                        val input = it.body()?.byteStream()
                        val length: Long = it.body()?.contentLength()!!
                        /**
                        contentLength() will retrieve value only when header: Content-Length in the downloading file
                        is present.
                         */

                        val output = FileOutputStream(filePath)
                        val data = ByteArray(1024)

                        emitter.onNext("0%")
                        var total: Long = 0
                        var count: Int = 0
                        var percent: Long = 0
                        while (percent < 100) { //FIXME Lame code
                            count = input?.read(data)!!
                            total += count.toLong()
                            percent = total * 100 / length
                            emitter.onNext("$percent%")
                            output.write(data, 0, count)
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
                emitter.onComplete()
            }
        }

    companion object {
        private const val TAG = "NetworkApiMapper"
    }
}