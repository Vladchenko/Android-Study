package com.example.vladislav.androidstudy.jobs.background_jobs.handlerthread

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import android.os.SystemClock
import android.util.Log

/**
 * HandlerThread example
 */
class ExampleHandlerThread : HandlerThread("HandlerThreadExample") {

    private var handler : Handler? = null

    override fun onLooperPrepared() {
//        handler = Handler(Looper.myLooper()!!)
        handler = object : Handler(Looper.myLooper()!!) {  // Let's crete an anonymous class this time )
            override fun handleMessage(msg: Message) {
                when(msg.what) {
                    ONE -> {
                        for (i in 0..10) {
                            Log.d(TAG, i.toString())
                            SystemClock.sleep(500)
                        }
                    }
                    TWO -> {
                        for (i in 110..120) {
                            Log.d(TAG, i.toString())
                            SystemClock.sleep(200)
                        }
                    }
                    THREE -> {
                        for (i in 1110..1120) {
                            Log.d(TAG, i.toString())
                            SystemClock.sleep(500)
                        }
                    }
                    else -> {

                    }
                }
            }
        }
    }

    fun getHandler() = handler

    companion object {
        const val ONE = 1
        const val TWO = 2
        const val THREE = 3
        private const val TAG = "HandlerThreadExample"
    }
}