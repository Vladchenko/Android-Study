package com.example.vladislav.androidstudy.jobs.background_jobs.looperthread

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log

/**
 * TODO
 */
class ExampleLooperThread : Thread() {

    var handler: Handler? = null
    var looper: Looper? = null

    override fun run() {
//        handler = Handler() // RuntimeException: Can't create handler inside thread
//        // Thread[Thread-2,5,main] that has not called Looper.prepare()
//        // This is because we haven't created a looper inside this thread.
//
//        for (i in 0..10) {
//            Log.d(TAG, "run: $i")
//            // Almost like Thread.sleep(), but catches an exception
//            SystemClock.sleep(1000)
//        }

        // Let's replace a previous code with ...
        Looper.prepare()    // This statement creates a message queue. Without it, Handler
        // won't find a looper and throw an exception.
        looper = Looper.myLooper()
//        handler = Handler(looper!!)
        handler = ExampleMessageHandler()
        Looper.loop()   // Any code after this statement won't run.
        // This code runs an infinite loop

        Log.d(TAG, "end of run")
    }

    companion object {
        private const val TAG = "ExampleLooperThread"
    }
}

//Stopped video at 11:02