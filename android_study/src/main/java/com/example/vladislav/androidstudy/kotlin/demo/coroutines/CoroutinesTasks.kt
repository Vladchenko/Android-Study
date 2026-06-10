package com.example.vladislav.androidstudy.kotlin.demo.coroutines

import android.os.Build
import android.service.autofill.UserData
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
    fun task1(): Job {
        return CoroutineScope(Dispatchers.Default).launch {
            repeat(5) {
                delay(1000)
                Log.i(TAG, it.toString())
            }
        }
    }
    // endregion task1

    // region task2
    /**
     * Создай две корутины: одна должна выводить чётные числа от 2 до 10 каждые 2 секунды, вторая
     * — нечётные числа от 1 до 9 каждую секунду. Выводы обеих корутин должны происходить
     * одновременно, чередуя друг друга.
     *
     * Решил Гигачат.
     */
    fun task2() =
        CoroutineScope(Dispatchers.Default).launch {
            val evenNumbersJob = launch {
                var number = 2
                while (number <= 10) {
                    delay(2000L)
                    println("Even Number: $number")
                    number += 2
                }
            }

            val oddNumbersJob = launch {
                var number = 1
                while (number <= 9) {
                    delay(1000L)
                    println("Odd Number: $number")
                    number += 2
                }
            }

            // В Андроиде не обязательно дожидаться. Если результат нужен до выполнения кода после
            // корутин, то ожидание обязательно.
//            joinAll(evenNumbersJob, oddNumbersJob) // Ожидаем завершение обеих корутин
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

    fun task3_1(factorialNumber: Int, sumNumber: Int, scope: CoroutineScope) {
        scope.launch {
            val result = select {
                async { factorial(factorialNumber) }.onAwait { it }
                async { sumOfN(sumNumber) }.onAwait { it }
            }
            Log.i(TAG, result.toString())
        }
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
    suspend fun task4(listSize: List<Int>, timeoutMs: Long): List<Int> = coroutineScope {
        val processed = arrayListOf<Int>()
        val job = launch {
            // launch не пробрасывает TimeoutCancellationException, а async пробрасывает.
            // В случае с async, результат работы этой корутины не будет выведен, так как внешняя
            // корутина тоже отменится.
            withTimeout(timeoutMs) {
                for (item: Int in listSize) {
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
        itemTimeout: Long = 500L,
        totalTimeout: Long = 3000L
    ): List<Int> {
        val resultList = mutableListOf<Int>()
        Log.i(TAG, "Processing ${list.size} items with 3s timeout")
        runCatching {
            withTimeout(totalTimeout) {
                list.onEach { item ->   // buildList seems to be an option, nut LLM says it cannot pass a partial result (not full list in this case)
                    delay(itemTimeout)
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
    //endregion task4

    //region task5
    /**
     * Есть 10 задач, но одновременно могут выполняться только 3. Реализуйте обработку.
     */
    suspend fun task5() = coroutineScope {
        val semaphore = Semaphore(3)
        val activeTasks = AtomicInteger(0)
        val list = List(10) {
            launch { // Dispatchers.IO.limitedParallelism(3) - почему-то не работает
                semaphore.withPermit {
                    val concurrent = activeTasks.incrementAndGet()
                    delay(100)
                    Log.d(TAG, "Tasks running in parallel: $concurrent")
                    activeTasks.decrementAndGet()
                }
            }
        }
    }

    // !!! Do not understand why there is extra launch needed here
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
        return if (Random.nextInt() > 0.3) Source.CacheSource(listOf("fetchedFromCache")) else null
    }

    private suspend fun fetchFromLocalDb(): Source? {
        delay(400)
        // When using resources, one should use .use { // resource access } to close them correctly after usage
        return if (Random.nextInt() > 0.3) Source.DbSource(listOf("fetchFromLocalDb")) else null
    }

    private suspend fun fetchFromNetwork(): Source? {
        delay(800)
        // When using resources, one should use .use { // resource access } to close them correctly after usage
        return if (Random.nextInt() > 0.3) Source.NetworkSource(listOf("fetchFromNetwork")) else null
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
                if (throwable is CancellationException) throw throwable
                cachedDeferred.cancel()
                dbDeferred.cancel()
                networkDeferred.cancel()
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
// endregion task9

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
    //endregion task10

    companion object {
        private const val TAG = "CoroutinesTasks"
    }
}