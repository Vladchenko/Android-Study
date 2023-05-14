package com.example.vladislav.androidstudy.javarx2.example6

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * Retrofit downloading service.
 * Uses Kotlin Coroutines for async operating.
 */
interface DownloadService {

    /** Downloads file by [fileUrl], returning [ResponseBody] wrapped in Observable. */
    @GET
    @Streaming
    suspend fun downloadFile(
        @Url fileUrl: String,
        @Header("Range") range: String?
    ): ResponseBody
}