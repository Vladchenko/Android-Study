package com.example.vladislav.androidstudy.javarx2.example7

/**
 * Interface to update a progress
 */
interface ProgressListener {
    /**
     * Updates a progress of file being downloaded, having [bytesRead], [contentLength]
     * and finish flag as [done] provided.
     */
    fun onProgressUpdate(bytesRead: Long, contentLength: Long, done: Boolean)
    /**
     * Provides a [throwable] in case of some downloading problem.
     */
    fun onDownloadFailed(throwable: Throwable)
}