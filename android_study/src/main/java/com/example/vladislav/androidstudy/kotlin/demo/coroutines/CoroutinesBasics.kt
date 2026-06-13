package com.example.vladislav.androidstudy.kotlin.demo.coroutines

import android.net.http.HttpException
import android.util.Log
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.yield
import java.io.IOException
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.random.Random
import kotlin.system.measureTimeMillis
import kotlin.time.Duration.Companion.milliseconds
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Duration

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
        GlobalScope.launch {    // Operates on a worker thread
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

    fun temp2() {
        val handler = CoroutineExceptionHandler { context, exception ->
            Log.i(TAG, "coroutine exception $exception")
        }

        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default + handler)

        scope.launch {
            delay(1000)
            Log.i(TAG, "first coroutine isActive ${isActive}")
            Integer.parseInt("a")
            Log.i(TAG, "first coroutine isActive ${isActive}")
        }

        // While previous coroutine is cancelled due to exception, this one will keep working,
        // because of SupervisorJob.
        scope.launch {
            repeat(5) {
                delay(300)
                Log.i(TAG, "second coroutine isActive ${isActive}")
            }
        }
    }

    fun manyCoroutinesDemo() {
        repeat(1000) {
            CoroutineScope(Dispatchers.IO).launch {
                delay(Random.nextLong(3000))
                Log.i(TAG, "$it done!")
            }
        }
    }

    /**
     * Demo on cancelling parent coroutine before child ones complete
     */
    fun manyCoroutinesDemo2() {
        CoroutineScope(Dispatchers.IO).launch {
            val job = launch {
                val jobsList = List(1000) {
                    launch {
                        delay(Random.nextLong(3000))
                        Log.i(TAG, "$it began working!")
                    }
                }
                jobsList.joinAll()
                Log.i(TAG, "All coroutines finished their work")
            }
            delay(1000)
            job.cancel()    // Main job cancels before all coroutines complete
        }
    }

    @ExperimentalStdlibApi
    fun coroutineDemo() {
        // runBlocking blocks a thread it runs on. It is a bridge from ordinary function to suspend one.
        // Should only be used in main function and tests and not be used in any coroutine.
        runBlocking(Dispatchers.IO) {   // Even stating Dispatchers(IO) here freezes UI thread, since main thread is always blocked until runBlocking is to finish.
            // BlockingCoroutine{Active}@3615897
            Log.d(TAG, this.toString()) // this - current scope
            // coroutineContext - current context of a coroutine
            // [BlockingCoroutine{Active}@b846792, BlockingEventLoop@bdac563]
            Log.d(TAG, this.coroutineContext.toString())
            // Dispatchers.IO
            Log.d(TAG, this.coroutineContext[CoroutineDispatcher].toString())
            // Since we run on main thread, next code will freeze UI.
            // By default, launch is run on Dispatchers.Default (same to async)
            launch {    // Even using separate thread, UI thread is locked
                while (true) {
                    delay(1000L)
                }
            }
        }
        // !!! The reason of this - runBlocking is always launched on main thread and blocks it
        // until all inner coroutines are to complete. runBlocking(Dispatchers.IO) makes inner
        // coroutine to run on worker thread.
    }

    fun coroutineDemo1() {
        runBlocking {
            // Thread is main
            Log.d(TAG, "Thread is ${Thread.currentThread().name}")
            // Context is [BlockingCoroutine{Active}@27e354d, BlockingEventLoop@a885802]
            Log.d(TAG, "Context is ${this.coroutineContext}")
            // withContext can return a result
            val result = withContext(Dispatchers.Default) {
                delay(5000L)
                // Thread is DefaultDispatcher-worker-1
                Log.d(TAG, "Thread is ${Thread.currentThread().name}")
                // Context is [DispatchedCoroutine{Active}@a6a4513, Dispatchers.Default]
                Log.d(TAG, "Context is ${this.coroutineContext}")
            }
            Log.d(TAG, result.toString())
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
            // change a thread it runs on when returning to this coroutine.
//            GlobalScope.launch(Dispatchers.Unconfined) {
            GlobalScope.launch {    // Seems to match Dispatchers.Default
                Log.d(TAG, "Coroutine #$it started. Thread is ${Thread.currentThread().name}")
                delay(Random.nextLong(500))
                Log.d(TAG, "Coroutine #$it finished. ")
            }
            // GlobalScope может исп-ся когда есть фоновая синхронизация данных, логгирование, аналитика, телеметрия.
        }
    }

    // Just like coroutineDemo1_3, but with time spent measuring
    fun coroutineDemo1_3_1() = runBlocking {
        val jobs = mutableListOf<Job>()
        // Creates 1000 coroutines and launches them at once.
        val time = measureTimeMillis {
            repeat(1_000) {
                jobs.add(launch(Dispatchers.IO.limitedParallelism(2)) {     // Try removing limitedParallelism and see how much time is spent
                    // Dispatchers.Default creates threads with number = to CPU cores and not less than 2
//                jobs.add(launch(Dispatchers.Default) {
                    // App hangs up for about a half a minute, w/o printing
                    // Coroutine #$it started. Thread is ${Thread.currentThread().name
                    // and begins printing "Coroutine #xxxxx started. Thread is main"
//            jobs.add(launch(Dispatchers.Main) { // On new laptop, waited for 2 minutes - no sprint
                    // Runs on a main thread
                    // Unconfined means that this coroutine is not "attached" to one thread only - it can
                    // change a thread it runs on when returning to this coroutine.
//            jobs.add(launch(Dispatchers.Unconfined) {
                    Log.d(TAG, "Coroutine #$it started. Thread is ${Thread.currentThread().name}")
                    delay(Random.nextLong(500))
                    Log.d(TAG, "Coroutine #$it finished. ")
                })
                // GlobalScope может исп-ся когда есть фоновая синхронизация данных, логгирование, аналитика, телеметрия.
            }
        }
        jobs.joinAll()
        Log.d(TAG, "Time taken: $time")
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
                Log.d(
                    TAG,
                    "Unconfined      : I'm working in thread ${Thread.currentThread().name}"
                )
                delay(500)
                // Unconfined      : After delay in thread kotlinx.coroutines.DefaultExecutor
                Log.d(
                    TAG,
                    "Unconfined      : After delay in thread ${Thread.currentThread().name}"
                )
            }
            launch { // контекст родителя, основная корутина runBlocking
                // main runBlocking: I'm working in thread main
                Log.d(
                    TAG,
                    "main runBlocking: I'm working in thread ${Thread.currentThread().name}"
                )
                delay(1000)
                // main runBlocking: After delay in thread main
                Log.d(
                    TAG,
                    "main runBlocking: After delay in thread ${Thread.currentThread().name}"
                )
            }
        }
    }

    fun coroutineDemo2() {
        CoroutineScope(Dispatchers.Main).launch {
            // CoroutineScope(Dispatchers.Main) runs on main
            Log.d(
                TAG,
                "CoroutineScope(Dispatchers.Main) runs on ${Thread.currentThread().name}"
            )
            // This coroutine runs on a main thread, but doesn't block it, because it frees a thread
            // (suspends), while performing a delay().
            while (true) {
                delay(1000L)
            }
        }
    }

    @ExperimentalStdlibApi
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
            // Dispatchers.Default
            Log.d(TAG, (this.coroutineContext[CoroutineDispatcher] ?: "").toString())
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
        // There are also viewModelScope in ViewModel and lifeCycleScope in activity and fragments, both run on main thread
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
        // Throws kotlinx.coroutines.JobCancellationException: BlockingCoroutine was cancelled; job=BlockingCoroutine{Cancelled}@f3b2870
