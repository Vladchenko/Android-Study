package com.example.vladislav.androidstudy.kotlin.demo.coroutines

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.CoroutineContext

/**
 * ============================================================
 * КОРУТИНЫ (COROUTINES) - ПОЛНОЕ РУКОВОДСТВО
 * ============================================================
 *
 * Корутины - это легковесные потоки для асинхронного программирования.
 *
 * Основные понятия:
 * - suspend функция - может приостанавливать выполнение без блокировки потока
 * - CoroutineScope - область жизни корутины
 * - CoroutineContext - контекст выполнения (диспетчер + Job)
 * - Job - управление жизненным циклом
 * - Dispatcher - какой поток использует корутина
 *
 * ВАЖНО: Все скоупы должны быть привязаны к жизненному циклу!
 *
 * Зависимости:
 * implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1"
 * implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1"
 * ============================================================
 */
class CoroutinesTutorial {

    companion object {
        private const val TAG = "CoroutinesTutorial"
    }

    // ============================================================
    // 1️⃣ БАЗОВЫЕ ПОНЯТИЯ
    // ============================================================

    /**
     * 1.1. suspend функции - могут приостанавливаться
     * Могут вызывать другие suspend функции
     */
    private suspend fun doWork() {
        delay(1000) // suspend функция
        Log.i(TAG, "🔵 Работа выполнена")
    }

    /**
     * 1.2. Обычная функция НЕ может вызывать suspend функции
     * Должна быть вызвана из корутины
     */
    private fun regularFunction() {
        // ❌ delay(1000) // Ошибка! Нельзя вызывать suspend из обычной функции

        // ✅ Должна быть вызвана из корутины
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
        scope.launch {
            doWork()
        }
        // Осторожно: scope нужно будет закрыть!
    }

    /**
     * 1.3. CoroutineScope - область видимости корутин
     * ✅ ПРАВИЛЬНО: скоуп привязан к жизненному циклу
     */
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // В реальном проекте используйте:
    // - viewModelScope (в ViewModel)
    // - lifecycleScope (в Activity/Fragment)
    // - свой скоуп с явным управлением

    /**
     * 1.4. Dispatchers - где выполняется код
     *
     * Dispatchers.Main    - UI поток (Android)
     * Dispatchers.IO      - для IO операций (сеть, БД, файлы)
     * Dispatchers.Default - для CPU интенсивных задач
     * Dispatchers.Unconfined - не привязан к потоку (опасно, не используйте!)
     */
    private suspend fun dispatchersExample() {
        // ✅ Используем withContext для переключения контекста
        val result = withContext(Dispatchers.IO) {
            // Тяжёлая работа в IO потоке
            "Данные из сети"
        }

        // ✅ Автоматически возвращаемся в исходный контекст (Main)
        updateUI(result)
    }

    private fun updateUI(data: String) {
        Log.i(TAG, "📱 UI обновлён: $data")
    }


    // ============================================================
    // 2️⃣ ЗАПУСК КОРУТИН
    // ============================================================

    // ✅ Скоуп для всех примеров (в реальном проекте используйте viewModelScope/lifecycleScope)
    private val tutorialScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    /**
     * 2.1. launch - "запусти и забудь" (возвращает Job)
     * ✅ ПРАВИЛЬНО: используем сохранённый скоуп
     */
    private fun launchExample() {
        val job = tutorialScope.launch {
            delay(1000)
            Log.i(TAG, "🔵 launch выполнен")
        }

        // Можно отменить
        job.cancel()
    }

    /**
     * 2.2. async - запуск с возвратом результата (возвращает Deferred<T>)
     * ✅ ПРАВИЛЬНО: используем сохранённый скоуп
     *
     * ВАЖНО: async нужен только для параллельного выполнения!
     * Для одиночных задач используйте withContext
     */
    private suspend fun asyncExample(): String {
        // ✅ Используем сохранённый скоуп
        val deferred = tutorialScope.async(Dispatchers.IO) {
            delay(2000)
            "Результат работы"
        }
        return deferred.await()
    }

