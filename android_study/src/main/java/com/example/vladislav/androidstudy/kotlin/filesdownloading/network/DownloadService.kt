package com.example.vladislav.androidstudy.kotlin.filesdownloading.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * Retrofit downloading service.
 */
interface DownloadService {

    /** Downloads file by [fileUrl], returning [ResponseBody] wrapped in Observable. */
    @GET
    @Streaming
    suspend fun downloadLargeFile(@Url fileUrl: String): ResponseBody
}