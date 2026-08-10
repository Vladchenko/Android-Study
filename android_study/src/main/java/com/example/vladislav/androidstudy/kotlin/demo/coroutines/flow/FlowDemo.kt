package com.example.vladislav.androidstudy.kotlin.demo.coroutines.flow

import android.util.Log
import android.widget.Button
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import java.util.logging.Logger

/**
 * ┌─────────────────────────────────────┬─────────────────────────────────────┐
 * │         Короткий пример             │       Минимальное объяснение        │
 * ├─────────────────────────────────────┼─────────────────────────────────────┤
 * │ flow { emit(1) }                    │ Базовый билдер                      │
 * ├─────────────────────────────────────┼─────────────────────────────────────┤
 * │ flowOf(1, 2, 3)                     │ Фиксированные значения              │
 * ├─────────────────────────────────────┼─────────────────────────────────────┤
 * │ (1..3).asFlow()                     │ Из range или коллекции              │
 * ├─────────────────────────────────────┼─────────────────────────────────────┤
 * │ channelFlow { send(1) }             │ Concurrent эмит из корутин          │
 * ├─────────────────────────────────────┼─────────────────────────────────────┤
 * │ callbackFlow { trySend(1); awaitClose() } │ Обёртка callback API          │
 * ├─────────────────────────────────────┼─────────────────────────────────────┤
 * │ MutableStateFlow(0)                 │ Хранит текущее состояние            │
 * ├─────────────────────────────────────┼─────────────────────────────────────┤
 * │ MutableSharedFlow<Int>()            │ Поток событий                       │
 * ├─────────────────────────────────────┼─────────────────────────────────────┤
 * │ emptyFlow<Int>()                    │ Пустой поток                        │
 * └─────────────────────────────────────┴─────────────────────────────────────┘
 *
 * A set of several random tasks
 */
class FlowDemo {

    val ioScope = CoroutineScope(Dispatchers.IO)

    private var countDownStartValue = 10
    val countDownFlow = flow {
        while (countDownStartValue >= 0) {
            emit(countDownStartValue)
            delay(300)
            // Be aware, following row is not thread-safe. Better to move such var inside flow {}
            countDownStartValue--
        }
    }

    // Cold flow (won't emit until someone subscribes to it)
    private fun flowDemo1(): Flow<Int> = flow {
        for (i in 1..10) {
            delay(100)
            emit(i)
        }
    }

    fun flowDemo1Print() {
        GlobalScope.launch {    // Use GlobalScope only for demo purposes
            // print(it) strangely doesn't work here, but println(it) does ))
            flowDemo1().collect { println(it) }
        }
    }

    fun flowDemo1_2Print() =
        // Following scope is not saved to any val, so one cannot control its lifetime, cannot cancel it.
        // To resolve this, one could create a scope and place it as a class(object) member,
        // or at least pass it as a function parameter.
        CoroutineScope(Dispatchers.Main).launch {
        flowDemo1()
            .onStart {
                Log.i(TAG, "Collection started")
            }
            .onEach {
                Log.i(TAG, "Collected $it")
            }
            .onCompletion {
                Log.i(TAG, "Flow collection completed")
            }
            .flowOn(Dispatchers.IO) // All previous operators will run on IO thread
            .map { it + it }
            .collect(::println) // or { value ->
//        println("value: $value")
//    }
    }

    fun flowDemo1_3Print() = (1..10).asFlow()
        .map { it * it }
        .onEach(::println)
//        .flowOn(CoroutineScope(Dispatchers.IO) as CoroutineContext)
        .launchIn(ioScope)   // Same to wrapping all this flow code with CoroutineScope(Dispatchers.IO).launch { ... }

    /** Prints from 1 to 3 with a delay of 1sec between each. */
    fun flowDemo() {
        ioScope.launch {
            (1..3).asFlow()
                .onEach { delay(1000) }
                .collect { value -> println(value) }
        }
    }