    /**
     * 2.3. Параллельное выполнение через async
     * ✅ Единственное место, где async действительно нужен!
     */
    private suspend fun parallelAsyncExample() {
        // ✅ Обе задачи выполняются параллельно в одном скоупе
        val deferred1 = tutorialScope.async(Dispatchers.IO) { fetchData1() }
        val deferred2 = tutorialScope.async(Dispatchers.IO) { fetchData2() }

        // Ждём оба результата
        val result1 = deferred1.await()
        val result2 = deferred2.await()

        Log.i(TAG, "📦 Результат 1: $result1, Результат 2: $result2")
    }

    private suspend fun fetchData1(): String {
        delay(1000)
        return "Data 1"
    }

    private suspend fun fetchData2(): String {
        delay(1500)
        return "Data 2"
    }

    /**
     * 2.4. withContext - переключение контекста (ПРЕДПОЧТИТЕЛЬНО)
     * ✅ Используйте для одиночных задач вместо async
     */
    private suspend fun withContextExample(): String {
        // ✅ Простое переключение контекста
        return withContext(Dispatchers.IO) {
            delay(1000)
            "Результат из IO"
        }
    }

    /**
     * 2.5. runBlocking - блокирует текущий поток (ТОЛЬКО ДЛЯ ТЕСТОВ!)
     * ❌ НИКОГДА не используйте в Android (блокирует UI)
     */
    private fun runBlockingExample() = runBlocking {
        // Только для тестов или main функции
        delay(1000)
        Log.i(TAG, "runBlocking выполнен")
    }


    // ============================================================
    // 3️⃣ ЖИЗНЕННЫЙ ЦИКЛ И ОТМЕНА
    // ============================================================

    /**
     * 3.1. Job - управление жизненным циклом
     * ✅ Правильно: job сохраняется и отменяется
     */
    private fun jobExample() {
        // ✅ Создаём скоуп для этого примера
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

        val job = scope.launch {
            repeat(100) { i ->
                delay(100)
                Log.i(TAG, "🔄 Итерация $i")
            }
        }

        // Отмена через 500ms
        scope.launch {
            delay(500)
            job.cancel()
            Log.i(TAG, "⛔ Job отменён")
        }

        // Не забываем закрыть скоуп!
        // scope.cancel() - когда больше не нужен
    }

    /**
     * 3.2. SupervisorJob - дети не отменяют друг друга
     * ✅ Правильно: ошибка в одном ребёнке не убивает других
     */
    private fun supervisorJobExample() {
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

        scope.launch {
            // Ребёнок 1 - падает с ошибкой
            delay(100)
            throw RuntimeException("💥 Ошибка в ребёнке 1")
        }

        scope.launch {
            // Ребёнок 2 - НЕ будет отменён из-за ошибки ребёнка 1
            delay(500)
            Log.i(TAG, "✅ Ребёнок 2 всё ещё работает")
        }
    }

    /**
     * 3.3. Отмена через ensureActive()
     * ✅ Правильно: проверяем состояние корутины
     */
    private suspend fun cancellationCheckExample() {
        repeat(1000) { i ->
            // ✅ Проверяем, не отменена ли корутина
            currentCoroutineContext().ensureActive() // Выбросит CancellationException если отменена

            // ИЛИ явная проверка
            if (!currentCoroutineContext().isActive) {
                Log.i(TAG, "⛔ Корутина отменена, выходим")
                return
            }

            Log.i(TAG, "🔄 Работаем: $i")
            delay(100)
        }
    }

    /**
     * 3.4. Отмена через withTimeout
     * ✅ Правильно: ограничиваем время выполнения
     */
    private suspend fun timeoutExample() {
        try {
            withTimeout(1000) { // Таймаут 1 секунда
                while (true) {
                    delay(100)
                    Log.i(TAG, "🔄 Работаю...")
                }
            }
        } catch (e: TimeoutCancellationException) {
            Log.i(TAG, "⏰ Таймаут! Работа остановлена")
        }
    }


    // ============================================================
    // 4️⃣ ОБРАБОТКА ОШИБОК
    // ============================================================

