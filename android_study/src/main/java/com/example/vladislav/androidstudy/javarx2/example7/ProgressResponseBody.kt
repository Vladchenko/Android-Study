package com.example.vladislav.androidstudy.javarx2.example7

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*

/**
 * Downloading response with a current progress.
 *
 * @param responseBody  data that is currently being downloaded
 * @param progressListener fetches a data downloading progress
 */
class ProgressResponseBody(
    private val responseBody: ResponseBody,
    private val progressListener: ProgressListener
) : ResponseBody() {

    private var bufferedSource: BufferedSource? = null

    override fun contentType(): MediaType? {
        return responseBody.contentType()
    }

    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    override fun source(): BufferedSource {
        if (bufferedSource == null) {
            bufferedSource = source(responseBody.source()).buffer()
        }
        return bufferedSource!!
    }

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            private var totalBytesRead = 0L

            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                totalBytesRead += if (bytesRead != -1L) bytesRead else 0L
                val contentLength = responseBody.contentLength()
                val done = bytesRead == -1L
                progressListener.onProgressUpdate(totalBytesRead, contentLength, done)
                    return bytesRead
            }
        }
    }
}
