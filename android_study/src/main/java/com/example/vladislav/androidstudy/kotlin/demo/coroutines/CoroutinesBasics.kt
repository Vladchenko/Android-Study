package com.example.vladislav.androidstudy.kotlin.demo.coroutines

import android.net.http.HttpException
import android.util.Log
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.yield
import java.io.IOException
import kotlin.random.Random
import kotlin.system.measureTimeMillis
import kotlin.time.Duration.Companion.milliseconds

/**
 * https://kotlinlang.ru/docs/coroutines-basics.html
 * https://kotlinlang.org/docs/reference/coroutines/basics.html
 *
 * https://www.youtube.com/watch?v=ShNhJ3wMpvQ&list=PLQkwcJG4YTCQcFEPuYGuv54nYai_lwil_&index=1
 *
 * @author Yanchenko Vladislav on 25.08.2020.
 */
class CoroutinesBasics(val callback: (String) -> Unit) {

    // Do not commit this one
    fun temp() {
        GlobalScope.launch {// Operates on a worker thread
            // Thread is DefaultDispatcher-worker-2
            Log.d(TAG, "Thread is ${Thread.currentThread().name}")
            val job1: Job
            val job2: Job
            val timeTaken2 = measureTimeMillis {
                val timeTaken = measureTimeMillis {
                    Log.d(TAG, currentCoroutineContext().toString())
                    job1 = launch {
                        delay(1000L)
                        Log.d(TAG, currentCoroutineContext().toString())
                    }
                    job2 = launch {
                        delay(1000L)
                        Log.d(TAG, currentCoroutineContext().toString())
                    }
                }
                job1.join()
                job2.join()
                Log.d(TAG, timeTaken.toString())    // Somewhat more than 1000 and way less than 2000
            }
            Log.d(TAG, timeTaken2.toString())
        }
    }

    fun coroutineDemo() {
        // runBlocking blocks a thread it runs on. It is a bridge from ordinary function to suspend one.
        // Should only be used in main function and tests and not be used in any coroutine.
        // UI thread will freeze (when launched on UI thread) until this block of code is to finish
        runBlocking {   // Even stating Dispatchers(IO) here freezes UI thread.
            // BlockingCoroutine{Active}@3615897
            Log.d(TAG, this.toString()) // this - current scope
            // coroutineContext - current context of a coroutine
            // [BlockingCoroutine{Active}@b846792, BlockingEventLoop@bdac563]
            Log.d(TAG, this.coroutineContext.toString())
            // Since we run on main thread, next code will freeze UI.
            while (true) {
                delay(1000L)
            }
        }
    }

    fun coroutineDemo1() {
        // runBlocking blocks a UI thread even when dispatcher is (Dispatchers.IO). Why so ?
        // Gigacode chat response:
        // В данном коде блокируется UI поток, потому что вы используете `runBlocking` в главном потоке. `runBlocking` блокирует текущий поток до тех пор, пока все корутины внутри него не завершат свою работу.
        // Ваш `withContext(Dispatchers.Default)` действительно запускает корутину в фоновом потоке, но `runBlocking` все равно блокирует главный поток до тех пор, пока эта корутина не завершит свою работу.
        // Если вы хотите, чтобы ваш код не блокировал главный поток, вы можете использовать `launch` вместо `runBlocking`. `launch` запускает корутину в фоновом потоке и не блокирует текущий поток.
        runBlocking {
            // Thread is main
            Log.d(TAG, "Thread is ${Thread.currentThread().name}")
            // Context is [BlockingCoroutine{Active}@27e354d, BlockingEventLoop@a885802]
            Log.d(TAG, "Context is ${this.coroutineContext}")
            withContext(Dispatchers.Default) {
                delay(5000L)
                // Thread is DefaultDispatcher-worker-1
                Log.d(TAG, "Thread is ${Thread.currentThread().name}")
                // Context is [DispatchedCoroutine{Active}@a6a4513, Dispatchers.Default]
                Log.d(TAG, "Context is ${this.coroutineContext}")
            }
            // This line won't execute until withContext(Dispatchers.Default) is to finish
            Log.d(TAG, "Thread is ${Thread.currentThread().name}")
        }
    }

    fun coroutineDemo1_1() = runBlocking {
        // Rows 1, 2 will be executed at once
        launch { doWork() }     // 1
        Log.d(TAG, "Hello Coroutines")     // 2
    }

    fun coroutineDemoScope() = runBlocking {
        launch {// This coroutine inherits context of parent coroutine, i.e. runBlocking
            Log.d(TAG, "Thread is ${Thread.currentThread().name}")  // Thread is main
        }
    }

    private suspend fun doWork() {
        for (i in 0..5) {
            Log.d(TAG, i.toString())
            delay(400L)
        }
    }

