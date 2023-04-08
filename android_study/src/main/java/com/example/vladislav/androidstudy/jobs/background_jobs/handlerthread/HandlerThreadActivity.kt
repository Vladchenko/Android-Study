package com.example.vladislav.androidstudy.jobs.background_jobs.handlerthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Button
import com.example.vladislav.androidstudy.R

/**
 * Demo for Handler, Looper, Thread
 * https://www.youtube.com/watch?v=TN-CGfzvBhc begin with 8:26
 */
class HandlerThreadActivity : AppCompatActivity() {

    private val handlerThread = ExampleHandlerThread()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler_thread)

        handlerThread.start()

        (findViewById<Button>(R.id.do_work_button)).setOnClickListener {
            doWork()
        }
        (findViewById<Button>(R.id.remove_message_button)).setOnClickListener {
            removeMessage()
        }
    }

    fun doWork() {
        var message = Message.obtain()
        message.what = 1
        handlerThread.getHandler()?.sendMessage(message)
//        handlerThread.getHandler().sendEmptyMessage(1)    // This one does the same as a previous row

        // Another way of sending message
        message = Message.obtain(handlerThread.getHandler())
        message.what = 2
        message.sendToTarget()

        // And another way
        message = Message.obtain()
        message.target = handlerThread.getHandler()
        message.what = 3
        message.sendToTarget()
    }

    fun removeMessage() {
        // Removing messages with what = 1
//        handlerThread.getHandler().removeMessages(1)
        // Removing all messages
        handlerThread.getHandler()?.removeCallbacksAndMessages(null)
    }

    override fun onDestroy() {
        super.onDestroy()
        handlerThread.quit()
    }

    companion object {
        private const val TAG = "HandlerThreadActivity"
    }
}