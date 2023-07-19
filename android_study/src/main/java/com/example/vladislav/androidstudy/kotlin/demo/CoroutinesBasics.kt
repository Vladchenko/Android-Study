package com.example.vladislav.androidstudy.kotlin.demo

import android.util.Log
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * https://kotlinlang.org/docs/reference/coroutines/basics.html
 *
 * Course stopped at -
 * https://www.youtube.com/watch?v=uiPYcSSjNTI&list=PLQkwcJG4YTCQcFEPuYGuv54nYai_lwil_&index=9
 *
 * @author Yanchenko Vladislav on 25.08.2020.
 */
class CoroutinesBasics {

//    CoroutineScope().launch(Dispatchers.Main) // Doesn't block main thread
//    runBlocking()                             // Blocks main thread

//    runBlocking() {
//        delay(someNumber)             // Will block UI updates in "someNumber" msecs.
//    }

//    runBlocking()     is in fact used if U need to call some suspend functions on a main thread

    fun simpleCoroutineDemo() {
        // GlobalScope coroutine will live while the app lives
        GlobalScope.launch { // launch a new coroutine in background and continue work in main thread
            Log.d(TAG, "Current thread in coroutines section = ${Thread.currentThread().name}")
            delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
            Log.d(TAG, "World!") // print after delay
        }
        Log.d(TAG, "Current thread = ${Thread.currentThread().name}")
        Log.d(TAG, "Hello,") // main thread continues while coroutine is delayed
        Thread.sleep(2000L) // block main thread for 2 seconds to keep JVM alive (for coroutine could complete)
    }

    fun simpleCoroutineDemo2() = runBlocking<Unit> { // start main coroutine
        GlobalScope.launch { // launch a new coroutine in background and continue
            delay(1000L)
            Log.d(TAG, "World!")
        }
        Log.d(TAG, "Hello,") // main coroutine continues here immediately
        delay(2000L)      // delaying for 2 seconds to keep JVM alive
    }

    fun simpleCoroutineDemo3() = runBlocking { // <Unit> can be omitted
        val job = GlobalScope.launch { // launch a new coroutine and keep a reference to its Job
            delay(1000L)
            Log.d(TAG, "World!")
        }
        Log.d(TAG, "Hello,")
        job.join() // wait until child coroutine completes
    }

    fun simpleCoroutineDemo4() = runBlocking { // <Unit> can be omitted
        GlobalScope.launch(Dispatchers.Main) {// Launch coroutine on main thread
            delay(1000L)
            Log.d(TAG, "First delay!")
        }
    }

    fun simpleCoroutineDemo5() = runBlocking { // <Unit> can be omitted
        GlobalScope.launch(newSingleThreadContext("My thread")) {// Launch coroutine on newly created thread
            delay(1000L)
            Log.d(TAG, "First delay!")
        }
    }

    fun simpleCoroutineDemo6() = runBlocking { // <Unit> can be omitted
        GlobalScope.launch(Dispatchers.IO) {// Launch coroutine on newly created thread
            simulateNetworkCall()
            withContext(Dispatchers.Main) {
                // Update some UI views here
            }
        }
    }

    fun simpleCoroutineDemo7() { // <Unit> can be omitted
        // These two coroutines will run simultaneously
        GlobalScope.launch(Dispatchers.IO) {// Launch coroutine on newly created thread
            Log.d(TAG, "Network call 1")
            simulateNetworkCall()
            Log.d(TAG, "Network call 1 done")
            withContext(Dispatchers.Main) {
                // Update some UI views here
            }
        }
        GlobalScope.launch(Dispatchers.IO) {// Launch coroutine on newly created thread
            Log.d(TAG, "Network call 2")
            simulateNetworkCall()
            Log.d(TAG, "Network call 2 done")
            withContext(Dispatchers.Main) {
                // Update some UI views here
            }
        }
    }

    suspend fun simulateNetworkCall() {
        delay(2000L)
        Log.d(TAG, "Data downloaded!")
    }

    suspend fun secondDelay() {
        delay(1000L)
        Log.d(TAG, "Second delay!")
    }

   fun demoDispatchersAndThreads() {
       CoroutineScope(Dispatchers.Main).apply {
           launch {
               // main runBlocking       : I'm working in a thread main
               println("main runBlocking       : I'm working in a thread ${Thread.currentThread().name}")
           }
           launch(Dispatchers.Unconfined) {
               // Unconfined             : I'm working in a thread main
               println("Unconfined             : I'm working in a thread ${Thread.currentThread().name}")
           }
           launch(Dispatchers.Default) {
               // Default                : I'm working in a thread DefaultDispatcher-worker-2
               println("Default                : I'm working in a thread ${Thread.currentThread().name}")
           }
           launch(newSingleThreadContext("MyOwnThread")) {
               // MyOwnThread            : I'm working in a thread MyOwnThread
               println("MyOwnThread            : I'm working in a thread ${Thread.currentThread().name}")
           }
       }
   }

