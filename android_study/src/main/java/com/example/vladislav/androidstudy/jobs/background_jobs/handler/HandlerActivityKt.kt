package com.example.vladislav.androidstudy.jobs.background_jobs.handler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.example.vladislav.androidstudy.R

/**
 * Demo for Handler, Looper, Thread
 * https://www.youtube.com/watch?v=QfQE1ayCzf8&list=PLrnPJCHvNZuD52mtV8NvazNYIyIVPVZRa
 */
class HandlerActivityKt : AppCompatActivity() {

    private var stopFlag = false
    private lateinit var textView: TextView
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var switch: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler_kt)
        initViews()
        startButton.setOnClickListener {
            stopFlag = false
//            demoMainThreadExecution()
//            demoSeparateThreadExecution()
            demoSeparateThreadMainThreadHandler()
        }
        stopButton.setOnClickListener {
            stopFlag = true
        }
    }

    private fun initViews() {
        textView = findViewById(R.id.some_text_view)
        startButton = findViewById(R.id.start_button)
        stopButton = findViewById(R.id.stop_button)
        switch = findViewById(R.id.switch1)
    }

    private fun demoMainThreadExecution() {
        for (i in 1..10) {
            try {
                Thread.sleep(500)
                // No problem in typing to log, but UI is frozen, since this is a main thread
                Log.i(TAG, i.toString())
            } catch (e: InterruptedException) {
                e.printStackTrace();
            }
        }
        for (i in 1..10) {
            try {
                Thread.sleep(500)
                // This textview won't be updated every 500ms, but only after this loop is over and
                // will have text "5". UI will freeze.
                textView.text = i.toString()
            } catch (e: InterruptedException) {
                e.printStackTrace();
            }
        }
    }

    private fun demoSeparateThreadExecution() {
        Thread {
            for (i in 1..10) {
                try {
                    Thread.sleep(500)
                    // No problem in typing to log, UI is NOT frozen
                    Log.i(TAG, i.toString())
                } catch (e: InterruptedException) {
                    e.printStackTrace();
                }
            }
            for (i in 5..10) {
                try {
                    Thread.sleep(500)
                    // CalledFromWrongThreadException: Only the original thread that created
                    // a view hierarchy can touch its views.
                    textView.text = i.toString()
                } catch (e: InterruptedException) {
                    e.printStackTrace();
                }
            }
        }.start()
    }

    private fun demoSeparateThreadMainThreadHandler() {
//        val handler = Handler() // Since we create handler in activity, it gets to attach to main
        // thread. But Handler() is deprecated and one has to created it differently
        val handler = Handler(Looper.getMainLooper())
        // Handler(Looper.myLooper()!!) could also be of use in this case, since takes current thread
        Thread {
            // RuntimeException: Can't create handler inside thread Thread[Thread-2,5,main] that
            // has not called Looper.prepare()
//            val handler = Handler()
            // NullPointerException at myLooper()
//            val handler = Handler(Looper.myLooper()!!)
            for (i in 1..10) {
                if (stopFlag) return@Thread
                try {
                    Thread.sleep(500)
                    // 1 way of referring to UI thread
                    // This code is run on a main thread, since this handler is attached to a main
                    // thread
                    handler.post {
                        textView.text = i.toString()
                    }
                    // 2nd way of referring to UI thread
                    // Instead of handler.post, one may use
//                    textView.post {
//                        textView.text = i.toString()
//                    }
                    // 3rd way of referring to UI thread
//                    runOnUiThread {
//                        textView.text = i.toString()
//                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace();
                }
            }
        }.start()
    }

    companion object {
        private const val TAG = "HandlerActivityKt"
    }
}