//        this.cancel()   // and crashes the app
        GlobalScope.launch(Dispatchers.Main) {
            Log.d(TAG, "First delay!")
            delay(10000L)
        }
        // This code is also executed on the Main thread
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
        // ? Почему здесь runBlocking не блочит главный поток на 10 сек ? Ведь runBlocking должен
        // ожидать завершения всех дочерних корутин с блокировкой мэин потока
        // ! runBlocking ждёт только свои дочерние корутины, но GlobalScope.launch — это НЕ дочерняя
        // корутина! Она находится вне runBlocking. Она не поддерживает structured concurrency.
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
                    launch {// Adding (start = CoroutineStart.LAZY) doesn't run coroutine right away
                        // coroutines in parallel. Why?
                        delay(Random.nextLong(3000))
                        Log.d(TAG, "Coroutines #$it done its job")
                    })
            }
//            jobs.forEach { it.start() } // To run in parallel
            jobs.joinAll()  // With (start = CoroutineStart.LAZY) present, runs coroutines sequentially unless previous line is uncommented
            Log.d(TAG, "simpleCoroutineDemo8 finished its job")
        }
        // Проблема в jobs.joinAll():
        //Когда вы вызываете joinAll() на коллекции ленивых корутин, происходит следующее:
        //join() — это приостанавливающая функция, которая ждет завершения корутины
        //Для ленивой корутины, если она еще не запущена, join() автоматически запускает ее
        //Но так как joinAll() запускается в цикле последовательно, каждая корутина запускается и полностью завершается перед тем, как будет запущена следующая

        // Почему последовательно:
        //Фактически, ваш код ведет себя так:
        //for (job in jobs) {
        //    job.join()  // для ленивой корутины: запускает и ждет завершения
        //}

        // Also watch https://stackoverflow.com/questions/63812589/coroutines-not-running-in-parallel
    }

    /** Create all the coroutines in advance and later run them. */
    fun simpleCoroutineDemo9() {
        GlobalScope.launch(Dispatchers.IO) {
            // These coroutines will start right away
            val coroutines: List<Deferred<String>> = List(100) {
                async(start = CoroutineStart.LAZY) {
                    Log.d(TAG, "Coroutine $it started")
                    val value = doWork(it)
                    Log.d(TAG, "Coroutine $it finished")
                    value
                }
            }
//            coroutines.forEach { Log.d(TAG, it.await()) }
            coroutines.awaitAll()   // even with LAZY start coroutines run in parallel
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
//            coroutines.awaitAll()     // This one runs in them in parallel
            Log.d(TAG, "simpleCoroutineDemo10 finished its job")
            // Checked online - start and await() launch coroutines in parallel and sequentially respectively.
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
                // I'm working in a thread main
                println("I'm working in a thread ${Thread.currentThread().name}")
            }
            launch(Dispatchers.IO) {
                // I'm working in a thread DefaultDispatcher-worker-1
                Log.d(
                    TAG,
                    "I'm working in a thread ${Thread.currentThread().name}"
                )
            }
            launch(Dispatchers.Unconfined) {
                // I'm working in a thread main
                Log.d(
                    TAG,
                    "I'm working in a thread ${Thread.currentThread().name}"
                )
            }
            launch(Dispatchers.Default) {
                // I'm working in a thread DefaultDispatcher-worker-2
                Log.d(
                    TAG,
                    "I'm working in a thread ${Thread.currentThread().name}"
                )
            }
            launch(newSingleThreadContext("MyOwnThread")) {
                // I'm working in a thread MyOwnThread
                Log.d(
                    TAG,
                    "I'm working in a thread ${Thread.currentThread().name}"
                )
            }
        }
    }

    fun demoDispatchersAndThreads2() = runBlocking<Unit> {
        launch {
            // I'm working in thread main
            Log.d(
                TAG,
                "I'm working in thread ${Thread.currentThread().name}"
            )
        }
        launch(Dispatchers.Unconfined) {
            // I'm working in thread main
            Log.d(
                TAG,
                "I'm working in thread ${Thread.currentThread().name}"
            )
        }
        launch(Dispatchers.Default) {
            // I'm working in thread DefaultDispatcher-worker-1
            Log.d(
                TAG,
                "I'm working in thread ${Thread.currentThread().name}"
            )
        }
        launch(newSingleThreadContext("MyOwnThread")) {
            // newSingleThreadContext: I'm working in thread MyOwnThread
            Log.d(
                TAG,
                "I'm working in thread ${Thread.currentThread().name}"
            )
        }
    }

    fun cancelDemo() {
        val job = CoroutineScope(Dispatchers.Default).launch {
            Log.d(TAG, "cancelDemo begun its work")
            repeat(5) {
                Log.d(TAG, "cancelDemo is working...")
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
            job.cancel()    // Coroutine won't cancel here
            Log.d(TAG, "cancelNotWorkingDemo cancelled")
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
                // await blocks current coroutines until answer is available
                Log.d(TAG, response1.await())
                Log.d(TAG, response2.await())
            }
            Log.d(TAG, "time spent = $time")
        }
    }

    fun lifeCycleDemo() {
//        lifecycleScope.launch     // Runs only in fragment or activity
//        viewmodelScope.launch     // Runs only in view model
    }

    private suspend fun networkCall1(): String {
        delay(2000L)
        return "networkCall1 response"
    }

    private suspend fun networkCall2(): String {
        delay(2000L)
        return "networkCall2 response"
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
        // this == coroutineScope
        Log.e(TAG, compute())
    }

    val scope = MainScope()
    val scope2 = CoroutineScope(EmptyCoroutineContext)
    val scope3 = CoroutineScope(
        Job()
                + Dispatchers.Main
                + CoroutineExceptionHandler({ _, e -> e.printStackTrace() })
    )
    val scope4 = CoroutineScope(SupervisorJob() + Dispatchers.IO + CoroutineName("MyCoroutine"))
    val scope5 = CoroutineScope(scope3.coroutineContext + CoroutineName("MyCoroutine2"))

    @OptIn(DelicateCoroutinesApi::class)
    fun mutexDemo() {
        val mutex = Mutex()
        var counter = 0
        val jobs = mutableListOf<Job>()
        val scope = CoroutineScope(Job() + Dispatchers.Default)
        scope.launch {
            repeat(10_000) {
                jobs.add(
                    launch(Dispatchers.IO) {
                        mutex.withLock {
                            counter++
                        }
                    }
                )
            }
            jobs.joinAll()
            Log.i(TAG, counter.toString())
            scope.cancel()  // Скоуп обязательно отменять, иначе он будет висеть в памяти всегда
        }
    }

    // Пример с отдельным методом
    suspend fun mutexDemoSuspend(): Int {
        val mutex = Mutex()
        var counter = 0

        coroutineScope {
            repeat(10_000) {
                launch {
                    mutex.withLock {
                        counter++
                    }
                }
            }
            // coroutineScope автоматически ждёт все дочерние корутины
        }

        return counter
    }

    // Функция для запуска из не-suspend контекста
    fun runMutexDemo() {
        val scope = CoroutineScope(Job() + Dispatchers.Default)

        scope.launch {
            val result = mutexDemoSuspend()
            println("Counter: $result")
            scope.cancel()  // Чистим за собой
        }
    }

    class BadExample() {
        private suspend fun call1() = withContext(Dispatchers.IO) {
            async {
                Log.d(TAG, "Call 1 started")
                delay(2000)
                Log.d(TAG, "Call 1 fiished")
            }
        }

        private suspend fun call2() = withContext(Dispatchers.IO) {
            async {
                Log.d(TAG, "Call 2 started")
                delay(3000)
                Log.d(TAG, "Call 2 finished")
            }
        }

        private suspend fun call3() = withContext(Dispatchers.IO) {
            async {
                Log.d(TAG, "Call 3 started")
                delay(1000)
                Log.d(TAG, "Call 3 finished")
            }
        }

        // These coroutines run sequentially, instead of in parallel
        fun doWork() {
            CoroutineScope(Dispatchers.IO).launch {
                call1()
                call2()
                call3()
            }
        }

        private suspend fun correctedCall1() {
            Log.d(TAG, "Call 1 started")
            delay(2000)
            Log.d(TAG, "Call 1 fiished")
        }

        private suspend fun correctedCall2() {
            Log.d(TAG, "Call 2 started")
            delay(3000)
            Log.d(TAG, "Call 2 finished")
        }

        private suspend fun correctedCall3() {
            Log.d(TAG, "Call 3 started")
            delay(1000)
            Log.d(TAG, "Call 3 finished")
        }

        // These coroutines run in parallel
        fun correctedDoWork() {
            CoroutineScope(Dispatchers.IO).launch {
                launch {
                    correctedCall1()
                }
                launch {
                    correctedCall2()
                }
                launch {
                    correctedCall3()
                }
            }
        }
    }

    fun check() {
        CoroutineScope(Dispatchers.IO).launch {
            val job = launch {
                delay(1000)
                println("Coroutine finished")
            }
            job.join()
            println("main continues")
        }
    }


