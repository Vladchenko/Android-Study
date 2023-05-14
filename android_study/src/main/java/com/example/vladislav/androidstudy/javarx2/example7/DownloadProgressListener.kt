package com.example.vladislav.androidstudy.javarx2.example7

/**
 * Implementation of [ProgressListener]
 *
 * @param listener callback listener, sending a current downloading progress.
 */
class DownloadProgressListener(private val listener: (Long) -> Unit) : ProgressListener {

    var totalBytesRead = 0L

    override fun onProgressUpdate(bytesRead: Long, contentLength: Long, done: Boolean) {
        if (contentLength != -1L) {
            val progress = ((100 * bytesRead) / contentLength)
            if (progress != totalBytesRead) {
                totalBytesRead = progress
                listener(progress)
            }
        }
    }

    override fun onDownloadFailed(throwable: Throwable) {
        TODO("Not yet implemented")
    }
}