    // These coroutines will be executed sequentially
    fun coroutineDemo1_2() = runBlocking {
        val job1 = launch {
            Log.d(TAG, "First coroutine")
            delay(1000)
        }
        job1.join()
        val job2 = launch {
            Log.d(TAG, "Second coroutine")
            delay(1000)
        }
        job2.join()
        val job3 = launch {
            Log.d(TAG, "Third coroutine")
            delay(1000)
        }
        job3.join()
    }

    fun coroutineDemo1_3() {
        // Creates 1000 coroutines and launches them at once.
        repeat(1_000) {
            // Thread is DefaultDispatcher-worker-69, so Dispatchers.IO creates like 70 threads in this case.
//            GlobalScope.launch(Dispatchers.IO) {
            // Dispatchers.Default create only 2 threads (but can up to 64)
//            GlobalScope.launch(Dispatchers.Default) {
            // App hangs up for about a half a minute, w/o printing
            // Coroutine #$it started. Thread is ${Thread.currentThread().name
            // and begins printing "Coroutine #xxxxx started. Thread is main"
//            GlobalScope.launch(Dispatchers.Main) {
            // Runs on a main thread
            // Unconfined means that this coroutine is not "attached" to one thread only - it can
            // change a thread it runs on at some conditions.
//            GlobalScope.launch(Dispatchers.Unconfined) {
            GlobalScope.launch {    // Seems to match Dispatchers.Default
                Log.d(TAG, "Coroutine #$it started. Thread is ${Thread.currentThread().name}")
                delay(Random.nextLong(500))
                Log.d(TAG, "Coroutine #$it finished. ")
            }
        }
    }

    fun coroutineDemoUnconfined() {
        runBlocking(Dispatchers.Unconfined) {
            // Hello from main
            Log.d(TAG, "Hello from ${Thread.currentThread().name}")
            withContext(Dispatchers.Default) {
                // Hello from DefaultDispatcher-worker-2
                Log.d(TAG, "Hello from ${Thread.currentThread().name}")
            }
            // Unconfined doesn't return back to main thread here
            // Welcome back to DefaultDispatcher-worker-2
            Log.d(TAG, "Welcome back to ${Thread.currentThread().name}")
        }
    }

    fun coroutineDemoUnconfined2() {
        runBlocking<Unit> {
            launch(Dispatchers.Unconfined) { // не ограничено - будет работать с основным потоком
                // Unconfined      : I'm working in thread main
                Log.d(TAG, "Unconfined      : I'm working in thread ${Thread.currentThread().name}")
                delay(500)
                // Unconfined      : After delay in thread kotlinx.coroutines.DefaultExecutor
                Log.d(TAG, "Unconfined      : After delay in thread ${Thread.currentThread().name}")
            }
            launch { // контекст родителя, основная корутина runBlocking
                // main runBlocking: I'm working in thread main
                Log.d(TAG, "main runBlocking: I'm working in thread ${Thread.currentThread().name}")
                delay(1000)
                // main runBlocking: After delay in thread main
                Log.d(TAG, "main runBlocking: After delay in thread ${Thread.currentThread().name}")
            }
        }
    }

    fun coroutineDemo2() {
        CoroutineScope(Dispatchers.Main).launch {
            // CoroutineScope(Dispatchers.Main) runs on main
            Log.d(TAG, "CoroutineScope(Dispatchers.Main) runs on ${Thread.currentThread().name}")
            // This coroutine runs on a main thread, but doesn't block it, because it frees a thread
            // (suspends), while performing a delay().
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
            // Thread is DefaultDispatcher-worker-1
            Log.d(
                TAG,
                "CoroutineScope(CoroutineName(\"MyCoroutine\")).launch runs on ${Thread.currentThread().name}"
            )
            // [CoroutineName(MyCoroutine), StandaloneCoroutine{Active}@53aea84, Dispatchers.Default]
            Log.d(TAG, this.coroutineContext.toString())
            // CoroutineName(MyCoroutine)
            Log.d(TAG, (this.coroutineContext[CoroutineName] ?: "").toString())
            // Requires @OptIn(ExperimentalStdlibApi::class)
//            Log.d(TAG, (this.coroutineContext[CoroutineDispatcher]?:"").toString())
        }
        CoroutineScope(Dispatchers.Main).launch {
            // CoroutineScope(Dispatchers.Main).launch runs on main
            Log.d(
                TAG,
                "CoroutineScope(Dispatchers.Main).launch runs on ${Thread.currentThread().name}"
            )
        }
        CoroutineScope(Dispatchers.Default).launch {
            // CoroutineScope(Dispatchers.Default).launch runs on DefaultDispatcher-worker-2
            Log.d(
                TAG,
                "CoroutineScope(Dispatchers.Default).launch runs on ${Thread.currentThread().name}"
            )
        }
        CoroutineScope(Dispatchers.Unconfined).launch {
            // CoroutineScope(Dispatchers.Unconfined).launch runs on main
            Log.d(
                TAG,
                "CoroutineScope(Dispatchers.Unconfined).launch runs on ${Thread.currentThread().name}"
            )
        }
        CoroutineScope(Dispatchers.IO).launch {
            // CoroutineScope(Dispatchers.IO).launch runs on DefaultDispatcher-worker-1
            Log.d(
                TAG,
                "CoroutineScope(Dispatchers.IO).launch runs on ${Thread.currentThread().name}"
            )
        }
        // There are also viewModelScope in ViewModel and lifeCycleScope in activity and fragments
    }