    fun demoDispatchersAndThreads2() = runBlocking<Unit> {
        launch { // context of the parent, main runBlocking coroutine
            Log.d(
                TAG,
                "main runBlocking      : I'm working in thread ${Thread.currentThread().name}"
            )
        }
        launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
            Log.d(
                TAG,
                "Unconfined            : I'm working in thread ${Thread.currentThread().name}"
            )
        }
        launch(Dispatchers.Default) { // will get dispatched to DefaultDispatcher
            Log.d(
                TAG,
                "Default               : I'm working in thread ${Thread.currentThread().name}"
            )
        }
        launch(newSingleThreadContext("MyOwnThread")) { // will get its own new thread
            Log.d(
                TAG,
                "newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}"
            )
        }
    }

    fun cancelDemo() {
        val job = CoroutineScope(Dispatchers.Default).launch {
            repeat(5) {
                Log.d(TAG, "cancelDemo begun its work")
                delay(1000L)
                Log.d(TAG, "cancelDemo finished its work")
            }
        }
        runBlocking {
            delay(2000L)
            job.cancel()
            Log.d(TAG, "cancelDemo cancelled")
        }
    }

    fun cancelNotWorkingDemo() {
        val job = CoroutineScope(Dispatchers.Default).launch {
            for (i in 30..40) {
                Log.d(TAG, "Result for i=$i = ${fibbonacci(i)}")
            }
            Log.d(TAG, "cancelNotWorkingDemo finished its work")
        }
        runBlocking {
            delay(100L)
            job.cancel()
            Log.d(TAG, "cancelDemo cancelled")
        }
    }

    /**
     * One can monitor if a coroutines was cancelled using an isActive flag
     */
    fun cancelWorkingDemo() {
        val job = CoroutineScope(Dispatchers.Default).launch {
            for (i in 30..40) {
                if (isActive) {
                    Log.d(TAG, "Result for i=$i = ${fibbonacci(i)}")
                }
            }
            Log.d(TAG, "cancelNotWorkingDemo finished its work")
        }
        runBlocking {
            delay(100L)
            job.cancel()
            Log.d(TAG, "cancelDemo cancelled")
        }
    }

    /**
     * One can set a timeout for a coroutine to run.
     */
    fun cancelWorkingDemo2() {
        val job = CoroutineScope(Dispatchers.Default).launch {
            withTimeout(3000L) {
                for (i in 30..40) {
                    Log.d(TAG, "Result for i=$i = ${fibbonacci(i)}")
                }
            }
            Log.d(TAG, "cancelNotWorkingDemo finished its work")
        }
        runBlocking {
            delay(100L)
            job.cancel()
            Log.d(TAG, "cancelDemo cancelled")
        }
    }

    private fun fibbonacci(n: Int): Int {
        return when (n) {
            0 -> 0
            1 -> 1
            else -> fibbonacci(n - 1) + fibbonacci(n - 2)
        }
    }

    suspend fun simulateNetworkCall2() {
        delay(3000L)
        Log.d(TAG, "Data downloaded for simulateNetworkCall2")
    }

    fun twoNetworkCalls() {
        CoroutineScope(Dispatchers.IO).launch {
            simulateNetworkCall()
        }
        CoroutineScope(Dispatchers.IO).launch {
            simulateNetworkCall2()
        }
    }

    fun twoNetworkCalls2() {
        CoroutineScope(Dispatchers.IO).launch {
            val time = measureTimeMillis {
                var response1 = ""
                /*
                 * Calling launch for async operation is a bad practice. Instead one should call async.
                 * This is a deferred in time job. Check following function for it.
                 */
                val job = launch {
                    response1 = networkCall1()
                }
                var response2 = ""
                val job2 = launch {
                    response2 = networkCall2()
                }
                job.join()
                job2.join()
                Log.d(TAG, response1)
                Log.d(TAG, response2)
            }
            Log.d(TAG, "time spent = $time")
        }
    }

    fun twoNetworkCalls3() {
        CoroutineScope(Dispatchers.IO).launch {
            val time = measureTimeMillis {
                val response1 = async { networkCall1() }
                val response2 = async { networkCall2() }
                Log.d(TAG, response1.await())   // await blocks current coroutines until answer is available
                Log.d(TAG, response2.await())
            }
            Log.d(TAG, "time spent = $time")
        }
    }

//    fun lifeCycleDemo() {
//        lifecycleScope.launch
//    }

    private suspend fun networkCall1(): String {
        delay(2000L)
        return "networkCall1 response"
    }

    private suspend fun networkCall2(): String {
        delay(2000L)
        return "networkCall2 response"
    }

    companion object {
        private const val TAG = "CoroutinesBasics"
    }
}
