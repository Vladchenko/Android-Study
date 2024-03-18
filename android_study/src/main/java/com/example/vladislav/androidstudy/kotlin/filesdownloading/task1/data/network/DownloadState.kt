package com.example.vladislav.androidstudy.kotlin.filesdownloading.task1.data.network

/** Defines a state of file downloading */
sealed class DownloadState {
    data class Downloading(val fileName: String, val progress: Int) : DownloadState()
    data class Finished(val fileName: String) : DownloadState()
    data class Failed(val error: Throwable? = null) : DownloadState()
}