    fun simpleCoroutineDemo() {
        GlobalScope.launch { // launch a new coroutine in background and continue work in main thread
            Log.d(TAG, "Current thread in coroutines section = ${Thread.currentThread().name}")
            delay(1000L) // delay coroutine for 1 second (main thread doesn't affect)
            Log.d(TAG, "World!") // print after delay
        }
        Log.d(TAG, "Current thread = ${Thread.currentThread().name}") // main thread continues
        Log.d(TAG, "Hello,")
        // Following row is required only for non-android apps.
        // Thread.sleep(2000L) // block main thread for 2 seconds to keep JVM alive (for coroutine could complete)
    }

    fun simpleCoroutineDemo2() = runBlocking<Unit> { // start coroutine on a main thread
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
        // wait until child coroutine completes
        job.join()
        // In fact, there is no really need to wait for following job to complete,
        // since runBlocking waits for all child coroutines to finish.
    }

    fun simpleCoroutineDemo4() = runBlocking { // <Unit> can be omitted
        // Launch coroutine on main thread. Coroutine will suspend for 10 seconds
        GlobalScope.launch(Dispatchers.Main) {
            delay(10000L)
            Log.d(TAG, "First delay!")
        }
        // This coroutine is also executed on the Main thread
        repeat(10) {
            delay(200)
            Log.d(TAG, "$it")
        }
    }

    fun simpleCoroutineDemo5() = runBlocking { // <Unit> can be omitted
        // Doesn't block main thread also
        GlobalScope.launch(newSingleThreadContext("My thread")) {// Launch coroutine on newly created thread
            delay(10000L)
            Log.d(TAG, "First delay!")
        }
    }

