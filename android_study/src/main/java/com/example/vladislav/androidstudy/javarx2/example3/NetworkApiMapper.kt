package com.example.vladislav.androidstudy.javarx2.example3

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

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
    fun downloadData(url: String): Response? {
        try {
            val request = Request.Builder()
                .url(url)
                .build()
            OkHttpClient().newCall(request).execute().let {
                return it
            }
        } catch (ex: Exception) {
            println(ex.message)
            ex.stackTrace.map { Log.e(TAG, it.toString()) }
            throw ex
        }
    }

    companion object {
        private const val TAG = "NetworkApiMapper"
    }
}