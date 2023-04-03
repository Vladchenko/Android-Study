package com.example.vladislav.androidstudy.jobs.currency.workerthread

import android.os.Handler
import android.os.Looper
import android.os.Looper.myLooper
import android.os.Message
import android.util.Log

/**
 * Worker thread to download a currencies inside it.
 */
class CurrencyDownloadingThread(
    val apiMapper: CurrencyDownloadingApiMapper,
    val mainThreadHandler: Handler?
) : Thread() {

    private lateinit var handler: WorkerThreadHandler

    override fun run() {
        // Do work
        Looper.prepare()
        handler = WorkerThreadHandler(myLooper()!!)
        Looper.loop()
    }

    fun sendMessageToBackgroundThread(message: Message) {
        handler.sendMessage(message)
    }

    inner class WorkerThreadHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(message: Message) {
            super.handleMessage(message)
            Log.d(TAG, "handle message : " + currentThread().name)
            val response = apiMapper.downloadCurrenciesData(message.obj as String)
            // 3. Sending the message back to Main Thread
            val responseMessage: Message = Message.obtain()
            responseMessage.obj = response
            mainThreadHandler?.sendMessage(responseMessage)
        }
    }

    companion object {
        const val TAG = "my_thread"
    }
}