package com.example.vladislav.androidstudy.javarx2.example7

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * Retrofit downloading service.
 * Uses JavaRx for async operating.
 */
interface DownloadService {

    /** Downloads file by [fileUrl], returning [ResponseBody] wrapped in Observable. */
    @GET
    @Streaming
    fun downloadFile(@Url fileUrl: String): Observable<ResponseBody>
}