    fun simpleCoroutineDemo6() = runBlocking { // <Unit> can be omitted
        GlobalScope.launch(Dispatchers.IO) {// Launch coroutine on newly created thread
            simulateNetworkCall()
            withContext(Dispatchers.Main) {
                // One may update some UI views here, if there is an access to UI.
                // But since there is none, let's make some callback.
                callback.invoke("Network call is over")
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

    /** Running each coroutine right when it is created */
    fun simpleCoroutineDemo8() {
        GlobalScope.launch(Dispatchers.IO) {
            val jobs = mutableListOf<Job>()
            repeat(100) {
                jobs.add(
                    launch {// Adding (start = CoroutineStart.LAZY) doesn't run
                        // coroutines in parallel. Why?
                        delay(Random.nextLong(3000))
                        Log.d(TAG, "Coroutines #$it done its job")
                    })
            }
            jobs.joinAll()
            Log.d(TAG, "simpleCoroutineDemo8 finished its job")
        }
        // Also watch https://stackoverflow.com/questions/63812589/coroutines-not-running-in-parallel
    }

    /** Create all the coroutines before running them and later run them. */
    fun simpleCoroutineDemo9() {
        GlobalScope.launch(Dispatchers.IO) {
            // These coroutines will start right away
            val coroutines: List<Deferred<String>> = List(100) {
                async {
                    doWork(it)
                }
            }
            coroutines.forEach { Log.d(TAG, it.await()) }
            Log.d(TAG, "simpleCoroutineDemo9 finished its job")
        }
    }

    /** Sequential and parallel start. */
    fun simpleCoroutineDemo10() {
        GlobalScope.launch(Dispatchers.IO) {
            // These coroutines will start only when they are invoked.
            val coroutines: List<Deferred<String>> = List(100) {
                async(start = CoroutineStart.LAZY) {
                    doWork(it)
                }
            }
            // This is the way they start.
            // When using start, coroutines execute in parallel
            coroutines.forEach {
                it.start()
                Log.d(TAG, it.toString())
            }
            // When using await(), coroutines execute sequentially
            coroutines.forEach { Log.d(TAG, it.await()) }
            Log.d(TAG, "simpleCoroutineDemo10 finished its job")
        }
    }

    private suspend fun doWork(it: Int): String {
        delay(Random.nextLong(3000))
        return "Coroutines #$it done its job"
    }

    private suspend fun simulateNetworkCall() {
        delay(7000L)
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
                Log.d(
                    TAG,
                    "IO                     : I'm working in a thread ${Thread.currentThread().name}"
                )
            }
            launch(Dispatchers.Unconfined) {
                // Unconfined             : I'm working in a thread main
                Log.d(
                    TAG,
                    "Unconfined             : I'm working in a thread ${Thread.currentThread().name}"
                )
            }
            launch(Dispatchers.Default) {
                // Default                : I'm working in a thread DefaultDispatcher-worker-2
                Log.d(
                    TAG,
                    "Default                : I'm working in a thread ${Thread.currentThread().name}"
                )
            }
            launch(newSingleThreadContext("MyOwnThread")) {
                // MyOwnThread            : I'm working in a thread MyOwnThread
                Log.d(
                    TAG,
                    "MyOwnThread            : I'm working in a thread ${Thread.currentThread().name}"
                )
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
            job.cancel()    // Coroutine won't cancel here
            Log.d(TAG, "cancelDemo cancelled")
        }
    }

    /**
     * One can monitor if a coroutine was cancelled using isActive flag
     * or use ensureActive() or yield()
     */
    fun cancelWorkingDemo() {
        val job = CoroutineScope(Dispatchers.Default).launch {
            for (i in 30..40) {
                if (isActive) {     // This flag checks if coroutine was cancelled
                    Log.d(TAG, "Result for i=$i = ${fibonacci(i)}")
                }
                // One can also use ensureActive() or yield()
            }
            Log.d(TAG, "fibonacci computation coroutine finished its work")
        }
        runBlocking {
            delay(1000L)
            job.cancel()
            Log.d(TAG, "fibonacci computation coroutine cancelled")
        }
    }

    /** One can set a timeout for a coroutine to run. */
    fun cancelWorkingDemo2() {
        val job = CoroutineScope(Dispatchers.Default).launch {
            withTimeout(5000L) {
                for (i in 30..40) {
                    Log.d(TAG, "Result for i=$i = ${fibonacci(i)}")
                }
            }
            Log.d(TAG, "fibonacci computation coroutine finished its work")
        }
        runBlocking {
            delay(1000L)
            job.cancel()    // Coroutine won't cancel here
            Log.d(TAG, "fibonacci computation coroutine cancelled")
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
            delay(3000)
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
                // Calling launch for asynchronous operation is a bad practice.
                // Instead one should call async. This is a deferred in time job.
                // Check twoNetworkCalls3 function for it.
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
                Log.d(
                    TAG,
                    response1.await()
                )   // await blocks current coroutines until answer is available
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

    fun flowDemo1Print() {
        GlobalScope.launch {
            // print(" $it") strangely doesn't work here
            flowDemo1().collect { println(it) }
        }
    }

    /** Prints from 1 to 3 with a delay of 1sec between each. */
    fun flowDemo() {
        GlobalScope.launch {
            (1..3).asFlow().onEach { delay(1000) }.collect { value -> println(value) }
        }
    }

    /** This way we wait for all the deferreds to finish */
    fun deferredDemo() = runBlocking {
        val deferreds: List<Deferred<Int>> = (1..3).map {
            async {
                delay(1000L * it)
                Log.d(TAG, "Loading $it")
                it
            }
        }
        val sum = deferreds.awaitAll().sum()
        println("$sum")
    }

    fun nonCancellableCoroutineDemo() {
        CoroutineScope(Dispatchers.Default).launch(NonCancellable) {
            // Some code
        }
    }

    suspend fun executeManyCoroutinesInParallelUsingAsync(): List<Int> {
        val result = coroutineScope {
            (1..5).map { n ->
                async {
                    val delay = Random.nextInt(100, 1000)
                    delay(delay.milliseconds)
                    println("- processing $n")
                    n * n
                }
            }.awaitAll()
        }
        println("Result: $result")
        return result
    }

    /** https://stackoverflow.com/questions/53577907/when-to-use-coroutinescope-vs-supervisorscope */
    private suspend fun compute(): String = coroutineScope {
        val job = SupervisorJob()
        async(job) {
            val color = async(job) { delay(6_000); "purple" }
            val height = async(job) {
                try {
                    delay(100)
                    throw HttpException("Connection lost", IOException())
                } catch (e: HttpException) {
                    Log.e(TAG, "")
                    return@async 0.0
                }
            }
            "The box is %.1f inches tall and it's %s".format(height.await(), color.await())
        }.await()
    }

    fun computeDemo() = GlobalScope.launch {
        Log.e(TAG, compute())
    }

    companion object {
        private const val TAG = "CoroutinesBasics"
    }
}