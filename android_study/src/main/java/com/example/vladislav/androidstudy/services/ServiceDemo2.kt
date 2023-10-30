package com.example.vladislav.androidstudy.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Simple service demo to show its callbacks.
 */
class ServiceDemo2 : Service() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate() {
        super.onCreate()
        Log.i(LOG_TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(LOG_TAG, "onStartCommand")
        someTask()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancelling job for coroutine could not last longer than life span of this Service.
        job.cancel()
        Log.i(LOG_TAG, "onDestroy")
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.i(LOG_TAG, "onBind")
        return null
    }

    // UI freezes
    private fun someTask() {
        Toast.makeText(this@ServiceDemo2, "ServiceDemo2 is started", Toast.LENGTH_SHORT).show()
        Thread.sleep(3000)
        Toast.makeText(this@ServiceDemo2, "ServiceDemo2 is over", Toast.LENGTH_SHORT).show()
    }

    // Just to be aware of how to make UI not freezing
    private fun someTask2() {
        // This scope runs on a main thread, since one should show Toast,
        scope.launch {
            Toast.makeText(this@ServiceDemo2, "ServiceDemo2 is started", Toast.LENGTH_SHORT).show()
            // but delay should go on a separate thread, for UI could not be locked.
            withContext(Dispatchers.IO) {   // Dispatchers should be provided through DI
                delay(3000)
            }
            Toast.makeText(this@ServiceDemo2, "ServiceDemo2 is over", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val LOG_TAG = "ServiceDemo2"
    }
}