// Following funs made just for practice
// TODO Remove

    fun type1To5WithDelay() =
        CoroutineScope(Dispatchers.IO + Job()).launch {
            var i = 0
            repeat(5) {
                Log.i(TAG, i.toString())
                i++
                delay(1000)
            }
        }

    fun type1To5WithDelay_2() =
        CoroutineScope(Dispatchers.IO).launch {
            (1..5).asFlow()
                .onEach {
                    delay(1000)
                }.collect {
                    Log.i(TAG, it.toString())
                }
        }

    fun task2() = CoroutineScope(Dispatchers.Default).launch {
        launch {
            repeat(10) {
                if (it % 2 == 1) {
                    Log.i(TAG, it.toString())
                    delay(1000)
                }
            }
        }
        launch {
            for (j in 2..10) {
                if (j % 2 == 0) {
                    Log.i(TAG, j.toString())
                    delay(2000)
                }
            }
        }
    }

    fun task2asChannel() =
        CoroutineScope(Dispatchers.IO).launch {
            val channel = Channel<Int>()
            launch {
                repeat(10) {
                    if (it % 2 == 1) {
                        delay(2000)
                        channel.send(it)
                    }
                }
            }
            launch {
                repeat(10) {
                    if (it % 2 == 0) {
                        delay(1000)
                        channel.send(it)
                    }
                }
            }
            repeat(10) {
                Log.i(TAG, channel.receive().toString())
            }
        }

    companion object {
        private const val TAG = "CoroutinesBasics"
    }
}

