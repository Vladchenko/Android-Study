package com.example.vladislav.androidstudy.kotlin.filesdownloading.task2.data

/**
 * File downloader.
 */
interface FileDownloader {

    /**
     * Download a file by its [url].
     * @return an id for its download.
     */
    fun downloadFile(url: String): Long
}