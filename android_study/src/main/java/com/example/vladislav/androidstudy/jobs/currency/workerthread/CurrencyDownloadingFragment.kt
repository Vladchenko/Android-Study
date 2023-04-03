package com.example.vladislav.androidstudy.jobs.currency.workerthread

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vladislav.androidstudy.R
import com.example.vladislav.androidstudy.jobs.currency.CurrencyRecyclerAdapter
import com.example.vladislav.androidstudy.jobs.currency.RectangularItemDecoration
import com.example.vladislav.androidstudy.jobs.currency.beans.CurrenciesContainer

/**
 * Fragment to display a downloaded currencies rates.
 */
class CurrencyDownloadingFragment : Fragment(R.layout.fragment_currency), Handler.Callback {

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var mainThreadHandler: Handler
    private lateinit var centerMessageTextView: TextView
    private lateinit var recyclerAdapter: CurrencyRecyclerAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var currencyDownloadingThread: CurrencyDownloadingThread

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(activity)
        recyclerView = view.findViewById<View>(R.id.currency_recycler_view) as RecyclerView
        recyclerView.addItemDecoration(RectangularItemDecoration(activity))
        progressBar = view.findViewById<View>(R.id.currency_progress_bar) as ProgressBar
        centerMessageTextView = view.findViewById<View>(R.id.center_message_text_view) as TextView
        mainThreadHandler = Handler(Looper.getMainLooper(), this)
        currencyDownloadingThread = CurrencyDownloadingThread(
            CurrencyDownloadingApiMapper(),
            mainThreadHandler
        )
    }

    override fun onStart() {
        super.onStart()
        currencyDownloadingThread.start()
    }

    override fun onResume() {
        super.onResume()
        val message = Message.obtain()
        message.obj = "http://www.cbr.ru/scripts/XML_daily.asp"
        currencyDownloadingThread.sendMessageToBackgroundThread(message)
    }

    override fun handleMessage(msg: Message): Boolean {
        val currenciesContainer = msg.obj as CurrenciesContainer
        centerMessageTextView.visibility = View.INVISIBLE
        recyclerAdapter = CurrencyRecyclerAdapter(currenciesContainer.currenciesList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerAdapter
        progressBar.visibility = View.INVISIBLE
        return false
    }
}