    /**
     * 4.1. try-catch внутри корутины
     * ✅ Правильно: локальная обработка ошибок
     */
    private fun tryCatchExample() {
        tutorialScope.launch {
            try {
                riskyOperation()
            } catch (e: Exception) {
                Log.e(TAG, "❌ Ошибка: ${e.message}")
            }
        }
    }

    private suspend fun riskyOperation() {
        throw RuntimeException("Тестовая ошибка")
    }

    /**
     * 4.2. CoroutineExceptionHandler - глобальный обработчик ошибок
     * ✅ Правильно: для обработки ошибок на уровне скоупа
     */
    private fun exceptionHandlerExample() {
        // ✅ Создаём обработчик
        val handler = CoroutineExceptionHandler { _, throwable ->
            Log.e(TAG, "❌ Глобальная ошибка: ${throwable.message}")
        }

        // ✅ Добавляем в скоуп
        val scope = CoroutineScope(SupervisorJob() + handler + Dispatchers.Main)

        scope.launch {
            throw RuntimeException("Ошибка в корутине")
        }
    }

    /**
     * 4.3. Обработка ошибок в async
     * ✅ Правильно: ошибка приходит в await()
     */
    private suspend fun asyncErrorHandling() {
        val deferred = tutorialScope.async(Dispatchers.IO) {
            throw RuntimeException("💥 Ошибка в async")
            "Результат"
        }

        try {
            val result = deferred.await() // ❌ Ошибка выбросится здесь!
        } catch (e: Exception) {
            Log.e(TAG, "❌ Поймали ошибку из async: ${e.message}")
        }
    }


    // ============================================================
    // 5️⃣ COROUTINECONTEXT
    // ============================================================

    /**
     * 5.1. Комбинирование контекстов
     * ✅ Правильно: объединяем элементы контекста
     */
    private fun contextComposition() {
        // ✅ Job + Dispatcher
        val context = Job() + Dispatchers.IO

        // ✅ SupervisorJob + Dispatcher + имя
        val namedContext = SupervisorJob() + Dispatchers.Main + CoroutineName("MyCoroutine")

        val scope = CoroutineScope(namedContext)
    }

    /**
     * 5.2. CoroutineName - для отладки
     * ✅ Правильно: помогает в логах
     */
    private fun namedCoroutineExample() {
        val scope = CoroutineScope(Dispatchers.Main + CoroutineName("NetworkRequest"))

        scope.launch {
            val name = coroutineContext[CoroutineName]?.name
            Log.i(TAG, "🔵 Выполняется в: $name")
        }
    }


    // ============================================================
    // 6️⃣ АСИНХРОННЫЕ ПАТТЕРНЫ
    // ============================================================

    /**
     * 6.1. Последовательное выполнение (один за другим)
     * ✅ Правильно: данные нужны по порядку
     */
    private suspend fun sequentialExample() {
        val result1 = fetchData1() // Ждём
        val result2 = fetchData2() // Затем ждём второе
        val combined = result1 + result2
        Log.i(TAG, "📦 Sequential: $combined")
    }

    /**
     * 6.2. Параллельное выполнение (одновременно)
     * ✅ Правильно: задачи независимы
     */
    private suspend fun parallelExample() {
        val deferred1 = tutorialScope.async(Dispatchers.IO) { fetchData1() }
        val deferred2 = tutorialScope.async(Dispatchers.IO) { fetchData2() }

        // Оба выполняются параллельно
        val result1 = deferred1.await()
        val result2 = deferred2.await()
        Log.i(TAG, "📦 Parallel: $result1, $result2")
    }

    /**
     * 6.3. Все результаты (когда все завершатся)
     * ✅ Правильно: ждём все задачи
     */
    private suspend fun allResultsExample() {
        val results = listOf(
            tutorialScope.async(Dispatchers.IO) { fetchData1() },
            tutorialScope.async(Dispatchers.IO) { fetchData2() },
            tutorialScope.async(Dispatchers.IO) { fetchData3() }
        ).awaitAll() // Ждём все

        Log.i(TAG, "📦 Все результаты: $results")
    }