    private fun flowDemo2() =
        flowOf(10..20)   // Here flow of one element (IntRange<10..20>), not of ten ints
    // One could use - (10..20).toList() or (10..20).asFlow to get separate values

    suspend fun flowDemo2Print() {
        flowDemo2()
            .onEach(::println)
            .map {
                delay(100)
                it + 100
            }
            .onEach(::println)
            .catch { cause -> Logger.getLogger("").severe("Exception: $cause") }
            .collect { println(it) }  // makes fun suspend
    } // Prints [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 100] - adds 100 to end of IntRange, not to each element. Declaring flowOf(1 .. 10) is not a good approach, better declare like this - (1 .. 10).asFlow()

    private fun flowDemo3() = (1..10).asFlow()
    // also - flowOf(1); flowOf("a", "b", "c", "d");  listOf(1,2,3,4).asFlow()

    suspend fun flowDemo3Print() = flowDemo3()
        .map { it + 100 }
        .filter { it % 2 == 0 }
        .collect { println(it) }

    fun fibonacci(count: Int) = flow {  // Creating own flow
        require(count >= 1) { "count must be positive value" }
        emit(1)
        if (count >= 2) emit(1)
        var previous = 1
        var current = 1
        for (i in 3..count) {
            val a = current
            current += previous
            previous = a
            emit(current)
        }
    }

    fun demoFibonacci(count: Int) {
        ioScope.launch {
            fibonacci(count).collect(::println)
        }
    }

    fun demoCountDownTimer() {
        ioScope.launch {
            countDownFlow
                .filter { it % 2 == 0 }
                .collect(::println)
        }
    }

    fun zipDemo() =
        ioScope.launch {
            flowDemo3().zip(    // Operates until any flow is to finish
                (1..3).asFlow(),
                { item1, item2 -> "${item1 * item2}" }
            ).collect(::println)
        }

    fun combineDemo() =
        ioScope.launch {
            flowDemo3().combine(    // Operates until both flows are to finish
                flowDemo1(),
                { value, value2 -> "${value}, ${value2}" }
            ).collect { println(it) }
        }

    // Flow cannot get data from coroutine(s) inside of this flow, to do this, use channelFlow
    // Оператор channelFlow позволял нам самим создать Flow, чтобы выполнять его код в корутине с нужным нам контекстом.
    // https://startandroid.ru/ru/courses/kotlin/29-course/kotlin/616-urok-21-korutiny-flow-operatory-channelflow-flowon-buffer-producein.html
    fun channelFlowDemo() {
        val flow = channelFlow {
            launch {
                delay(1000)
                send(1)
            }
            launch {
                delay(1000)
                send(2)
            }
            launch {
                delay(1000)
                send(3)
            }
        }
        val scope = CoroutineScope(Job())
        scope.launch {
            flow.collect {
                Log.i(TAG, "channelFlow received: $it")
            }
        }
    }

    // https://startandroid.ru/ru/courses/kotlin/29-course/kotlin/616-urok-21-korutiny-flow-operatory-channelflow-flowon-buffer-producein.html
    // Flow that lets send events when button is clicked
    fun callbackFlowDemo() {
        // Fake button, not working
        lateinit var button: Button
        val flow = callbackFlow {
            button.setOnClickListener {
                trySend(Unit)
            }
            awaitClose { button.setOnClickListener(null) }  // Required to finish this flow
        }
    }

    // https://startandroid.ru/ru/courses/kotlin/29-course/kotlin/617-urok-22-korutiny-flow-oshibka-otmena-povtor.html
    fun flowErrorDemo() {
        CoroutineScope(CoroutineName("SomeName")).launch {
            val flow = flow {
                delay(500)
                emit("1")
                delay(500)
                emit("2")

                val a = 1 / 0

                delay(500)
                emit("3")
                delay(500)
                emit("4")
            }
            flow
                .catch {
                    // Here error occurs
                    Log.i(TAG, "Error: $it")
                }
                .collect {
                    Log.i(TAG, "Collected item: $it")
                }
        }
    }

    companion object {
        private const val TAG = "FlowDemo"
    }
}