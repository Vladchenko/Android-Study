package com.example.vladislav.androidstudy.kotlin.demo

import android.util.Log
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.yield
import kotlin.random.Random
import kotlin.system.measureTimeMillis

/**
 * https://kotlinlang.ru/docs/coroutines-basics.html
 * https://kotlinlang.org/docs/reference/coroutines/basics.html
 *
 * https://www.youtube.com/watch?v=ShNhJ3wMpvQ&list=PLQkwcJG4YTCQcFEPuYGuv54nYai_lwil_&index=1
 *
 * @author Yanchenko Vladislav on 25.08.2020.
 */
class CoroutinesBasics {

    fun coroutineDemo() {
        // runBlocking blocks a thread it runs on. It is an entry point from non suspend functions.
        runBlocking {
            // Since we run on main thread, next code will freeze UI.
            while (true) {
                delay(1000L)
            }
        }
    }

    fun coroutineDemo1() {
        // UI thread will freeze until this block of code is to finish
        runBlocking {
            Log.d(TAG,"Hello from ${Thread.currentThread().name}")
            withContext(Dispatchers.Default) {
                delay(10000L)
                Log.d(TAG,"Hello from ${Thread.currentThread().name}")
            }
            // This line won't execute until withContext(Dispatchers.Default) is to finish
            Log.d(TAG,"Welcome back to ${Thread.currentThread().name}")
        }
    }

    fun coroutineDemoUnconfined() {
        runBlocking(Dispatchers.Unconfined) {
            Log.d(TAG,"Hello from ${Thread.currentThread().name}")
            withContext(Dispatchers.Default) {
                Log.d(TAG,"Hello from ${Thread.currentThread().name}")
            }
            // Unconfined doesn't return back to main thread here
            Log.d(TAG,"Welcome back to ${Thread.currentThread().name}")
        }
    }

    fun coroutineDemoUnconfined2() {
        runBlocking<Unit> {
            launch(Dispatchers.Unconfined) { // не ограничено -- будет работать с основным потоком
                Log.d(TAG,"Unconfined      : I'm working in thread ${Thread.currentThread().name}")
                delay(500)
                Log.d(TAG,"Unconfined      : After delay in thread ${Thread.currentThread().name}")
            }
            launch { // контекст родителя, основная корутина runBlocking
                Log.d(TAG,"main runBlocking: I'm working in thread ${Thread.currentThread().name}")
                delay(1000)
                Log.d(TAG,"main runBlocking: After delay in thread ${Thread.currentThread().name}")
            }
        }
    }

    fun coroutineDemo2() {
        CoroutineScope(Dispatchers.Main).launch {
            // This coroutine runs on a main thread, but doesn't block it
            while (true) {
                delay(1000L)
            }
        }
    }

    fun coroutineDemo3() {
        runBlocking {
            // runBlocking runs on main
            Log.d(TAG, "runBlocking runs on ${Thread.currentThread().name}")
        }
        GlobalScope.launch {
            // GlobalScope.launch runs on DefaultDispatcher-worker-1
            Log.d(TAG, "GlobalScope.launch runs on ${Thread.currentThread().name}")
        }
        CoroutineScope(CoroutineName("MyCoroutine")).launch {
            // CoroutineScope(CoroutineName("MyCoroutine")).launch runs on DefaultDispatcher-worker-1
            Log.d(TAG, "CoroutineScope(CoroutineName(\"MyCoroutine\")).launch runs on ${Thread.currentThread().name}")
        }
        CoroutineScope(Dispatchers.Main).launch {
            // CoroutineScope(Dispatchers.Main).launch runs on main
            Log.d(TAG, "CoroutineScope(Dispatchers.Main).launch runs on ${Thread.currentThread().name}")
        }
        CoroutineScope(Dispatchers.Default).launch {
            // CoroutineScope(Dispatchers.Default).launch runs on DefaultDispatcher-worker-2
            Log.d(TAG, "CoroutineScope(Dispatchers.Default).launch runs on ${Thread.currentThread().name}")
        }
        CoroutineScope(Dispatchers.Unconfined).launch {
            // CoroutineScope(Dispatchers.Unconfined).launch runs on main
            Log.d(TAG, "CoroutineScope(Dispatchers.Unconfined).launch runs on ${Thread.currentThread().name}")
        }
        CoroutineScope(Dispatchers.IO).launch {
            // CoroutineScope(Dispatchers.IO).launch runs on DefaultDispatcher-worker-1
            Log.d(TAG, "CoroutineScope(Dispatchers.IO).launch runs on ${Thread.currentThread().name}")
        }
        // There are also viewModelScope in ViewModel and lifeCycleScope in activity and fragments
    }

    fun simpleCoroutineDemo() {
        GlobalScope.launch { // launch a new coroutine in background and continue work in main thread
            Log.d(TAG, "Current thread in coroutines section = ${Thread.currentThread().name}")
            delay(1000L) // Coroutine delay for 1 second (main thread doesn't affect)
            Log.d(TAG, "World!") // print after delay
        }
        Log.d(TAG, "Current thread = ${Thread.currentThread().name}")
        Log.d(TAG, "Hello,") // main thread continues while coroutine is delayed
        // Following row is required only for non-android apps.
        // Thread.sleep(2000L) // block main thread for 2 seconds to keep JVM alive (for coroutine could complete)
    }