    private suspend fun fetchData3(): String {
        delay(800)
        return "Data 3"
    }


    // ============================================================
    // 7️⃣ СТРУКТУРИРОВАННАЯ КОНКУРЕНТНОСТЬ
    // ============================================================

    /**
     * 7.1. coroutineScope - скоуп с автоматическим ожиданием детей
     * ✅ Правильно: все дети должны завершиться
     */
    private suspend fun coroutineScopeExample() = coroutineScope {
        // ✅ Все дети должны завершиться, прежде чем функция вернётся
        val job1 = launch { fetchData1() }
        val job2 = launch { fetchData2() }

        Log.i(TAG, "✅ Все дети выполнены")
    }

    /**
     * 7.2. supervisorScope - дети не влияют друг на друга
     * ✅ Правильно: ошибка в одном не убивает других
     */
    private suspend fun supervisorScopeExample() = supervisorScope {
        launch {
            delay(100)
            throw RuntimeException("💥 Ошибка")
        }
        launch {
            delay(500)
            Log.i(TAG, "✅ Всё ещё работаю")
        }
    }

    /**
     * 7.3. Правильное создание скоупов
     * ✅ Правильно: скоуп привязан к жизненному циклу
     */
    private fun properScopeCreation() {
        // ✅ ХОРОШО: скоуп с явным управлением
        class MyViewModel {
            private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

            fun loadData() {
                viewModelScope.launch {
                    // Работает пока жив скоуп
                }
            }

            fun onCleared() {
                viewModelScope.cancel() // ✅ Отменяем всё
            }
        }

        // ❌ ПЛОХО: GlobalScope
        class BadExample {
            fun doWork() {
                GlobalScope.launch { // ❌ Опасно! Живёт вечно
                    // Может вызвать утечку памяти
                }
            }
        }
    }


    // ============================================================
    // 8️⃣ ПРАКТИЧЕСКИЕ КЕЙСЫ ДЛЯ ANDROID
    // ============================================================

    /**
     * 8.1. Выполнение сетевого запроса
     * ✅ Правильно: с переключением контекста
     */
    private suspend fun fetchUser(id: String): User {
        return withContext(Dispatchers.IO) {
            // Имитация сетевого запроса
            delay(1000)
            User(id, "User $id")
        }
    }

    /**
     * 8.2. Debounce (анти-спам)
     * ✅ Используем SharedFlow для событий
     */
    @FlowPreview
    private class SearchViewModel {
        // ✅ SharedFlow для событий поиска
        private val _searchQuery = MutableSharedFlow<String>()
        private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

        init {
            scope.launch {
                _searchQuery
                    .debounce(300) // Ждём 300ms без ввода
                    .collect { query ->
                        performSearch(query)
                    }
            }
        }

        fun onSearchQueryChanged(query: String) {
            _searchQuery.tryEmit(query) // Отправляем запрос
        }

        private suspend fun performSearch(query: String) {
            if (query.isNotBlank()) {
                Log.i(TAG, "🔍 Поиск: $query")
                // Здесь делаем запрос
            }
        }

        fun onCleared() {
            scope.cancel()
        }
    }

    /**
     * 8.3. Retry (повтор при ошибке)
     * ✅ Правильно: с экспоненциальной задержкой
     */
    private suspend fun fetchWithRetry() {
        var retries = 0
        var success = false

        while (!success && retries < 3) {
            try {
                fetchData()
                success = true
            } catch (e: Exception) {
                retries++
                val delayMs = 1000L * retries // Экспоненциальная задержка
                Log.i(TAG, "🔄 Повтор через ${delayMs}ms (попытка $retries)")
                delay(delayMs)
            }
        }
    }

    private suspend fun fetchData() {
        // Имитация сетевого запроса
        delay(500)
        if (System.currentTimeMillis() % 2 == 0L) {
            throw RuntimeException("Случайная ошибка")
        }
    }


    // ============================================================
    // 9️⃣ CHANNEL И MUTEX
    // ============================================================

