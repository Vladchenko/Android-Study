package com.example.vladislav.androidstudy.kotlin.filesdownloading.network

import com.example.vladislav.androidstudy.kotlin.filesdownloading.calladapter.FlowCallAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Downloads a file
 */
class FileDownloadApiMapper {

    private var downloadService: DownloadService? = null

    init {
        // be aware, Retrofit's @Streaming doesn't work with [HttpLoggingInterceptor.Level.BODY]
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)

        downloadService = Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .readTimeout(30L, TimeUnit.SECONDS)
                    .writeTimeout(30L, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .build()
            )
            .addCallAdapterFactory(FlowCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build().create(DownloadService::class.java)
    }

    /**
     * Download and save file
     *
     * @param fileUrl to locate a source of a file to download from
     * @param filePath to save file to
     */
    suspend fun saveFile(
        fileUrl: String,
        filePath: String
    ): Flow<DownloadState> {
        return downloadService?.downloadLargeFile(fileUrl)?.saveFile(filePath)!!
    }

    private fun ResponseBody.saveFile(filePath: String): Flow<DownloadState> {
        return flow {
            emit(DownloadState.Downloading(filePath, 0))
            val destinationFile = File(filePath)
            var progress = 0
            var progressToEmit = 0
            try {
                byteStream().use { inputStream ->
                    destinationFile.outputStream().use { outputStream ->
                        val totalBytes = contentLength()
                        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                        var progressBytes = 0L
                        var bytes = inputStream.read(buffer)
                        while (bytes >= 0) {
                            outputStream.write(buffer, 0, bytes)
                            progressBytes += bytes
                            bytes = inputStream.read(buffer)
                            progress = (progressBytes * 100 / totalBytes).toInt()
                            if (progress != progressToEmit) {
                                progressToEmit = progress
                                emit(DownloadState.Downloading(filePath, progressToEmit))
                            }
                        }
                    }
                }
                emit(DownloadState.Finished(filePath))
            } catch (e: Exception) {
                emit(DownloadState.Failed(e))
            }
        }.flowOn(Dispatchers.IO).distinctUntilChanged()
    }

    companion object {
        private const val BASE_URL = "https://mp3bob.ru/download/"
        private const val DEFAULT_BUFFER_SIZE = 4096
    }
}