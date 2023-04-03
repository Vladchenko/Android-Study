package com.example.vladislav.androidstudy.jobs.currency.workerthread

import com.example.vladislav.androidstudy.jobs.currency.CurrencyUtils
import com.example.vladislav.androidstudy.jobs.currency.beans.CurrenciesContainer
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 * Currencies rates downloading api mapper.
 */
class CurrencyDownloadingApiMapper {
    
    private var currenciesContainer: CurrenciesContainer? = null

    /**
     * Download currencies rates
     * TODO Downloading mechanism should be replaced with some modern one, in a future.
     */
    fun downloadCurrenciesData(link: String): CurrenciesContainer? {
        var url: URL? = null
        try {
            url = URL(link)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        var `in`: InputStream? = null
        val urlConnection: HttpURLConnection
        try {
            urlConnection = url?.openConnection() as HttpURLConnection
            // Why the absence of a following row makes a downloading not work ?
            urlConnection.requestMethod
            `in` = BufferedInputStream(urlConnection.inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        try {
            currenciesContainer = CurrencyUtils.parse(`in`)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return currenciesContainer
    }
}