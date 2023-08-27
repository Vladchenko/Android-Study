package com.example.vladislav.androidstudy.kotlin.demo

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random
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
        // Thread.sleep(2000L) // block main thread for 2 seconds to keep JVM alive (for coroutine could complete)
        // Previous row required only for non-android apps.
    }

    fun simpleCoroutineDemo2() = runBlocking<Unit> { // start main coroutine
        GlobalScope.launch { // launch a new coroutine in background and continue
            delay(1000L)
            Log.d(TAG, "World!")
        }
        Log.d(TAG, "Hello,") // main coroutine continues here immediately
        // delay(2000L)      // delaying for 2 seconds to keep JVM alive
        // Previous row required only for non-android apps.
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

    fun simpleCoroutineDemo8() {
        GlobalScope.launch(Dispatchers.IO) {
            repeat(100) {
                launch {
                    delay(Random.nextLong(3000))
                    Log.d(TAG, "Coroutines #$it done its job")
                }
            }
            Log.d(TAG, "simpleCoroutineDemo8 finished its job")
        }
    }

    fun simpleCoroutineDemo9() {
        GlobalScope.launch(Dispatchers.IO) {
            val coroutines: List<Deferred<String>> =  List(100) {
                async {
                    doWork(it)
                }
            }
            coroutines.forEach { println(it.await()) }
            Log.d(TAG, "simpleCoroutineDemo9 finished its job")
        }
    }

    private suspend fun doWork(it: Int): String {
        delay(Random.nextLong(3000))
        return "Coroutines #$it done its job"
    }

    private suspend fun simulateNetworkCall() {
        delay(2000L)
        Log.d(TAG, "Data downloaded!")
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
        launch {
            // main runBlocking      : I'm working in thread main
            Log.d(
                TAG,
                "main runBlocking      : I'm working in thread ${Thread.currentThread().name}"
            )
        }
        launch(Dispatchers.Unconfined) {
            // Unconfined            : I'm working in thread main
            Log.d(
                TAG,
                "Unconfined            : I'm working in thread ${Thread.currentThread().name}"
            )
        }
        launch(Dispatchers.Default) {
            // Default               : I'm working in thread DefaultDispatcher-worker-1
            Log.d(
                TAG,
                "Default               : I'm working in thread ${Thread.currentThread().name}"
            )
        }
        launch(newSingleThreadContext("MyOwnThread")) {
            // newSingleThreadContext: I'm working in thread MyOwnThread
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

    /** One could use yield() for a coroutine to be cancellable */
    fun cancellableCoroutineDemo3() {
        CoroutineScope(Dispatchers.Main).launch {
            val job = launch {
                var time = System.currentTimeMillis()
                var i = 0
                while (i < 1000) {
                    yield()
                    if (System.currentTimeMillis() >= time) {
                        println("Loop number ${++i}")
                        time += 500
                    }
                }
            }
            // wait some time
            delay(1300)
            println("Stopping the coroutine....")
            job.cancel()
            job.join()
            // or call job.cancelAndJoin()
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

    /** 2 network calls that run at the same time */
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

    private fun flowDemo1(): Flow<Int> = flow {
        for (i in 1..10) {
            delay(100)
            emit(i)
        }
    }

    fun main() = runBlocking {
        coroutineScope {
            // Rows 1, 2 will be executed at once
            launch { doWork() }     // 1
            println("Hello Coroutines")     // 2
        }
    }

    private suspend fun doWork() {
        for (i in 0..5) {
            println(i)
            delay(400L)
        }
    }

    fun flowDemo() {
        GlobalScope.launch {
            (1..3).asFlow().collect { value -> println(value) }
        }
    }

    companion object {
        private const val TAG = "CoroutinesBasics"
    }
}