class CancellationDemo() {
    val handler = CoroutineExceptionHandler { context, handler ->
        Log.e(TAG, "${context.job} ${handler.message.toString()}")
    }

    fun example1() {
        //region scope
        val scopeWithHandler = CoroutineScope(Dispatchers.Default + Job())
        // Exception is printed in CoroutineExceptionHandler
        scopeWithHandler.launch(handler) { throw RuntimeException() }
        scopeWithHandler.launch(handler) {
            delay(100)
            // This line is not printed, since this coroutine is cancelled
            Log.i(TAG, "CoroutineScope, RuntimeException, CoroutineExceptionHandler, child")
        }

        val scopeWithTryCatch = CoroutineScope(Dispatchers.Default + Job())
        // Since exception is caught, it won't propagate to parent to subsequently cancel all its children
        scopeWithTryCatch.launch {
            try {
                throw RuntimeException()
            } catch (ex: Exception) {
                Log.i(TAG, ex.message.toString())
            }
        }
        scopeWithTryCatch.launch {
            delay(100)
            // This line is printed, since this coroutine is not cancelled
            Log.i(TAG, "CoroutineScope, RuntimeException, try-catch, child")
        }

        val cancellationScope = CoroutineScope(Dispatchers.Default + Job())
        // Since it is an ordinary cancel, then it does not cancel other children and parent.
        // If it were caused from error like NullPointerException, then it would propagate up
        // and cancel everyone. It is the same to calling .cancel() on it.
        cancellationScope.launch { throw CancellationException() }
        cancellationScope.launch {
            delay(100)
            // This line is printed, since this coroutine is not cancelled
            Log.i(TAG, "CoroutineScope, CancellationException child")
        }

        val timeoutScope = CoroutineScope(Dispatchers.Default + Job())
        timeoutScope.launch {
            // Throws TimeoutCancellationException, cancels this coroutine, but doesn't send
            // exception up the chain (nothing else is cancelled)
            withTimeout(50, {
                delay(80)
            })
        }
        timeoutScope.launch {
            delay(100)
            // This line is printed, since this coroutine is not cancelled
            Log.i(TAG, "CoroutineScope, withTimeout child")
        }

        // Waiting for all these scopes to finish
        CoroutineScope(Dispatchers.Default).launch {
            scopeWithHandler.coroutineContext[Job]?.join()
            scopeWithTryCatch.coroutineContext[Job]?.join()
            cancellationScope.coroutineContext[Job]?.join()
        }
        //endregion scope

        //region supervisorScope
        val supervisorScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
        // Cancels itself, but won't cancel parent and its child coroutines because of
        // SupervisorJob, but will crash the app unless caught in CoroutineExceptionHandler
        // or in try-catch block.
        // Exception is printed in CoroutineExceptionHandler
        supervisorScope.launch(handler) { throw RuntimeException() }
        supervisorScope.launch(handler) {
            delay(100)
            // This line is printed, since this coroutine is not cancelled
            Log.i(TAG, "SupervisorJob, RuntimeException, CoroutineExceptionHandler, child")
        }

        val supervisorTryCatchScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
        // Since exception is caught, doesn't cancel itself. SupervisorScope is not even of use here.
        supervisorTryCatchScope.launch {
            try {
                throw RuntimeException()
            } catch (ex: Exception) {
                Log.e(TAG, ex.message.toString())
            }
        }
        // This line is printed, since this coroutine is not cancelled
        supervisorTryCatchScope.launch {
            delay(100)
            Log.i(TAG, "SupervisorJob, RuntimeException try-catch child")
        }

        val cancellationSupervisorScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
        // Since it is an ordinary cancel, then it does not stop other children and parent.
        // SupervisorJob won't even have to engage in this case.
        cancellationSupervisorScope.launch { throw CancellationException() }
        cancellationSupervisorScope.launch {
            delay(100)
            // This line is printed, since this coroutine is not cancelled
            Log.i(TAG, "SupervisorJob, CancellationException child")
        }

        // Waiting for scope to finish
        CoroutineScope(Dispatchers.Default).launch {
            supervisorTryCatchScope.coroutineContext[Job]?.join()
            cancellationSupervisorScope.coroutineContext[Job]?.join()
            supervisorScope.coroutineContext[Job]?.join()
        }
        //endregion supervisorScope
    }

