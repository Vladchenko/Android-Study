package com.example.vladislav.androidstudy.jobs.background_jobs.looperthread

import android.os.Handler
import android.os.Message
import android.util.Log

/**
 * TODO
 */
class ExampleMessageHandler : Handler() {

    override fun handleMessage(msg: Message) {
        when (msg.what) {
            TASK_A -> {
                Log.d(TAG, "Task A executed")
            }
            TASK_B -> {
                Log.d(TAG, "Task B executed")
            }
        }
    }

    companion object {
        const val TASK_A = 1
        const val TASK_B = 2
        private const val TAG = "LooperThreadActivity"
    }
}