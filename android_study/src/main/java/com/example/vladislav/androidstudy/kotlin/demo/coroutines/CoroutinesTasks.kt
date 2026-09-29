package com.example.vladislav.androidstudy.kotlin.demo.coroutines

import android.os.Build
import android.service.autofill.UserData
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.yield
import okhttp3.internal.toImmutableList
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.URL
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.cancellation.CancellationException
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

/**
 * Some tasks from Deepseek to study coroutines
 */
class CoroutinesTasks {

    // region task1
    /**
     * Напиши простую программу на Kotlin с использованием корутин, которая выводит на экран числа
     * от 1 до 5 с задержкой в одну секунду перед каждым выводом.
     */
    fun generateNumbers(scope: CoroutineScope, delayMs: Long = 1000L, count: Int = 5): Job {
        return scope.launch {
            try {
                Log.i(TAG, "Task1 started")
                repeat(count) { value -> // Or (1..count).forEach { value ->
                    delay(delayMs) // Delay checks for cancellation
                    Log.i(TAG, "Task1 received value:${(value + 1)}")
                }
            } catch (e: CancellationException) {
                Log.i(TAG, "Task1 cancelled")
                throw e // Rethrowing CancellationException for structured concurrency to work
            } catch (e: Exception) {
                Log.e(TAG, "Task1 error", e)
                throw e // Rethrowing exception for parent coroutine to be aware of the exception
            }
        }
        //✅ Correct delay (before every log)
        //✅ No magic numbers
        //✅ Correct exceptions handling
        //✅ Task starting logging
        //❌ Function's SRP broken - separate numbers generation and their logging
    }

    fun getNumberSequence(count: Int) =
        (1..count).asSequence() // One could also use flow, but task says to use coroutines

    fun generateNumbersCorrected(
        scope: CoroutineScope,
        delayMs: Long = 1000L,
        count: Int = 5
    ): Job {
        return scope.launch {
            try {
                Log.i(TAG, "Task1 started")
                getNumberSequence(count).forEach { value ->
                    delay(delayMs) // delay checks for cancellation
                    Log.i(TAG, "Task1 received value: $value")
                }
            } catch (e: CancellationException) {
                Log.i(TAG, "Task1 cancelled")
                throw e // Rethrowing CancellationException for structured concurrency to work
            } catch (e: Exception) {
                Log.e(TAG, "Task1 error", e)
                throw e // Rethrowing exception for parent coroutine to be aware of the exception
            }
        }
        //✅ Correct delay (before every log)
        //✅ No magic numbers
        //✅ Correct exceptions handling
        //✅ Task starting logging
        //✅ Separated concerns
    }

    fun generateNumbersFlow(count: Int) = flow {
        (1..10).forEach { emit(it) }
    }

    // Same to generateNumbers but with flow
    fun generateNumbers2(scope: CoroutineScope, count: Int): Job =
        scope.launch {
            generateNumbersFlow(count)
                .onEach { delay(1000) } // Some heavy operation presumed
                .flowOn(Dispatchers.IO)
                .collect { value ->
                    Log.d(TAG, value.toString())
                }
        }
    // endregion task1

    // region task2
    /**
     * Создай две корутины: одна должна выводить чётные числа от 2 до 10 каждые 2 секунды, вторая
     * — нечётные числа от 1 до 9 каждую секунду. Выводы обеих корутин должны происходить
     * одновременно, чередуя друг друга.
     */
    suspend fun task2() =
        coroutineScope {
            launch {
                val evenNumbersJob = launch {
                    var number = 2
                    while (number <= 10) {  // Better to use kotlin-idiomatic declarative way - for (number in 2..10 step 2) {
                        delay(2000L)
                        Log.d(TAG, "Task2: Even Number: $number")
                        number += 2
                    }
                }

                val oddNumbersJob = launch {
                    var number = 1
                    while (number <= 9) {   // Better to use kotlin-idiomatic declarative way - for (number in 1..9 step 2) {
                        delay(1000L)
                        Log.d(TAG, "Task2: Odd Number: $number")
                        number += 2
                    }
                }

                // - Jobs' vals could be omitted, since one doesn't do cancel or join on them.
                // - If one needs a result of these jobs, one should use following row -
//            joinAll(evenNumbersJob, oddNumbersJob) // Ожидаем завершение обеих корутин
                // - It might be required in some case to wait for the block of these 2 coroutines to
                // finish before exiting, in such case - surround them with coroutineScope { ... }
            }
        }

    fun task2Test() {
        CoroutineScope(Dispatchers.Default).launch {
            task2()
        }
    }

    /**
     * Решение предыдущей задачи каналами
     */
    fun task2_1() = CoroutineScope(Dispatchers.IO).launch {
        val channel = Channel<Int>()
        launch {    // coroutineScope doesn't work here
            val job1 = launch {
                var num = 2
                while (num <= 10) {
                    channel.send(num)
                    Log.i(TAG, "Data sent $num")
                    num += 2
                    delay(2000L)
                }
            }
            val job2 = launch {
                var num = 1
                while (num <= 9) {
                    channel.send(num)
                    Log.i(TAG, "Data sent $num")
                    num += 2
                    delay(1000L)
                }
            }
            job1.join()
            job2.join()
            channel.close()
            Log.i(TAG, "Data sending complete")
        }

        launch {
            for (item in channel) {
                Log.i(TAG, "Data received $item")
            }
            Log.i(TAG, "Data receiving complete")
        }
    }

    fun task2_2() = CoroutineScope(Dispatchers.IO).launch {
        val channel = Channel<Int>()
        launch {
            for (item in channel) {
                Log.i(TAG, "Data received $item")
            }
        }
        coroutineScope {    // Waits for children to finish
            launch {
                var num = 2
                while (num <= 10) {
                    channel.send(num)
                    Log.i(TAG, "Data sent $num")
                    num += 2
                    delay(2000L)
                }
            }
            launch {
                var num = 1
                while (num <= 9) {
                    channel.send(num)
                    Log.i(TAG, "Data sent $num")
                    num += 2
                    delay(1000L)
                }
            }
        }
        channel.close()
        Log.i(TAG, "Data receiving complete")
        Log.i(TAG, "Data sending complete")
    }