    /**
     * 9.1. Channel - канал для передачи данных между корутинами
     * ✅ Правильно: для обмена данными
     */
    private suspend fun channelExample() {
        val channel = Channel<Int>(capacity = 10)

        // ✅ Отправитель
        val producer = tutorialScope.launch {
            for (i in 1..10) {
                delay(500)
                channel.send(i) // Отправляем
                Log.i(TAG, "📤 Отправлено: $i")
            }
            channel.close() // Закрываем канал
        }

        // ✅ Получатель
        val consumer = tutorialScope.launch {
            for (value in channel) { // Читаем пока канал открыт
                Log.i(TAG, "📥 Получено: $value")
            }
        }
    }

    /**
     * 9.2. Mutex - защита от конкурентного доступа
     * ✅ Правильно: для потокобезопасного доступа
     */
    private suspend fun mutexExample() {
        val mutex = Mutex()
        val counter = AtomicInteger(0)

        // ✅ Запускаем много корутин с защитой
        val jobs = List(100) {
            tutorialScope.launch {
                repeat(1000) {
                    mutex.withLock { // Только одна корутина за раз
                        counter.incrementAndGet()
                    }
                }
            }
        }

        jobs.forEach { it.join() }
        Log.i(TAG, "📊 Счётчик: ${counter.get()}")
    }


    // ============================================================
    // 🔟 ЛУЧШИЕ ПРАКТИКИ
    // ============================================================

    /**
     * 10.1. НИКОГДА не используйте GlobalScope
     */
    // ❌ ПЛОХО
    class BadClass {
        fun doWork() {
            GlobalScope.launch { // ❌ Живёт вечно!
                // Утечка памяти
            }
        }
    }

    // ✅ ХОРОШО
    class GoodClass : CoroutineScope {
        private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

        override val coroutineContext: CoroutineContext
            get() = scope.coroutineContext

        fun doWork() {
            launch {
                // Привязан к жизненному циклу
            }
        }

        fun destroy() {
            scope.cancel()
        }
    }

    /**
     * 10.2. Всегда используйте обработку ошибок
     */
    private suspend fun goodErrorHandling() {
        try {
            riskyOperation()
        } catch (e: Exception) {
            Log.e(TAG, "❌ Ошибка", e)
        }
    }

    /**
     * 10.3. Правильно выбирайте Dispatcher
     */
    private suspend fun chooseRightDispatcher() {
        // ✅ UI работа → Dispatchers.Main
        withContext(Dispatchers.Main) {
            updateUI("Результат")
        }

        // ✅ Сеть/БД → Dispatchers.IO
        withContext(Dispatchers.IO) {
            // networkCall()
        }

        // ✅ CPU интенсивные → Dispatchers.Default
        withContext(Dispatchers.Default) {
            // heavyCalculation()
        }
    }


    // ============================================================
    // 1️⃣1️⃣ БОНУС: ПОЛНЫЙ ПРИМЕР В VIEWMODEL
    // ============================================================

    /**
     * Полный пример использования корутин в ViewModel
     * ✅ Все скоупы привязаны к жизненному циклу
     * ✅ Правильная обработка ошибок
     * ✅ Структурированная конкурентность
     */
    class MainViewModel : CoroutineScope {
        // ✅ Скоуп привязан к жизненному циклу ViewModel
        private val job = SupervisorJob()
        override val coroutineContext: CoroutineContext
            get() = job + Dispatchers.Main

        private val _state = MutableStateFlow<UiState>(UiState.Loading)
        val state: StateFlow<UiState> = _state

        private val _toasts = MutableSharedFlow<String>()
        val toasts: SharedFlow<String> = _toasts

        private val repository = Repository()

        fun loadData() {
            // ✅ Используем скоуп ViewModel
            launch {
                _state.value = UiState.Loading

                try {
                    // ✅ Таймаут и переключение контекста
                    val data = withTimeout(5000) {
                        withContext(Dispatchers.IO) {
                            repository.fetchData()
                        }
                    }

                    _state.value = UiState.Success(data)

                } catch (e: TimeoutCancellationException) {
                    _state.value = UiState.Error("⏰ Таймаут запроса")

                } catch (e: Exception) {
                    _state.value = UiState.Error("❌ Ошибка: ${e.message}")

                } finally {
                    Log.i(TAG, "✅ Загрузка завершена")
                }
            }
        }