    fun example2() {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }
        val job = CoroutineScope(Dispatchers.IO).launch(handler) {
            launch { // the first child
                try {
                    delay(Long.MAX_VALUE)
                } finally {
                    // With this change to NonCancellable, coroutine doesn't get cancelled
                    withContext(NonCancellable) {
                        Log.i(
                            TAG,
                            "Children are cancelled, but exception is not handled until all children terminate"
                        )
                        delay(100)
                        Log.i(TAG, "The first child finished its non cancellable block")
                    }
                }
            }
            launch { // the second child
                delay(10)
                Log.i(TAG, "Second child throws an exception")
                throw ArithmeticException()
            }
        }
        CoroutineScope(Dispatchers.IO).launch(handler) {
            job.join()
        }
    }

    fun example3() {
        runBlocking {
            val handler = CoroutineExceptionHandler { _, exception ->
                Log.i(
                    TAG,
                    "CoroutineExceptionHandler got $exception with suppressed ${exception.suppressed.contentToString()}"
                )
            }
            val job = CoroutineScope(handler).launch {
                launch {
                    try {
                        delay(Long.MAX_VALUE) // it gets cancelled when another sibling fails with IOException
                    } finally {
                        throw ArithmeticException() // the second exception
                    }
                }
                launch {
                    delay(100)
                    throw IOException() // the first exception
                }
            }
        }
    }

    // Here supervisorJob doesn't work
    fun example3_1() {
        runBlocking {
            val handler = CoroutineExceptionHandler { _, exception ->
                Log.i(
                    TAG,
                    "CoroutineExceptionHandler got $exception with suppressed ${exception.suppressed.contentToString()}"
                )
            }
            // Although there is SupervisorJob() in declaration of this coroutine, it is not a SupervisorJob
            val job = CoroutineScope(handler + SupervisorJob()).launch {
                // Even making launch() to have SupervisorJob doesn't make it so
//          val job = CoroutineScope(handler + SupervisorJob()).launch(SupervisorJob()) {
                launch {
                    try {
                        delay(Long.MAX_VALUE) // it gets cancelled when another sibling fails with IOException
                    } finally {
                        throw ArithmeticException() // the second exception
                    }
                }
                launch {
                    delay(100)
                    throw IOException() // the first exception
                }
            }
        }
    }

    // Correct way of using supervisorJob
    fun example3_2() {
        runBlocking {
            val handler = CoroutineExceptionHandler { _, exception ->
                Log.i(
                    TAG,
                    "CoroutineExceptionHandler got $exception with suppressed ${exception.suppressed.contentToString()}"
                )
            }
            val scope = CoroutineScope(handler + SupervisorJob())
            scope.launch {
                try {
                    delay(Long.MAX_VALUE) // it gets cancelled when another sibling fails with IOException
                } finally {
                    throw ArithmeticException() // the second exception
                }
            }
            scope.launch {
                delay(100)
                throw IOException() // the first exception
            }
        }
    }

    /*
     * Данный пример демонстрирует различные способы бесконечного приостановления (suspend) корутин
     * и то, как все они корректно отменяются через родительскую отмену.
     * Несмотря на то, что каждая корутина использует разный механизм приостановки
     * (таймеры, каналы, deferred, мьютексы), все они кооперативно реагируют на отмену и корректно
     * завершаются без утечек ресурсов. Это демонстрирует унифицированную модель отмены в Kotlin Coroutines.
     */
    fun example4() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Default) {
                val childJobs = listOf(
                    launch {
                        // Suspends until canceled
                        awaitCancellation()
                    },
                    launch {
                        // Suspends until canceled
                        delay(Duration.INFINITE)
                    },
                    launch {
                        val channel = Channel<Int>()
                        // Suspends while waiting for a value that is never sent
                        channel.receive()
                    },
                    launch {
                        val deferred = CompletableDeferred<Int>()
                        // Suspends while waiting for a value that is never completed
                        deferred.await()
                    },
                    launch {
                        val mutex = Mutex(locked = true)
                        // Suspends while waiting for a mutex that remains locked indefinitely
                        mutex.lock()
                    }
                )

                // Gives the child coroutines time to start and suspend
                delay(100.milliseconds)

                // Cancels all child coroutines
                childJobs.forEach { it.cancel() }
            }
            println("All child jobs completed!")
        }
    }

    // async exception propagation
    fun example5() {
        CoroutineScope(Job()).launch {
            launch {
                delay(2000L)
                Log.i(TAG, "coroutine is over")
            }
            // Although async throws an exception only in await(), it anyway propagates error
            // from its coroutine to a parent. And to stop its propagation, one needs to use
            // SupervisorJob in its builder.
            val deferred = async(Job()) {   // Job is used to see that error is really propagated
                delay(1000L)
                "a".toInt()
            }
            try {
                deferred.await()
            } catch (ex: Exception) {
                Log.i(TAG, ex.toString())
            }
        }
    }

    // Job in child coroutine breaks connection with parent
    fun example6() {
        CoroutineScope(
            Dispatchers.IO
                    + CoroutineExceptionHandler { _, ex -> Log.wtf(TAG, "$ex") }
        ).launch {
            // Putting Job() here breaks a connection with a parent (structured concurrency).
            // I.e. having an error in this coroutine with this Job() provided, won't cause parent to fail
            launch(Job()) {
                delay(1000)
                "q".toInt()
                Log.i(TAG, "Child coroutine is over")
            }
            delay(2000)
            Log.i(TAG, "Parent coroutine is over")
        }
    }

    companion object {
        private const val TAG = "CancellationDemo"
    }
}