    // Максимально функциональная реализация с внешним скоупом для универсальности
    fun task2_3(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            (2..10 step 2).forEach { value ->
                delay(2000)
                Log.i(TAG, "Coroutine #1: $value")
            }
        }
        coroutineScope.launch {
            (1..9 step 2).forEach { value ->
                delay(1000)
                Log.i(TAG, "Coroutine #2: $value")
            }
        }
    }

    /*
    Почему List лучше:
        Данные уже есть — вы не грузите их из сети/БД, они уже в памяти
        Маленький объем — всего 10 чисел, нет смысла в стриминге
        Код проще — никаких collect(), catch, flowOn
        Производительность выше — нет оверхеда на создание Flow
    Когда Flow ДЕЙСТВИТЕЛЬНО нужен
        Данные приходят асинхронно (из сети, БД, сенсоров)
        Большой объем данных (миллионы записей)
        Нужна обработка ошибок и retry
            fetchDataFlow()
                .retry(3) { it is IOException }
                .catch { emit(defaultValue) }
        Нужна отмена на каждом шаге
            for (i in 1..1000) {
                emit(i)  // ← Проверяет отмену на каждом emit
            }
        Нужен backpressure (управление скоростью)
            fastProducerFlow
                .buffer(100)  // ← Буфер
     */
    fun task2_4(scope: CoroutineScope, numbers: List<Int>) {
        scope.launch {
            launch {
                try {
                    numbers
                        .filter { it % 2 == 0 }
                        .forEach {
                            delay(2000)
                            Log.i(TAG, "task2 even: $it")
                        }
                } catch (ex: CancellationException) {
                    Log.e(TAG, ex.message ?: "Evens collection cancelled")
                } catch (ex: Exception) {
                    Log.e(TAG, ex.message ?: "Exception during evens collection")
                }
            }
            launch {
                try {
                    numbers
                        .filter { it % 2 != 0 }
                        .forEach {
                            delay(1000)
                            Log.i(TAG, "task2 odd: $it")
                        }
                } catch (ex: CancellationException) {
                    Log.e(TAG, ex.message ?: "Odds collection cancelled")
                } catch (ex: Exception) {
                    Log.e("Error", ex.message ?: "Exception during odds collection")
                }
            }
        }
    }
    // endregion task2

    // region task3
    /**
     * Напишите функцию, которая параллельно вычисляет:
     *  Факториал числа (с задержкой 1 секунда)
     *  Сумму чисел от 1 до N (с задержкой 1 секунда)
     * Возвращает результат быстрейшей операции (гонка)
     */
    fun task3(factorialNumber: Int, sumNumber: Int, scope: CoroutineScope) {
//        CoroutineScope(Dispatchers.Default).launch { - лучше выносить наружу, передавая сюда скоуп. Как вариант, вообще возвращать Job для отслеживания.
        scope.launch {
            Log.i(TAG, "Task3 began its work")
            val fastest = select {
                async {
                    factorial(factorialNumber)
                }.onAwait { it }
                async {
                    sumOfN(sumNumber)
                }.onAwait { it }
            }
            Log.i(TAG, fastest.toString())
        }
        // Instead of direct calling select, one could use extension like -
        //
        // suspend fun <T> awaitAny(vararg deferreds: Deferred<T>): T = select {
        //    deferreds.forEach { deferred ->
        //        deferred.onAwait { it }
        //    }
        // }
        // and call following way
        // val fastest = awaitAny(
        //    async { factorial(10) },
        //    async { sumOfN(100) }
        // )
    }

    private suspend fun factorial(n: Int): Long {
        var result = 1L
        delay(1000)
        for (incN in 1..n) {
            currentCoroutineContext().ensureActive()     // Required, else at cancellation, coroutine won't stop
            result *= incN
        }
        return result
    }

    private suspend fun sumOfN(n: Int): Long {
        var result = 0L
        delay(1000)
        for (incN in 1..n) {
            currentCoroutineContext().ensureActive()     // Required, else at cancellation, coroutine won't stop
            result += incN
        }
        return result
    }

    fun task3_2(factorialNumber: Int, sumNumber: Int, coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            runCatching {
                select {
                    async { factorial2(factorialNumber) }.onAwait { it }
                    async { sumOfN2(sumNumber) }.onAwait { it }
                }
            }.onFailure { exception ->
                Log.i(TAG, exception.message.toString())
                if (exception is CancellationException) throw exception
            }.onSuccess { result ->
                when (result) {
                    is Task3Result.Factorial -> {
                        Log.i(TAG, "Factorial is: '${result.value}'")
                    }

                    is Task3Result.SumN -> {
                        Log.i(TAG, "Sum of 1..N numbers is: '${result.value}'")
                    }
                }
            }
        }
    }

    sealed class Task3Result() {
        data class Factorial(val value: Long) : Task3Result()
        data class SumN(val value: Long) : Task3Result()
    }

    private suspend fun factorial2(n: Int): Task3Result {
        var result = 1L
        for (incN in 1..n) {
            currentCoroutineContext().ensureActive()     // Required, else at cancellation, coroutine won't stop
            result *= incN
        }
        return Task3Result.Factorial(result)
    }

    private suspend fun sumOfN2(n: Int): Task3Result {
        var result = 0L
        for (incN in 1..n) {
            currentCoroutineContext().ensureActive()     // Required, else at cancellation, coroutine won't stop
            result += incN
        }
        return Task3Result.SumN(result)
    }
    // endregion task3

    //region task4
    /**
     * У вас есть список чисел. Нужно обработать каждое число с задержкой 500 мс, но если вся
     * обработка занимает больше 3 секунд - прервать выполнение и вернуть то, что успели.
     */
    suspend fun task4(ints: List<Int>, timeoutMs: Long): List<Int> = coroutineScope {
        val processed = arrayListOf<Int>()
        val job = launch {
            // launch не пробрасывает TimeoutCancellationException, а async пробрасывает.
            // В случае с async, результат работы этой корутины не будет выведен, так как внешняя
            // корутина тоже отменится.
            withTimeout(timeoutMs) {
                for (item: Int in ints) {
                    delay(500)
                    processed.add(item)
                }
            }
        }
        job.join()
        // Даже без job.join() код работает верно. Хоть далее мы сразу возвращаем результат,
        // coroutineScope всё равно дожидается завершения дочерних корутин. return отработает только
        // когда завершится launch.
        return@coroutineScope processed.toList()    // Возвращаем неизменяемый list для безопасности
    }

    // Same task reworked considering the drawbacks of the previous one.
    suspend fun task4_1(
        list: List<Int>,
        itemDelay: Long = 500L,
        totalTimeout: Long = 3000L
    ): List<Int> {
        val resultList = mutableListOf<Int>()
        Log.i(TAG, "Processing ${list.size} items with 3s timeout")
        runCatching {
            withTimeout(totalTimeout) {
                list.onEach { item ->   // buildList seems to be an option, иut LLM says it cannot pass a partial result (not full list in this case)
                    delay(itemDelay)
                    resultList.add(item)
                    Log.i(TAG, "$item processed")
                }
            }
        }.onSuccess {
            Log.i(TAG, "Completed successfully! Processed list: $resultList")
        }
            .onFailure { exception ->  // onFailure rethrows CancellationException on its own, so no need to rethrow yourself
                when (exception) {
                    is TimeoutCancellationException -> {
                        Log.i(TAG, "Coroutine cancelled by timeout. Processed list: $resultList")
                        throw exception
                    }

                    is CancellationException -> {
                        Log.i(TAG, "Coroutine cancelled. Processed list: $resultList")
                        throw exception
                    }

                    else -> {
                        Log.i(
                            TAG,
                            "Completed with error ! ${exception.message}. Processed list: $resultList"
                        )
                    }
                }
            }
        return resultList.toList()
    }

    // Implemented with async and separate scope
    suspend fun task4_2(
        ints: List<Int>,
        itemDelay: Long,
        timeout: Long,
        scope: CoroutineScope
    ): List<Int> {
        val mutableList = mutableListOf<Int>()
        scope.async {
            runCatching {
                withTimeout(timeout) {
                    ints
                        .asFlow()
                        .onEach { listItem ->
                            Log.i(TAG, "item: $listItem is being processed")
                            delay(itemDelay)
                            Log.i(TAG, "item: $listItem processed")
                        }
                        .collect { listItem ->
                            mutableList.add(listItem)
                        }
                }
            }.onFailure { exception ->
                if (exception is TimeoutCancellationException) {
                    Log.e(TAG, "Task 4 has timed out")
                    Log.e(TAG, exception.message.toString())
                }
                return@async mutableList
            }.onSuccess {
                Log.i(TAG, "All items processed successfully")
                return@async mutableList
            }
        }.await()
        return mutableList
    }
    //endregion task4

    //region task5
    /**
     * Есть 10 задач, но одновременно могут выполняться только 3. Реализуйте обработку.
     */
    suspend fun task5() = coroutineScope {
        val semaphore = Semaphore(3)
        val activeTasks = AtomicInteger(0) // AtomicInteger здесь нужен только для
        // подсчёта и логирования — сколько корутин сейчас реально находится внутри критической секции.
        val list = List(10) {
            launch { // Dispatchers.IO.limitedParallelism(3) - почему-то не работает, ИИ подскажет
                semaphore.withPermit {
                    val concurrent = activeTasks.incrementAndGet()
                    delay(100)
                    Log.d(TAG, "Tasks running in parallel: $concurrent")
                    activeTasks.decrementAndGet()
                }
            }
        }
    }

    suspend fun task5_1(jobs: List<Job>, parallelLimit: Int) {
        val semaphore = Semaphore(parallelLimit)
        withContext(Dispatchers.IO) {
            jobs.forEach { job ->
                launch {    // Launches a separate coroutine for each Job, else Jobs run in 1 coroutine
                    semaphore.withPermit {
                        job.start()
                        job.join()  // One has to await finishing unless, all jobs will run at once
                    }
                }
            }
        }
    }

    suspend fun task5_1Run(coroutineScope: CoroutineScope) {
        val jobs = List(10) { item ->
            coroutineScope.launch(start = CoroutineStart.LAZY) {
                Log.i(TAG, "Coroutine #$item started")
                Log.i(TAG, "Thread is ${Thread.currentThread().name}")
                delay(Random.nextLong(300) + 200)
                Log.i(TAG, "Coroutine #$item finished")
            }
        }
        task5_1(jobs, parallelLimit = 3)
    }

    // Made this task not watching to task5_1
    fun task5_2(
        scope: CoroutineScope,
        tasks: List<Job>, // These jobs are already started, so no sense in limiting their parallelism, i.e. thi fun is useless
        parallel: Int
    ) {
        val semaphore = Semaphore(parallel)
        tasks.forEach { task ->
            // Bad solution, since creates extra coroutine on each existing coroutine
            // But making just one coroutine for all tasks - doesn't work
            scope.launch {
                semaphore.withPermit {
                    task.join()
                }
            }
        }
    }
    //endregion task5

    // region task6
    private val dateFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())

    /**
     * У вас есть три асинхронных операции, которые должны выполниться последовательно,
     * причем каждая следующая использует результат предыдущей:
     *  fetchUserToken() – получаем токен пользователя (задержка 500 мс)
     *  fetchUserData(token) – по токену загружаем данные пользователя (задержка 500 мс)
     *  saveToLocalCache(userData) – сохраняем данные в локальный кэш (задержка 300 мс)
     *  После этого нужно вернуть UserData и логировать время выполнения каждого шага.
     *
     * Дополнительный вызов (со звездочкой):
     *  Реализуйте параллельную загрузку двух разных источников данных после получения токена,
     *  а потом сохраните результат
     */
    @RequiresApi(Build.VERSION_CODES.P)
    suspend fun task6() = coroutineScope {
        val token = fetchUserTokenSelection()  //fetchUserToken()
        val data = fetchUserData(token)
        saveToLocalCache(data)
        return@coroutineScope data
    }

    private sealed class TokenResult() {
        data class Local(val token: UUID) : TokenResult()
        data class Remote(val token: UUID) : TokenResult()
        object Fail : TokenResult()
    }

    private suspend fun fetchUserToken(): UUID {
        Log.i(TAG, "User token retrieval begin time: ${dateFormat.format(Date())}")
        delay(500)
        Log.i(TAG, "User token retrieval end time: ${dateFormat.format(Date())}")
        return UUID.randomUUID()
    }

    private suspend fun fetchUserTokenSelection(): UUID? {
        val token: UUID? = null
        coroutineScope {
            val token = select {
                async { fetchUserTokenFromNetwork() }.onAwait { it }
                async { fetchUserTokenFromDisk() }.onAwait { it }
            }
            when (token) {
                is TokenResult.Fail -> {
                    Log.e(TAG, "Token retrieval failed")
                }

                is TokenResult.Local -> {
                    Log.i(TAG, "Token retrieval local succeeded")
                }

                is TokenResult.Remote -> {
                    Log.i(TAG, "Token retrieval remote succeeded")
                }
            }
            return@coroutineScope token
        }
        return token
    }

    private suspend fun fetchUserTokenFromNetwork(): TokenResult {
        Log.i(TAG, "User token from network retrieval begin time: ${dateFormat.format(Date())}")
        delay(Random.nextInt(500) + 100L)
        Log.i(TAG, "User token from network retrieval end time: ${dateFormat.format(Date())}")
        return TokenResult.Remote(UUID.randomUUID())
    }

    private suspend fun fetchUserTokenFromDisk(): TokenResult {
        Log.i(TAG, "User token from disk retrieval begin time: ${dateFormat.format(Date())}")
        delay(Random.nextInt(500) + 100L)
        Log.i(TAG, "User token from disk retrieval end time: ${dateFormat.format(Date())}")
        return TokenResult.Local(UUID.randomUUID())
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private suspend fun fetchUserData(token: UUID?): UserData {
        Log.i(TAG, "User data retrieval begin time: ${dateFormat.format(Date())}")
        delay(500)
        Log.i(TAG, "User data retrieval end time: ${dateFormat.format(Date())}")
        return UserData.Builder(token.toString(), "100", "categoryId")
            .add("100", "categoryId")
            .build()
    }

    private suspend fun saveToLocalCache(data: UserData) {
        Log.i(TAG, "User token save begin time: ${dateFormat.format(Date())}")
        delay(500)
        Log.i(TAG, "User token save end time: ${dateFormat.format(Date())}")
    }
    // endregion task6

    // region task7
    /**
     * Retry с экспоненциальной задержкой
     * Условие:
     * Вам нужно реализовать функцию retryOnFailure, которая:
     * Выполняет переданную suspend операцию
     * Если операция упала с исключением, повторяет её с увеличивающейся задержкой
     * Максимальное количество попыток: 5
     * Начальная задержка: 100 мс
     * Задержка удваивается после каждой неудачной попытки (100, 200, 400, 800 мс...)
     * После исчерпания попыток пробрасывает последнее исключение
     * Перед каждой попыткой логирует номер попытки
     */
    suspend fun <T> retryOnFailure(
        maxAttempts: Int = 5,
        initialDelay: Long = 100L,
        block: suspend () -> T
    ): T {
        var delayMs = initialDelay
        repeat(maxAttempts) { attempt ->
            runCatching {
                block.invoke()
            }.onSuccess { result ->
                Log.i(
                    TAG,
                    "Success on attempt #${attempt + 1}, with previous delay=${delayMs}."
                )
                return result
            }.onFailure { ex ->
                if (ex is CancellationException) throw ex
                delay(delayMs)
                delayMs *= 2
                Log.e(TAG, ex.message.toString())
                if (attempt == 4) throw ex
            }
        }
//        return block() - вместо этого правильнее писать как на следующей строке
        throw IllegalStateException("Should not reach here")
    }

    // Same implementation, but no return value
    suspend fun retryOnFailure2(
        retries: Int = 5,
        initialDelay: Long = 100,
        suspendBlock: suspend () -> Unit
    ) {
        var delay = initialDelay
        var exception: Exception? = null
        repeat(retries) {
            try {
                delay(delay)
                val result = suspendBlock.invoke()
                Log.i(TAG, "Task7 succeeded")
                return result
            } catch (e: Exception) {
                if (e is CancellationException) {
                    Log.i(TAG, "Task7 cancelled")
                    throw e
                }
                exception = e
                delay *= 2
                Log.e(TAG, "Error in task7_", e)
            }
        }
        exception?.let {
            throw it
        }
        throw IllegalStateException("Should not reach here")
    }

    var attempt = 0
    suspend fun unstableNetworkCall(): String {
        delay(100)
        attempt++
        if (attempt < 3) {
            throw Exception("Network error on attempt $attempt")
        }
        return "Success on attempt $attempt"
    }
    // endregion task7

    // region task8
    /**
     * У вас есть три источника данных:
     *      fetchFromCache() — быстрый (200 мс),
     *      fetchFromLocalDb() — средний (400 мс), но точный
     *      fetchFromNetwork() — медленный (800 мс), но самый свежий
     * Нужно реализовать функцию fetchFirstNonEmpty(), которая:
     * Запускает все три источника параллельно
     * Как только первый источник вернул непустые данные (не null и не пустой список) — сразу отменяет остальные задачи
     * Возвращает эти данные
     * Если все три вернули null (или пустой список) — возвращает null
     * Логирует, какой источник «победил»
     * Дополнительное задание (со звёздочкой)
     *      Реализуйте версию, которая не просто возвращает первые данные, а ждёт хотя бы один
     *      успешный результат, но если за 500 мс ничего не пришло — берёт то, что есть (даже null).
     */
    sealed class Source() {
        data class CacheSource(val data: List<Any>) : Source()
        data class DbSource(val data: List<Any>) : Source()
        data class NetworkSource(val data: List<Any>) : Source()
        data class Error(val throwable: Throwable) : Source()
    }

    private suspend fun fetchFromCache(): Source? {
        delay(200)
        return null
    }

    private suspend fun fetchFromLocalDb(): Source? {
        delay(400)
        // When using resources, one should use .use { // resource access } to close them correctly after usage
        return if (Random.nextInt() > 0.3) Source.DbSource(listOf("fetchFromLocalDb")) else null
    }

    private suspend fun fetchFromNetwork(): Source? {
        delay(800)
        // When using resources, one should use .use { // resource access } to close them correctly after usage
        return null
    }

    suspend fun fetchFirstNotEmpty() =
        coroutineScope {
            val cachedDeferred = async { fetchFromCache() }
            val dbDeferred = async { fetchFromLocalDb() }
            val networkDeferred = async { fetchFromNetwork() }
            val cachedValue = cachedDeferred.await()
            if (cachedValue != null) {
                dbDeferred.cancel()
                networkDeferred.cancel()
                return@coroutineScope cachedValue
            } else {
                val dbValue = dbDeferred.await()
                if (dbValue != null) {
                    networkDeferred.cancel()
                    return@coroutineScope dbValue
                } else {
                    return@coroutineScope networkDeferred.await()
                }
            }
        }

    // This one is for case when data retrieval time is unknown for each source
    suspend fun fetchFirstNotEmpty2(): Source? {
        return coroutineScope {
            var result: Source? = null
            val cacheTask = async { fetchFromCache() }
            val dbTask = async { fetchFromLocalDb() }
            val networkTask = async { fetchFromNetwork() }
            val list = listOf(cacheTask, dbTask, networkTask)
            while (list.all { !it.isCompleted }) {
                coroutineContext.ensureActive()
                result = select {
                    cacheTask.onAwait { it }
                    dbTask.onAwait { it }
                    networkTask.onAwait { it }
                }
                if (result != null && result.isNotEmpty()) break
            }
            cacheTask.cancel()
            dbTask.cancel()
            networkTask.cancel()
            result
        }
    }

    private fun Source?.isNotEmpty(): Boolean {
        return when (this) {
            is Source.DbSource -> data.isNotEmpty()
            is Source.CacheSource -> data.isNotEmpty()
            is Source.NetworkSource -> data.isNotEmpty()
            else -> false
        }
    }

    // Attempted to make a code to look production-like
    suspend fun fetchFirstNotEmptyWithTimeout(timeout: Long) =
        coroutineScope {
            ensureActive()
            val cachedDeferred = async { fetchFromCache() }
            val dbDeferred = async { fetchFromLocalDb() }
            val networkDeferred = async { fetchFromNetwork() }
            try {
                withTimeout(timeout) {
                    val cachedValue = cachedDeferred.await()
                    if (cachedValue != null) {
                        dbDeferred.cancel()
                        networkDeferred.cancel()
                        return@withTimeout cachedValue
                    } else {
                        val dbValue = dbDeferred.await()
                        if (dbValue != null) {
                            networkDeferred.cancel()
                            return@withTimeout dbValue
                        } else {
                            return@withTimeout networkDeferred.await()
                        }
                    }
                }
            } catch (exception: TimeoutCancellationException) {
                Log.i(TAG, "Cancelled due to ${exception.cause}")
                cachedDeferred.cancel()
                dbDeferred.cancel()
                networkDeferred.cancel()
                throw exception
            } catch (throwable: Throwable) {
                cachedDeferred.cancel()
                dbDeferred.cancel()
                networkDeferred.cancel()
                if (throwable is CancellationException) throw throwable
                return@coroutineScope Source.Error(throwable)
            } finally {
                // Resources freeing, unless used .use()
            }
        }

    // Предложил deepseek, не запускал, код как будто рабочий
    suspend fun fetchFirstNotEmptyWithTimeout2(timeout: Long) = coroutineScope {
        val deferreds = listOf(
            async { fetchFromCache() to "Cache" },
            async { fetchFromLocalDb() to "LocalDb" },
            async { fetchFromNetwork() to "Network" }
        )

        // Ждем таймаут или пока все не завершатся
        delay(timeout)

        // Берем первый НЕ пустой из завершенных
        deferreds
            .filter { it.isCompleted }
            .firstNotNullOfOrNull { it.getCompleted().takeIf { it.first != null } }
            ?.first
    }
    // endregion task8

    // region task9
    /**
     * «Обработчик событий с паузами»
     * Условие:
     *      У вас есть поток событий (например, нажатия кнопок, данные с сенсора). Нужно реализовать
     *      suspend-функцию, которая собирает события за определённый интервал и возвращает их список.
     * Требования:
     *      Функция suspend fun collectEvents(timeoutMs: Long): List<String>
     *      Ждёт первое событие (бесконечно долго, пока не появится)
     *      После первого события начинает отсчёт timeoutMs
     *      Если за это время появились новые события — добавляет их в список
     *      Таймаут сбрасывается после каждого нового события
     *      Когда таймаут истёк — функция возвращает все собранные события
     *      Функция должна быть безопасной для отмены
     * Логировать:
     *      Первое событие
     *      Каждое новое событие в пачке
     *      Истечение таймаута
     *
     * Дополнительное задание (со звездочкой):
     *      Добавить максимальный общий таймаут — если общее время сбора превышает maxTotalMs,
     *      функция возвращает то, что есть (даже пустой список).
     */
    @FlowPreview
    suspend fun task9(
        maxTotalMs: Long,
        itemTimeout: Long
    ): List<Long> {
        val list = mutableListOf<Long>()
        runCatching {
            withTimeout(maxTotalMs) {
                eventsFlow()
                    .onStart { Log.i(TAG, "Collecting began") }
                    .onEach {
                        ensureActive()
                        Log.i(TAG, "Collected: $it")
                    }
                    .onCompletion { exception ->
                        Log.i(TAG, "Collecting complete $exception")
                    }
                    .timeout(itemTimeout.milliseconds)
                    // .catch doesn't catch CancellationExceptions
                    // by the way it is redundant here
                    // https://startandroid.ru/ru/courses/kotlin/29-course/kotlin/617-urok-22-korutiny-flow-oshibka-otmena-povtor.html
//                    .catch { exception ->
//                        Log.e(TAG, "Error: ${exception.message}", exception)
//                        throw exception
//                    }
                    .toCollection(list)
            }
        }.onFailure { exception ->
            Log.i(TAG, "Stopped after ${list.size} items: ${exception.message}")
            // One should not put check for CancellationException, since any result has to be returned and cancellation won't let doing it.
            if (exception is CancellationException
                && exception !is TimeoutCancellationException
            ) throw exception
        }
        return list.toList()
    }

    private fun eventsFlow(): Flow<Long> {
        return flow {
            while (currentCoroutineContext().isActive) {
                val someValue = Random.nextLong(100) + 100
                delay(someValue)
                emit(someValue)
            }
        }
    }

    // Without global timeout
    @FlowPreview
    suspend fun collectEvents(flow: Flow<String>, timeoutMs: Long): List<String> {
        val list = mutableListOf<String>()
        return coroutineScope {
            list.add(
                flow
                    .onStart {
                        Log.i(TAG, "Task9: Data reception begun")
                    }
                    .onEach { value ->
                        Log.i(TAG, "Task9: received value - $value")
                    }
                    .catch { ex ->
                        when (ex) {
                            is TimeoutCancellationException -> {
                                Log.i(TAG, "Task9: Timed out with ${timeoutMs}ms")
                                throw ex
                            }

                            is CancellationException -> {
                                Log.i(TAG, "Task9: Cancelled")
                                throw ex
                            }

                            else -> {
                                Log.i(TAG, "Task9: Error - ${ex.message}")
                                throw ex
                            }
                        }
                    }
                    .first()
            )
            flow
                .timeout(timeoutMs.milliseconds)
                .catch { ex ->
                    when (ex) {
                        is TimeoutCancellationException -> {
                            Log.i(TAG, "Task9: Timed out with ${timeoutMs}ms")
                            throw ex
                        }

                        is CancellationException -> {
                            Log.i(TAG, "Task9: Cancelled")
                            throw ex
                        }

                        else -> {
                            Log.i(TAG, "Task9: Error - ${ex.message}")
                            throw ex
                        }
                    }
                }
                .onCompletion { throwable ->
                    if (throwable != null) {
                        Log.e(TAG, "Task9: Flow completed with error", throwable)
                    } else {
                        Log.i(
                            TAG,
                            "Task9: Flow completed successfully, collected ${list.size} events"
                        )
                    }
                }
                .collect { value ->
                    currentCoroutineContext().ensureActive()
                    list.add(value)
                    Log.i(TAG, "Task9: received value - $value")
                }
            return@coroutineScope list
        }
    }

    @FlowPreview
    suspend fun collectEventsWithTimeout(
        flow: Flow<String>,
        timeoutMs: Long,
        maxTotalMs: Long
    ): CollectResult {
        val list = mutableListOf<String>()
        return coroutineScope {
            try {
                list.add(
                    flow
                        .onStart {
                            Log.i(TAG, "Task9: Data reception begun")
                        }
                        .onEach { value ->
                            Log.i(TAG, "Task9: received value - $value")
                        }
                        .first()
                )
                withTimeout(maxTotalMs) {
                    flow
                        .timeout(timeoutMs.milliseconds)
                        .onCompletion { throwable ->
                            if (throwable != null) {
                                Log.e(TAG, "Task9: Flow completed with error", throwable)
                            } else {
                                Log.i(
                                    TAG,
                                    "Task9: Flow completed successfully, collected ${list.size} events"
                                )
                            }
                        }
                        .collect { value ->
                            currentCoroutineContext().ensureActive()
                            list.add(value)
                            Log.i(TAG, "Task9: received value - $value")
                        }
                    return@withTimeout CollectResult.Success(list)
                }
            } catch (throwable: Throwable) {
                when (throwable) {
                    is TimeoutCancellationException -> {
                        Log.i(TAG, "Task9: Timed out with ${timeoutMs}ms")
                        return@coroutineScope CollectResult.Error(list, throwable)
                    }

                    is CancellationException -> {
                        Log.i(TAG, "Task9: Cancelled")
                        return@coroutineScope CollectResult.Error(list, throwable)
                    }

                    else -> {
                        Log.i(TAG, "Task9: Error - ${throwable.message}")
                        return@coroutineScope CollectResult.Error(list, throwable)
                    }
                }
            }
        }
    }

    fun eventsFlowString(): Flow<String> {
        return flow {
            while (currentCoroutineContext().isActive) {
                val someValue = Random.nextLong(100) + 100
                delay(someValue)
                emit(someValue.toString())
            }
        }
    }

    sealed class CollectResult {
        data class Success(val events: List<String>) : CollectResult()
        data class Error(val events: List<String>, val error: Throwable) : CollectResult()
    }// endregion task9

    //region task10
    /**
     * «Карусель запросов с ротацией»
     * Условие:
     *      У вас есть несколько серверов-источников данных. Нужно реализовать функцию, которая
     *      последовательно опрашивает их, пока не получит успешный ответ, а затем запоминает этот
     *      источник и начинает с него в следующий раз.
     * Требования:
     *      suspend fun <T> requestWithFallback( vararg sources: suspend () -> T ): T
     *      Принимает список suspend-функций (источников данных)
     *      Запрашивает их по порядку, пока один из них не вернёт результат (не выбросит исключение)
     *      Если источник выбросил исключение — переходим к следующему
     *      Если все источники выбросили исключение — пробрасываем последнее
     *      Запоминает успешный источник и в следующий раз начинает опрос именно с него
     *      Должна быть потокобезопасной (можно использовать AtomicReference или synchronized)
     */
    class RequestWithFallback<T>() {

        // function of last successful data source
        var firstSuccessful = AtomicReference<(suspend () -> T)?>(null)
            private set

        // List of fake data sources generator
        fun dataSources(count: Int): List<suspend () -> T> {
            return List(count) {
                suspend {
                    val value = (1..10).random()
                    if (value < 6) {
                        throw RuntimeException("$TAG Data source failure.")
                    } else {
                        (1..10).random() as T
                    }
                }
            }
        }

        /**
         * Returns first function with non-error response
         */
        suspend fun requestWithFallback(vararg sources: suspend () -> T): suspend () -> T {
            var lastException: Exception? = null
            var pointer: suspend () -> T = suspend { Any() as T }
            try {
                firstSuccessful.get()?.invoke()
                return firstSuccessful as suspend () -> T
            } catch (_: java.lang.Exception) {
                for (source in sources) {
                    try {
                        source()
                        firstSuccessful.set(source)
                        Log.i(TAG, "${pointer.hashCode()} successful")
                        return pointer
                    } catch (ex: Exception) {
                        Log.i(TAG, "${pointer.hashCode()} failing")
                        lastException = ex
                    }
                }
            }
            return firstSuccessful.get() ?: throw lastException as Exception
        }

        //  Когда подаётся новый список функций, надо объединять его с firstSuccessful. И
        //  получается что функция из firstSuccessful с прошлого прогона так остаётся успешной всегда
        //  и всегда в последующих прогонах будет выбираться именно она. По сути надо делать чтобы
        //  со временем она становилась не работающей, создавая видимость будто сервер со временем
        //  перестал отвечать.
    }

    // Пока без потокобезопасности
    class RequestWithFallback2<T>() {

        lateinit var result: SourceResult

        suspend fun requestWithFallback(vararg sources: suspend () -> T): SourceResult {
            if (::result.isInitialized) {
                when (result) {
                    is SourceResult.Success<*> -> {
                        (result as SourceResult.Success<*>).source.invoke()
                        Log.i(TAG, "Task10: Previous source invocation")
                    }

                    is SourceResult.Failure -> {
                        val error = (result as SourceResult.Failure).throwable
                        Log.i(TAG, "Task10: Previous source has had an error: $error")
                        result = SourceResult.Failure(error)
                    }
                }
            } else {
                result = getNextServerSource(*sources)
            }
            return result
        }

        private suspend fun getNextServerSource(vararg sources: suspend () -> T): SourceResult {
            var result: SourceResult = SourceResult.Success(sources[0])
            for (source in sources) {
                try {
                    result = SourceResult.Success(source)
                    source.invoke()
                    Log.i(TAG, "Task10: $source ran successfully. ${sources.indexOf(source)}")
                    break
                } catch (throwable: Throwable) {
                    if (throwable is CancellationException) throw throwable
                    Log.e(TAG, "Task10: $throwable")
                    result = SourceResult.Failure(throwable)
                }
            }
            return result
        }

        fun dataSources(count: Int): List<suspend () -> T> {
            return List(count) {
                suspend {
                    val value = (1..10).random()
                    if (value < 6) {
                        throw RuntimeException("$TAG Data source failure.")
                    } else {
                        (1..10).random() as T
                    }
                }
            }
        }
    }

    sealed class SourceResult {
        data class Success<T>(val source: (suspend () -> T)) : SourceResult()
        data class Failure(val throwable: Throwable) : SourceResult()
    }
    //endregion task10

    //-----------

    //region task1
    /**
     * **Запуск параллельных запросов**
     * Есть 3 API-запроса. Нужно выполнить их параллельно, дождаться всех результатов и объединить
     * в один объект. Один из запросов может упасть — обработать эту ситуацию.
     *
     * Поместил результаты в список. Возможно надо в отдельную модель данных.
     */
    // Не нужно передавать скоуп, тк каждый Deferred уже запущен в своём скоупе
    suspend fun <T> commonResult(vararg deferreds: Deferred<T>): List<T> {
        val valuesList = mutableListOf<T>()
        runCatching {
            valuesList.addAll(
                deferreds.toList().awaitAll()
            ) // More simple way - awaitAll(*deferreds)
        }.onFailure { throwable ->
            Log.e(TAG, throwable.cause.toString())
            if (throwable is CancellationException) {
                throw throwable
            }
        }.onSuccess {
            Log.i(TAG, "Task1: All $deferreds completed successfully")
        }
        return valuesList.toImmutableList()
    }

    /**
     * Deepseek's implementation
     */
    suspend fun <T> commonResult2(vararg deferreds: Deferred<T>): List<T> {
        return runCatching {
            deferreds.mapIndexed { index, deferred ->
                runCatching {
                    deferred.await()
                }.onFailure { throwable ->
                    Log.e(TAG, "Deferred #$index failed", throwable)
                }.getOrNull()
            }.filterNotNull()
        }.onFailure { throwable ->
            Log.e(TAG, "commonResult failed", throwable)
            if (throwable is CancellationException) throw throwable
        }.onSuccess { results ->
            Log.i(TAG, "Successfully completed ${results.size}/${deferreds.size} tasks")
        }.getOrThrow()
    }

    private fun getData1(scope: CoroutineScope) =
        scope.async {
            val delayValue = Random.nextLong(1000) + 1000
            delay(delayValue)
            Log.i(TAG, "Task1: getData1 finished")
            return@async delayValue
        }

    private fun getData2(scope: CoroutineScope) =
        scope.async {
            val delayValue = Random.nextLong(1000) + 1000
            delay(delayValue)
            Log.i(TAG, "Task1: getData2 finished")
            return@async delayValue.toString()
        }

    private fun getData3(scope: CoroutineScope) =
        scope.async {
            val delayValue = Random.nextLong(1000) + 1000
            delay(delayValue)
            Log.i(TAG, "Task1: getData3 finished")
            return@async delayValue.toInt().toChar()
        }

    suspend fun checkTask1() {
        val scope = CoroutineScope(Dispatchers.IO)
        val list = commonResult(
            getData1(scope),
            getData2(scope),
            getData3(scope),
        )
        Log.i(TAG, "Task1: $list")
    }
    //endregion task1

    //region task2
    /*
     * **Таймаут операции**
     * Запрос к серверу должен завершиться за 3 секунды. Если нет — отменить его и показать ошибку
     * пользователю.
     */
    // Изначальный вариант. TODO Проанализировать что здесь не так, исправить и сравнить с окончательным вариантом ниже
    fun requestWithTimeout(scope: CoroutineScope, job: Job, timeout: Long) {
        runCatching {
            scope.launch {
                withTimeout(timeout) {
                    job.join()
                }
            }
        }.onFailure { throwable ->
            if (throwable is CancellationException) throw throwable
            Log.e(TAG, "Task2: Error occurred: $throwable")
            job.cancel()
        }.onSuccess { value ->
            Log.i(TAG, "Task2: Job finished successfully with $value")
        }
    }

    suspend fun awaitWithTimeout(job: Job, timeout: Long) {
        runCatching {
            withTimeout(timeout) {
                job.join()
            }
        }.onFailure { throwable ->
            when (throwable) {
                is CancellationException -> {
                    if (throwable is TimeoutCancellationException) {
                        Log.i(TAG, "Task2: Task has been cancelled by timeout")
                    } else {
                        Log.i(TAG, "Task2: Task has been cancelled")
                    }
                    throw throwable  // Не подавляем отмену
                }

                else -> {
                    Log.e(TAG, "Task2: Error occurred", throwable)
                }
            }
        }.onSuccess { value ->
            Log.i(TAG, "Task2: Job finished successfully with $value")
        }
    }
    //endregion task2

    //region task3
    /**
     * **Обработка ошибок**
     * В цепочке из 3 последовательных запросов (зависимых друг от друга) обработать ошибку
     * на любом этапе и показать соответствующее сообщение.
     */
    // Невозможно передавать список лямбд, у которого каждая лямбд будет
    // принимать разные типы и возвращать разные, ведь в таком случае принимающий тип будет всегда
    // будет один и тот же. Аналогично для возвращаемого.
    // TODO Обдумать что может быть не так и сравнить с исправленным примером далее.
    suspend fun requestsQueue(
        request1: (String) -> Int,
        request2: (Int) -> Long,
        request3: (Long) -> String
    ): String {
        var request1Response: Int = Int.MIN_VALUE
        var request2Response: Long = Long.MIN_VALUE
        var request3Response: String = ""
        return coroutineScope {
            runCatching {
                request1Response = request1.invoke("")
            }.onFailure { action ->
                Log.e(TAG, "Task3: Error occurred in request1: $action")
                if (action is CancellationException) throw action
            }.onSuccess {
                Log.i(TAG, "Task3: Request2 succeeded with response $it")
            }

            runCatching {
                request2Response = request2.invoke(request1Response)
            }.onFailure { action ->
                Log.e(TAG, "Task3: Error occurred in request2: $action")
                if (action is CancellationException) throw action
            }.onSuccess {
                Log.i(TAG, "Task3: Request2 succeeded with response $it")
            }

            runCatching {
                request3Response = request3.invoke(request2Response)
            }.onFailure { action ->
                Log.e(TAG, "Task3: Error occurred in request3: $action")
                if (action is CancellationException) throw action
            }.onSuccess {
                Log.i(TAG, "Task3: Request3 succeeded with response $it")
            }
            return@coroutineScope request3Response
        }
    }

    // Поскольку это запросы, то всё таки верно использовать именно корутины, так как корутины
    // асинхронно ждут результат не блокируя поток. (В этом случае вернее суспенд блоки)
    suspend fun executeChain(
        request1: suspend () -> Int,
        request2: suspend (Int) -> Long,
        request3: suspend (Long) -> String
    ): String {
        var request1Response: Int = Int.MIN_VALUE
        var request2Response: Long = Long.MIN_VALUE
        var request3Response: String = ""
        runCatching {
            request1.invoke()
        }.onFailure { action ->
            Log.e(TAG, "Task3: Error occurred in request1: $action")
            throw action // Rethrow any exception, not just cancellation one, so that any error could stop following tasks
        }.onSuccess { result ->
            request1Response = result
            Log.i(TAG, "Task3: Request1 succeeded with response $result")
        }

        runCatching {
            request2.invoke(request1Response)
        }.onFailure { action ->
            Log.e(TAG, "Task3: Error occurred in request2: $action")
            throw action // Rethrow any exception, not just cancellation one, so that any error could stop following tasks
        }.onSuccess { result ->
            request2Response = result
            Log.i(TAG, "Task3: Request2 succeeded with response $result")
        }

        runCatching {
            request3.invoke(request2Response)
        }.onFailure { action ->
            Log.e(TAG, "Task3: Error occurred in request3: $action")
            throw action // Rethrow any exception, not just cancellation one, so that any error could stop following tasks
        }.onSuccess { result ->
            request3Response = result
            Log.i(TAG, "Task3: Request3 succeeded with response $result")
        }
        return request3Response
    }

    fun executeChainTest() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = executeChain(
                request1 = {
                    delay(1000)
                    Log.i(TAG, "Task3: Request1 executing")
                    42
                },
                request2 = { input: Int ->
                    delay(1000)
                    Log.i(TAG, "Task3: Request2 executing with input: $input")
                    input.toLong() * 2
                },
                request3 = { input: Long ->
                    delay(1000)
                    Log.i(TAG, "Task3: Request3 executing with input: $input")
                    "Result: $input"
                }
            )
            Log.i(TAG, "Task3: result: $result")
        }
    }
    //endregion task3

    //region task4
    /**
     * **Поиск с дебаунсом**
     * Пользователь печатает в поисковую строку. Отправлять запрос только через 500 мс бездействия
     * и не дублировать одинаковые запросы.
     * TODO Проверить что не так, исправить и сравнить с верным кодом ниже.
     */
    @FlowPreview
    suspend fun textType(flow: Flow<Char>, debounceMs: Long) {
        flow
            .distinctUntilChanged()
            .debounce(debounceMs)
            .flowOn(Dispatchers.IO)
            .collect { value ->
                Log.i(TAG, "typed string $value")
            }
    }

    // Нужен тип String, тк мы шлём не символы отдельно а символы набором, то есть строки.
    @ExperimentalCoroutinesApi
    @FlowPreview
    fun searchFlow(flow: Flow<String>, debounceMs: Long = 500L) =
        flow
            .debounce(debounceMs)
            .distinctUntilChanged()
            .filter { it.length >= 2 }  // Минимальная длина для поиска
            .flatMapLatest { query ->  // Отменяем предыдущий запрос
                flow {
                    emit("Searching for: $query")
                    delay(200)  // Симуляция сетевого запроса
                    emit("Result for: $query")
                }.catch { e ->
                    emit("Error: ${e.message}")
                }
            }
            .flowOn(Dispatchers.IO)
    // Не коллектим флоу здесь же, а возвращаем его.
    //endregion task4

    //region task5
    /**
     * **Retry с ограничением**
     * Запрос к сети может упасть. Сделать 3 попытки с задержкой перед повторной отправкой.
     */
    suspend fun requestWithRetries(
        count: Int = 3,
        initialDelay: Long = 1000L,
        deferred: Deferred<Long>
    ): Long {
        var currentAttempt = 0
        var delay = initialDelay
        while (currentAttempt < count) {
            try {
                return deferred.await()
            } catch (throwable: Throwable) {
                Log.e(TAG, throwable.cause.toString())
                currentAttempt++
                delay(delay)
                delay *= 2
            }
        }
        throw IllegalStateException("$TAG Task5: Request failed with attempts = $count")
    }

    // 1. После первой ошибки, повторный запуск упавшей корутины точне не будет успешным.
    // 2. Нет проверки на отмену, CancellationException нужно пробрасывать, не пытаясь повторить
    // 3. Фиксированный тип Long, лучше использовать дженерики <T>
    // 4. Нет обработки разных ошибок, сетевые ошибки стоит повторять, а ошибки аутентификации — нет

    // Исправленная версия
    suspend fun <T> runWithRetries2(
        attempts: Int = 3,
        initialDelay: Long = 1000L,
        block: suspend () -> T
    ): T {
        var currentAttempt = 0
        var delay = initialDelay
        while (currentAttempt < attempts) {
            try {
                return block()  // this block is executed in fact
            } catch (exception: CancellationException) {
                Log.e(TAG, exception.cause.toString())
                throw exception
            } catch (throwable: Throwable) {
                Log.e(TAG, throwable.cause.toString())
                val isRetryable = isRetryableError(throwable)
                if (!isRetryable || currentAttempt == attempts - 1) {
                    // ❌ Не повторяем, если ошибка не retryable или это последняя попытка
                    Log.e(TAG, "Request failed after ${currentAttempt + 1} attempts", throwable)
                    throw throwable
                }
                currentAttempt++
                delay(delay)
                delay *= 2
            }
        }
        throw IllegalStateException("$TAG Task5: Request failed with attempts = $attempts")
    }

    // Определяем, можно ли повторить запрос
    private fun isRetryableError(throwable: Throwable): Boolean {
        return when (throwable) {
            // ✅ Сетевые ошибки — повторяем
            is SocketTimeoutException -> true
            is UnknownHostException -> true
            is IOException -> true

            // ❌ Ошибки клиента (4xx) или сервера (5xx) — обычно не повторяем
            is HttpException -> throwable.code() !in 500..599

            // ❌ Ошибки аутентификации — не повторяем
//            is UnauthorizedException -> false

            // ❌ Остальные — по умолчанию не повторяем
            else -> false
        }
    }

    fun testTask5() {
        CoroutineScope(Dispatchers.IO).launch {
            runWithRetries2(
                attempts = 3,
                1000L,
                suspend {
                    throw SocketTimeoutException()
                }
            )
        }
    }
    //endregion task5

    //region task6
    /**
     * **Тяжелые вычисления с прогрессом**
     * Обработка большого списка данных (например, 10 000 элементов) с отображением прогресса в UI.
     * Вычисления в `Dispatchers.Default`, обновление прогресса в `Dispatchers.Main`.
     */
    fun computeWithProgress(
        scope: CoroutineScope,
        dataToProcess: List<Int>,
        updateUi: (String) -> Unit
    ) {
        scope.launch {
            for (i in 0..dataToProcess.size) {
                if (i % (dataToProcess.size / 10) == 0) {
                    if (!isActive) return@launch
                    withContext(Dispatchers.Main) {
                        updateUi("${(i.toFloat() / dataToProcess.size * 100).toInt()}% processed out of ${dataToProcess.size}")
                    }
                }
            }
            withContext(Dispatchers.Main) {
                updateUi("Processing complete.")
            }
        }
    }
    // Проблемы в коде:
    //❌ computeWithProgress не suspend	Нельзя дождаться завершения
    //❌ Нет возвращаемого результата	Непонятно, что делать с обработанными данными
    //❌ dataToProcess.size в цикле	Ошибка на 1 (индекс выходит за пределы)
    //❌ Нет реальной обработки данных	Просто цикл без вычислений
    //❌ updateUi как callback	Для UI лучше использовать StateFlow
    //❌ Нет обработки ошибок	Если обработка упадёт, UI не узнает
    //❌ Нет withContext(Dispatchers.Default)	Весь код выполняется в Dispatchers.Main (из-за launch)

    fun computeWithProgress2(
        dataToProcess: List<Int>,
//        uiState: MutableStateFlow<String>,  // SRP breaking. Function knows about UI. Better return this flow.
        timeout: Long = 60000L
    ): Flow<ProgressState> = flow {
        val processedList = mutableListOf<Int>()
        try {
            withTimeout(timeout) {
                val step = maxOf(1, dataToProcess.size / 10)
                for (i in dataToProcess.indices) {
                    processedList.add((dataToProcess[i] + 10) * 2)  // Presumable calculations
                    ensureActive()
                    if (i % step == 0 || i == dataToProcess.size - 1) {
                        if (i % 100 == 0) yield()  // Даём шанс другим корутинам поработать каждые 100 итераций
                        val currentProcessed = (i.toFloat() / dataToProcess.size * 100).toInt()
                        // emit switches to main thread itself, no need in withContext(Dispatchers.Main))
                        emit(ProgressState.Progress(currentProcessed, dataToProcess.size))
                    }
                }
                emit(ProgressState.Success(processedList.toImmutableList()))
            }
        } catch (exception: CancellationException) {
            emit(ProgressState.Error(exception.message ?: exception.cause.toString()))
            throw exception
        } catch (exception: Exception) {
            emit(ProgressState.Error(exception.message.toString()))
        }
    }.flowOn(Dispatchers.Default)

    sealed class ProgressState {
        data class Progress(val current: Int, val total: Int) : ProgressState()
        data class Success(val data: List<Int>) : ProgressState()
        data class Error(val message: String) : ProgressState()
    }
    //endregion task6

    //region task7
    /**
     * **Конкурентная загрузка с ограничением**
     * Есть 20 URL для загрузки. Нужно загружать не более 5 одновременно, используя корутины.
     */
    fun multipleDownload(
        scope: CoroutineScope,
        urls: List<URL>,
        multiplicity: Int = 5
    ) {
        val semaphore = Semaphore(multiplicity)
        urls.forEach { url ->
            scope.launch {
                semaphore.withPermit {
                    Log.i(TAG, "$url downloading began")
                    delay(2000)
                    Log.i(TAG, "$url downloading complete")
                }
            }
        }
        Log.i(TAG, "All downloads complete")
    }
    //endregion task7

    //region task8
    /**
     * **Channel как очередь**
     * Реализовать продюсера-консьюмера через `Channel`, где продюсер генерирует события быстрее,
     * чем консьюмер успевает обрабатывать (решить проблему с буфером).
     *
     * Примечание: Продюсер и консьюмер - это один и тот же канал, как я выяснил у ИИ
     */
    fun overflownChannel(scope: CoroutineScope, channel: Channel<Int>) {
        scope.launch {
            while (isActive) {
                delay(50)
                val sentValue = Random.nextInt(100)
                val start = System.currentTimeMillis()
                channel.send(sentValue)
                val duration = System.currentTimeMillis() - start
                if (duration > 10) {
                    Log.w(TAG, "⚠️ send() занял ${duration}ms — буфер был полон!")
                }
                Log.i(TAG, "Producer sent: $sentValue")
            }
        }
        scope.launch {
            while (isActive) {
                delay(200)
                Log.i(TAG, "Consumer received: ${channel.receive()}")
            }
        }
    }

    // Run channel test without any buffer. Data reception will stumble right away.
    fun overflownChannelTest() =
        overflownChannel(CoroutineScope(Dispatchers.IO), Channel<Int>())

    // Run channel test with buffer. Data reception will stumble in several seconds.
    fun overflownChannelTestBuffered() =
        overflownChannel(
            CoroutineScope(Dispatchers.IO),
            Channel<Int>(Channel.BUFFERED)
        )

    // Run channel test with buffer. Data reception won't stumble, since when buffer is full, it ignores an oldest entry..
    fun overflownChannelTestBufferedDropOldest() =
        overflownChannel(
            CoroutineScope(Dispatchers.IO),
            Channel<Int>(Channel.BUFFERED, BufferOverflow.DROP_OLDEST)
        )

    // Run channel test with buffer. Data reception won't stumble, since when buffer is full, it ignores an oldest entry..
    fun overflownChannelTestBufferedDropLatest() =
        overflownChannel(
            CoroutineScope(Dispatchers.IO),
            Channel<Int>(Channel.BUFFERED, BufferOverflow.DROP_LATEST)
        )

    // Run channel test with unlimited size buffer.  Data reception won't stumble until whole app RAM runs out.
    fun overflownChannelTestUnlimited() =
        overflownChannel(
            CoroutineScope(Dispatchers.IO),
            Channel<Int>(Channel.UNLIMITED)
        )
    //endregion task8

    companion object {
        private const val TAG = "CoroutinesTasks"
    }
}