        fun onButtonClick() {
            // ✅ Отправка события
            launch {
                _toasts.emit("🖱️ Кнопка нажата!")
            }
        }

        fun onCleared() {
            // ✅ Отменяем всё при уничтожении
            job.cancel()
        }
    }

    data class User(val id: String, val name: String)

//    data class UiState(
//        val isLoading: Boolean = false,
//        val data: List<String>? = null,
//        val error: String? = null
//    ) {
//        companion object {
//            val Loading = UiState(isLoading = true)
//            fun Success(data: List<String>) = UiState(data = data)
//            fun Error(message: String) = UiState(error = message)
//        }
//    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val data: List<String>) : UiState()
        data class Error(val message: String) : UiState()
    }

    class Repository {
        suspend fun fetchData(): List<String> {
            // Имитация сетевого запроса
            delay(1000)
            return listOf("Item 1", "Item 2", "Item 3")
        }
    }


    // ============================================================
    // 📊 ШПАРГАЛКА ПО КОРУТИНАМ
    // ============================================================

    /**
     * ============================================================
     * ШПАРГАЛКА ПО КОРУТИНАМ (ВСЕГДА ДЕРЖИТЕ ПОД РУКОЙ!)
     * ============================================================
     *
     * 🔹 Создание скоупа (ВСЕГДА с привязкой к жизненному циклу):
     * ✅ val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
     * ✅ viewModelScope (в ViewModel)
     * ✅ lifecycleScope (в Activity/Fragment)
     * ❌ GlobalScope.launch { } - НИКОГДА!
     *
     * 🔹 Запуск:
     * ✅ scope.launch { }        // "Запусти и забудь"
     * ✅ scope.async { }.await() // С возвратом результата (только для параллельных задач!)
     * ✅ withContext(Dispatchers.IO) { } // Для одиночного переключения контекста
     *
     * 🔹 Переключение контекста:
     * ✅ withContext(Dispatchers.IO) { }
     * ✅ scope.launch(Dispatchers.IO) { }
     *
     * 🔹 Ожидание:
     * ✅ delay(1000)           // Неблокирующая задержка
     * ✅ yield()              // Уступить время другим корутинам
     *
     * 🔹 Отмена:
     * ✅ job.cancel()
     * ✅ ensureActive()
     * ✅ withTimeout(1000) { }
     *
     * 🔹 Обработка ошибок:
     * ✅ try { } catch (e: Exception) { }
     * ✅ CoroutineExceptionHandler { _, e -> }
     *
     * 🔹 Структурированная конкурентность:
     * ✅ coroutineScope { }    // Все дети должны завершиться
     * ✅ supervisorScope { }   // Дети независимы (ошибка не убивает всех)
     *
     * 🔹 Именованные скоупы (по жизненному циклу):
     * ✅ applicationScope      // Живёт всё приложение (только Application!)
     * ✅ viewModelScope        // Живёт пока жива ViewModel (встроенный)
     * ✅ lifecycleScope        // Живёт пока жива Activity/Fragment (встроенный)
     * ✅ repositoryScope       // Живёт пока жив Repository
     *
     * 🔹 Правила:
     * 1. НИКОГДА не используйте GlobalScope
     * 2. НИКОГДА не создавайте скоуп на лету: CoroutineScope().launch
     * 3. ВСЕГДА привязывайте скоуп к жизненному циклу
     * 4. ВСЕГДА обрабатывайте ошибки
     * 5. Используйте async ТОЛЬКО для параллельных задач
     * 6. Для одиночных задач используйте withContext
     * ============================================================
     */

    // ✅ ЧИСТКА: закрываем скоуп, когда он больше не нужен
    fun cleanup() {
        tutorialScope.cancel()
        applicationScope.cancel()
    }
}