    fun simpleCoroutineDemo2() = runBlocking<Unit> { // start main coroutine
        GlobalScope.launch { // launch a new coroutine in background and continue
            delay(1000L)
            Log.d(TAG, "World!")
        }
        Log.d(TAG, "Hello,") // main coroutine continues here immediately
        // Following row is required only for non-android apps.
        // delay(2000L)      // delaying for 2 seconds to keep JVM alive
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

    /**
     * Running each coroutine right when it is created
     */
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

    /**
     * Create all the coroutines before running them and later run them.
     */
    fun simpleCoroutineDemo9() {
        GlobalScope.launch(Dispatchers.IO) {
            val coroutines: List<Deferred<String>> = List(100) {
                async {
                    doWork(it)
                }
            }
            coroutines.forEach { Log.d(TAG, it.await()) }
            Log.d(TAG, "simpleCoroutineDemo9 finished its job")
        }
    }

    /**
     * Comparing to simpleCoroutineDemo9, coroutines start sequentially.
     */
    fun simpleCoroutineDemo10() {
        GlobalScope.launch(Dispatchers.IO) {
            val coroutines: List<Deferred<String>> = List(100) {
                async(start = CoroutineStart.LAZY) {
                    doWork(it)
                }
            }
            coroutines.forEach { Log.d(TAG, it.await()) }
            Log.d(TAG, "simpleCoroutineDemo10 finished its job")
        }
    }

    /**
     * Just like simpleCoroutineDemo9, but using launch and list of jobs instead of async.
     */
    fun simpleCoroutineDemo11() {
        GlobalScope.launch(Dispatchers.IO) {
            val coroutines: List<Job> = List(100) {
                launch {
                    doWork(it)
                }
            }
            coroutines.forEach {
                it.start()
                Log.d(TAG, it.toString())
            }
            Log.d(TAG, "simpleCoroutineDemo11 finished its job")
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
            launch(Dispatchers.IO) {
                // Unconfined             : I'm working in a thread DefaultDispatcher-worker-1
                Log.d(TAG, "IO                     : I'm working in a thread ${Thread.currentThread().name}")
            }
            launch(Dispatchers.Unconfined) {
                // Unconfined             : I'm working in a thread main
                Log.d(TAG, "Unconfined             : I'm working in a thread ${Thread.currentThread().name}")
            }
            launch(Dispatchers.Default) {
                // Default                : I'm working in a thread DefaultDispatcher-worker-2
                Log.d(TAG, "Default                : I'm working in a thread ${Thread.currentThread().name}")
            }
            launch(newSingleThreadContext("MyOwnThread")) {
                // MyOwnThread            : I'm working in a thread MyOwnThread
                Log.d(TAG, "MyOwnThread            : I'm working in a thread ${Thread.currentThread().name}")
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
            delay(2000L)    // Coroutine will be canceled in 2 secs
            job.cancel()
            Log.d(TAG, "cancelDemo cancelled")
        }
    }

    fun cancelNotWorkingDemo() {
        val job = CoroutineScope(Dispatchers.Default).launch {
            // Coroutine is pretty busy here and cannot check if it was cancelled
            for (i in 30..40) {
                Log.d(TAG, "Result for i=$i = ${fibonacci(i)}")
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
     * One can monitor if a coroutines was cancelled using isActive flag
     */
    fun cancelWorkingDemo() {
        val job = CoroutineScope(Dispatchers.Default).launch {
            for (i in 30..40) {
                if (isActive) {
                    Log.d(TAG, "Result for i=$i = ${fibonacci(i)}")
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
                    Log.d(TAG, "Result for i=$i = ${fibonacci(i)}")
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

    private fun fibonacci(n: Int): Int {
        return when (n) {
            0 -> 0
            1 -> 1
            else -> fibonacci(n - 1) + fibonacci(n - 2)
        }
    }

    private suspend fun simulateNetworkCall2() {
        delay(3000L)
        Log.d(TAG, "Data downloaded for simulateNetworkCall2")
    }

    fun twoNetworkCallsSequentially() {
        CoroutineScope(Dispatchers.IO).launch {
            val timeTaken = measureTimeMillis {
                val job = launch {
                    simulateNetworkCall()
                    simulateNetworkCall2()
                }
                job.join()  // Removing this line won't wait for network calls to finish
            }
            Log.d(TAG, "Time taken: $timeTaken.toString()")
        }
    }

    /** 2 network calls that run simultaneously */
    fun twoNetworkCallsInParallel() {
        CoroutineScope(Dispatchers.IO).launch {
            simulateNetworkCall()
        }
        CoroutineScope(Dispatchers.IO).launch {
            simulateNetworkCall2()
        }
    }

    /** 2 network calls that run simultaneously */
    fun twoNetworkCalls2() {
        CoroutineScope(Dispatchers.IO).launch {
            val time = measureTimeMillis {
                var response1 = ""
                /* Calling launch for asynchronous operation is a bad practice. Instead one should call async.
                 * This is a deferred in time job. Check twoNetworkCalls3 function for it. */
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

    /** 2 network calls that run simultaneously. Async instead of launch */
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

    /**
     * Prints from 1 to 3 with a delay of 1sec between each.
     */
    fun flowDemo() {
        GlobalScope.launch {
            (1..3).asFlow().onEach { delay(1000) }.collect { value -> println(value) }
        }
    }

    companion object {
        private const val TAG = "CoroutinesBasics"
    }
}
