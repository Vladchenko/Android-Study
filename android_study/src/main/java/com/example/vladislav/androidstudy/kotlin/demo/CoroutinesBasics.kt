package com.example.vladislav.androidstudy.kotlin.demo

import kotlinx.coroutines.*

/**
 * https://kotlinlang.org/docs/reference/coroutines/basics.html
 *
 * @author Yanchenko Vladislav on 25.08.2020.
 */
class CoroutinesBasics {

    fun simpleCoroutineDemo() {
        GlobalScope.launch { // launch a new coroutine in background and continue
            delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
            println("World!") // print after delay
        }
        println("Hello,") // main thread continues while coroutine is delayed
        Thread.sleep(2000L) // block main thread for 2 seconds to keep JVM alive (for coroutine could complete)
    }

    fun simpleCoroutineDemo2() = runBlocking<Unit> { // start main coroutine
        GlobalScope.launch { // launch a new coroutine in background and continue
            delay(1000L)
            println("World!")
        }
        println("Hello,") // main coroutine continues here immediately
        delay(2000L)      // delaying for 2 seconds to keep JVM alive
    }

    fun simpleCoroutineDemo3() = runBlocking { // <Unit> can be omitted
        val job = GlobalScope.launch { // launch a new coroutine and keep a reference to its Job
            delay(1000L)
            println("World!")
        }
        println("Hello,")
        job.join() // wait until child coroutine completes
    }

//    fun demoDispatchersAndThreads() {
//        CoroutineScope(Main).apply {
//            launch {
//                println("main runBlocking       : I'm working in a thread ${Thread.currentThread().name}")
//            }
//            launch(Dispatchers.Unconfined) {
//                println("Unconfined             : I'm working in a thread ${Thread.currentThread().name}")
//            }
//            launch(Dispatchers.Default) {
//                println("Default                : I'm working in a thread ${Thread.currentThread().name}")
//            }
//            launch(newSingleThreadContext("MyOwnThread")) {
//                println("Default                : I'm working in a thread ${Thread.currentThread().name}")
//            }
//        }
//    }

    fun demoDispatchersAndThreads2() = runBlocking<Unit> {
        launch { // context of the parent, main runBlocking coroutine
            println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
            println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Default) { // will get dispatched to DefaultDispatcher
            println("Default               : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(newSingleThreadContext("MyOwnThread")) { // will get its own new thread
            println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
        }
        // Output
//        System.out: Unconfined            : I'm working in thread main
//        System.out: Default               : I'm working in thread DefaultDispatcher-worker-1
//        System.out: main runBlocking      : I'm working in thread main
//        System.out: newSingleThreadContext: I'm working in thread MyOwnThread
    }
}
