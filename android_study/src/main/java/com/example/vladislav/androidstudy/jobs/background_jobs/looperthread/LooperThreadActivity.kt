package com.example.vladislav.androidstudy.jobs.background_jobs.looperthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import com.example.vladislav.androidstudy.R
import com.example.vladislav.androidstudy.jobs.background_jobs.looperthread.ExampleMessageHandler.Companion.TASK_A
import com.example.vladislav.androidstudy.jobs.background_jobs.looperthread.ExampleMessageHandler.Companion.TASK_B

/**
 * Demo for Handler, Looper, Thread
 * https://www.youtube.com/watch?v=TN-CGfzvBhc
 */
class LooperThreadActivity : AppCompatActivity() {

    private val looperThread = ExampleLooperThread()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_looper_thread)
        (findViewById<Button>(R.id.start_button)).setOnClickListener {
            startThread()
        }
        (findViewById<Button>(R.id.stop_button)).setOnClickListener {
            stopThread()
        }
        (findViewById<Button>(R.id.task_a_button)).setOnClickListener {
            runTaskA()
        }
        (findViewById<Button>(R.id.task_b_button)).setOnClickListener {
            runTaskB()
        }
    }

    private fun startThread() {
        // If thread was not started, start it.
        if (!looperThread.isAlive) {
            looperThread.start()
            Log.d(TAG, "ExampleLooperThread started")
        } else {
            Log.d(TAG, "ExampleLooperThread already started")
        }
    }

    private fun stopThread() {
        Log.d(TAG, "ExampleLooperThread stopped")
//        looperThread.stop()     // Stopping thread is not a good idea, since it takes a lot of
        // resources to create and run it again. Better create looper for thread and stop it instead.
        looperThread.looper?.quit()     // This one doesn't stop a thread. Why ?
    }

    // One can call this method several times, since a thread is looped in waiting for a messages.
    private fun runTaskA() {
        // Since we prepared a looper in looperThread, we can dispatch a task into it.
        // Handler maybe null, if thread is not started. Click a START button.
        if (looperThread.handler == null) {
            Log.d(TAG, "ExampleLooperThread started")
            looperThread.start()
            runSomeTask()
        } else {
            runSomeTask()
        }
    }

    private fun runSomeTask() {
        looperThread.handler?.post {
            // Here we send a task from UI to worker thread
            for (i in 0..10) {
                Log.d(TAG, i.toString())
                // Almost like Thread.sleep(), but catches an exception
                SystemClock.sleep(1000)
            }
        }
    }

    private fun runTaskB() {
        // Handler can run not ony Runnables, but also Messages
        // Now let's use them to run tasks on a separate thread.
        val message = Message.obtain()  // Better than Message(), since .obtain() can be garbage
        // collected
        message.what = TASK_B
        looperThread.handler?.sendMessage(message)
        // Using Runnables, we send a piece of work to run, using Message, we send some data to run
        // work in other place
    }

    companion object {
        private const val TAG = "LooperThreadActivity"
    }
}