package com.example.vladislav.androidstudy.kotlin.demo.coroutines.flow

import android.util.Log
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * ================================================================================================
 * ПОЛНОЕ РУКОВОДСТВО ПО FLOW В KOTLIN COROUTINES
 * ================================================================================================
 *
 * ⚠️ ВНИМАНИЕ: В этом файле есть секции с АНТИПАТТЕРНАМИ.
 * Они помечены как "❌ НЕПРАВИЛЬНО" и используются ТОЛЬКО для демонстрации ошибок.
 * В продакшн-коде эти паттерны использовать ЗАПРЕЩЕНО!
 *
 * ОСНОВНЫЕ ПОНЯТИЯ:
 * - Cold Flow: Генерирует данные только при подписке (каждый коллектор получает свои данные)
 * - Hot Flow: Генерирует данные независимо от подписчиков (все получают одни и те же данные)
 * - Backpressure: Управление скоростью эмиссии vs скорость обработки
 * - Context: Flow работает в контексте вызывающей корутины (можно изменить через flowOn)
 * ================================================================================================
 */
class FlowTutorial {

    // ✅ ПРАВИЛЬНО: Создаём и сохраняем скоуп для управления корутинами
    // В Android используйте viewModelScope или lifecycleScope
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // ============================================================================================
    // БЛОК 1: СОЗДАНИЕ FLOW - ВСЕ ВОЗМОЖНЫЕ СПОСОБЫ
    // ============================================================================================

    /**
     * 1.1. Базовый билдер flow { }
     */
    private fun basicFlowBuilder(): Flow<Int> = flow {
        Log.i(TAG, "⚡ Flow стартует (холодный)")
        for (i in 1..3) {
            delay(100)
            emit(i)
        }
    }

    /**
     * ❌ АНТИПАТТЕРН 1: Использование GlobalScope
     *
     * Проблемы:
     * - Живёт всё время работы приложения
     * - Не отменяется при уничтожении Activity/Fragment
     * - Вызывает утечки памяти
     * - Не привязан к жизненному циклу
     *
     * Единственное допустимое использование - в учебных целях или в приложениях без UI
     */
    private fun antiPatternGlobalScope() {
        // ❌ НИКОГДА так не делайте в продакшн-коде!
        GlobalScope.launch { // <-- АНТИПАТТЕРН
            val flow = flow {
                emit(1)
                emit(2)
                emit(3)
            }

            flow.collect { value ->
                Log.i(TAG, "✅ $value") // Работает, но вызывает утечку памяти
            }
        }
        // Корутина продолжает жить, даже если Activity уничтожен!
    }

    /**
     * ✅ ПРАВИЛЬНО: Использование сохранённого скоупа
     */
    private fun correctScopeUsage() {
        // В Android: viewModelScope.launch { } или lifecycleScope.launch { }
        coroutineScope.launch { // <-- ПРАВИЛЬНО: скоуп сохранён и управляем
            val flow = flow {
                emit(1)
                emit(2)
                emit(3)
            }

            flow.collect { value ->
                Log.i(TAG, "✅ $value")
            }
        }
        // Корутина будет отменена при отмене скоупа
    }

    /**
     * ❌ АНТИПАТТЕРН 2: Создание CoroutineScope без сохранения
     *
     * Проблемы:
     * - Скоуп создаётся и сразу теряется
     * - Нельзя отменить корутины
     * - Приводит к утечкам
     */
    private fun antiPatternLostScope() {
        // ❌ НИКОГДА так не делайте!
        CoroutineScope(Dispatchers.IO).launch { // <-- Скоуп теряется
            val flow = flow {
                emit(1)
                delay(1000)
                emit(2)
            }

            flow.collect { value ->
                Log.i(TAG, "✅ $value")
            }
        }
        // Нельзя отменить эту корутину!
    }

    /**
     * ✅ ПРАВИЛЬНО: Сохранение скоупа как поля
     */
    private val correctScope = CoroutineScope(Dispatchers.IO + Job())

    private fun correctScopeAsField() {
        correctScope.launch { // <-- ПРАВИЛЬНО: скоуп сохранён
            val flow = flow {
                emit(1)
                delay(1000)
                emit(2)
            }

            flow.collect { value ->
                Log.i(TAG, "✅ $value")
            }
        }
    }

    fun cleanupScope() {
        correctScope.cancel() // Можно отменить все корутины
    }

    // ... (остальные способы создания Flow из предыдущей версии)

    // ============================================================================================
    // БЛОК 2: ОПЕРАТОРЫ И ТРАНСФОРМАЦИИ
    // ============================================================================================

    // ... (операторы из предыдущей версии)

    /**
     * ❌ АНТИПАТТЕРН 3: Тяжёлые операции в main потоке
     *
     * Проблемы:
     * - Блокирует UI
     * - Вызывает ANR (Application Not Responding)
     * - Плохой пользовательский опыт
     */
    private fun antiPatternMainThreadBlocking() {
        // ❌ НИКОГДА так не делайте!
        CoroutineScope(Dispatchers.Main).launch {
            (1..10).asFlow()
                .map { value ->
                    // Тяжёлые вычисления на Main потоке!
                    Thread.sleep(100) // <-- Блокирует UI
                    value * 2
                }
                .collect { value ->
                    // Обновление UI
                    Log.i(TAG, "✅ $value") // Будет с задержкой
                }
        }
        // UI будет заморожен на 1 секунду!
    }

    /**
     * ✅ ПРАВИЛЬНО: Тяжёлые операции на background потоках
     */
    private fun correctBackgroundOperations() {
        CoroutineScope(Dispatchers.Main).launch {
            (1..10).asFlow()
                .map { value ->
                    // Тяжёлые вычисления на IO
                    delay(100) // <-- Не блокирует
                    value * 2
                }
                .flowOn(Dispatchers.IO) // <-- КЛЮЧЕВОЙ МОМЕНТ
                .collect { value ->
                    // Обновление UI на Main потоке
                    Log.i(TAG, "✅ $value")
                }
        }
    }

    // ============================================================================================
    // БЛОК 3: КОНТЕКСТ ВЫПОЛНЕНИЯ
    // ============================================================================================

    // ... (flowOn демонстрации из предыдущей версии)

    /**
     * ❌ АНТИПАТТЕРН 4: Неправильное использование flowOn
     *
     * Проблемы:
     * - flowOn влияет только на операторы ДО него
     * - Операторы ПОСЛЕ flowOn выполняются в контексте коллектора
     */
    private fun antiPatternWrongFlowOn() {
        CoroutineScope(Dispatchers.Main).launch {
            Log.i(TAG, "\n❌ НЕПРАВИЛЬНЫЙ FLOW ON\n")

            (1..5).asFlow()
                .flowOn(Dispatchers.IO) // <-- Влияет только на map ниже?
                .map { // ✅ Будет на IO (правильно)
                    Log.i(TAG, "map на ${Thread.currentThread().name}")
                    it * 2
                }
                .collect { // ❌ Будет на Main (неправильно для тяжёлой работы)
                    Log.i(TAG, "collect на ${Thread.currentThread().name}")
                    // Тяжёлая работа здесь заблокирует UI!
                    Thread.sleep(100)
                }
        }
    }

    /**
     * ✅ ПРАВИЛЬНО: Все тяжёлые операции до flowOn
     */
    private fun correctFlowOnUsage() {
        CoroutineScope(Dispatchers.Main).launch {
            Log.i(TAG, "\n✅ ПРАВИЛЬНЫЙ FLOW ON\n")

            (1..5).asFlow()
                .map { // ✅ На IO
                    Log.i(TAG, "map на ${Thread.currentThread().name}")
                    it * 2
                }
                .filter { // ✅ На IO
                    Log.i(TAG, "filter на ${Thread.currentThread().name}")
                    it % 2 == 0
                }
                .flowOn(Dispatchers.IO) // <-- Все операторы выше на IO
                .collect { // ✅ На Main (только лёгкие операции)
                    Log.i(TAG, "collect на ${Thread.currentThread().name}")
                    Log.i(TAG, "✅ $it")
                }
        }
    }

    // ============================================================================================
    // БЛОК 4: ОБРАБОТКА ОШИБОК
    // ============================================================================================

    // ... (обработка ошибок из предыдущей версии)

    /**
     * ❌ АНТИПАТТЕРН 5: catch в неправильном месте
     *
     * Проблемы:
     * - catch перехватывает ошибки только ДО collect
     * - Операторы после catch не защищены
     */
    private fun antiPatternWrongCatchPosition() {
        CoroutineScope(Dispatchers.IO).launch {
            Log.i(TAG, "\n❌ НЕПРАВИЛЬНЫЙ CATCH\n")

            flow {
                emit(1)
                throw RuntimeException("Ошибка в flow")
                emit(2)
            }
                .map { // ❌ Не защищён catch
                    Log.i(TAG, "map: $it")
                    it * 2
                }
                .catch { error -> // ❌ Слишком поздно!
                    Log.i(TAG, "⚠️ Поймано: ${error.message}")
                }
                .collect { value ->
                    Log.i(TAG, "✅ $value")
                }
        }
        // Ошибка в map НЕ будет поймана!
    }

    /**
     * ✅ ПРАВИЛЬНО: catch перед операторами, которые могут выбросить ошибку
     */
    private fun correctCatchPosition() {
        CoroutineScope(Dispatchers.IO).launch {
            Log.i(TAG, "\n✅ ПРАВИЛЬНЫЙ CATCH\n")

            flow {
                emit(1)
                throw RuntimeException("Ошибка в flow")
                emit(2)
            }
                .catch { error -> // <-- ПРАВИЛЬНО: до map
                    Log.i(TAG, "⚠️ Поймано: ${error.message}")
                    emit(-1) // Fallback
                }
                .map { // ✅ Безопасно
                    Log.i(TAG, "map: $it")
                    it * 2
                }
                .collect { value ->
                    Log.i(TAG, "✅ $value")
                }
        }
    }

    /**
     * ❌ АНТИПАТТЕРН 6: Игнорирование ошибок в collect
     *
     * Проблемы:
     * - Ошибки в collect не перехватываются catch
     * - Приводят к падению приложения
     */
    private fun antiPatternIgnoringCollectErrors() {
        CoroutineScope(Dispatchers.IO).launch {
            Log.i(TAG, "\n❌ ИГНОРИРОВАНИЕ ОШИБОК В COLLECT\n")

            flowOf(1, 2, 3)
                .catch { error -> // ❌ Не поймает ошибку в collect
                    Log.i(TAG, "⚠️ ${error.message}")
                }
                .collect { value ->
                    Log.i(TAG, "📦 $value")
                    if (value == 2) {
                        throw RuntimeException("Ошибка в collect!") // <-- Приложение упадёт!
                    }
                }
        }
    }

    /**
     * ✅ ПРАВИЛЬНО: try-catch вокруг collect
     */
    private fun correctCollectErrorHandling() {
        CoroutineScope(Dispatchers.IO).launch {
            Log.i(TAG, "\n✅ ПРАВИЛЬНАЯ ОБРАБОТКА ОШИБОК В COLLECT\n")

            try {
                flowOf(1, 2, 3)
                    .catch { error -> // Для ошибок до collect
                        Log.i(TAG, "⚠️ Ошибка в потоке: ${error.message}")
                    }
                    .collect { value ->
                        Log.i(TAG, "📦 $value")
                        if (value == 2) {
                            throw RuntimeException("Ошибка в collect!")
                        }
                    }
            } catch (e: Exception) { // <-- ПРАВИЛЬНО
                Log.i(TAG, "✅ Поймано в try-catch: ${e.message}")
            }
        }
    }

    // ============================================================================================
    // БЛОК 5: ГОРЯЧИЕ ПОТОКИ (SHAREDFLOW И STATEFLOW)
    // ============================================================================================

    // ... (StateFlow и SharedFlow демонстрации из предыдущей версии)

    /**
     * ❌ АНТИПАТТЕРН 7: Использование StateFlow как SharedFlow
     *
     * Проблемы:
     * - StateFlow всегда имеет значение
     * - Не подходит для одноразовых событий
     * - Может привести к обработке устаревших событий
     */
    private fun antiPatternStateFlowAsSharedFlow() {
        // ❌ НЕПРАВИЛЬНО
        val stateFlow = MutableStateFlow<String?>(null)

        // Пытаемся использовать как событийный поток
        CoroutineScope(Dispatchers.IO).launch {
            stateFlow.collect { event ->
                // При старте получим null!
                event?.let { Log.i(TAG, "📦 $it") }
            }
        }

        // Отправка события
        stateFlow.value = "Event 1" // <-- Неправильно: сохраняется состояние
        // Новый подписчик получит "Event 1", даже если событие уже обработано
    }

    /**
     * ✅ ПРАВИЛЬНО: SharedFlow для событий, StateFlow для состояния
     */
    private fun correctHotFlowUsage() {
        Log.i(TAG, "\n✅ ПРАВИЛЬНОЕ ИСПОЛЬЗОВАНИЕ ГОРЯЧИХ ПОТОКОВ\n")

        // Для состояния - StateFlow
        val stateFlow = MutableStateFlow("Initial")
        stateFlow.value = "Updated State" // <-- ПРАВИЛЬНО

        // Для событий - SharedFlow
        val sharedFlow = MutableSharedFlow<String>()

        CoroutineScope(Dispatchers.IO).launch {
            sharedFlow.collect { event -> // <-- Получаем только новые события
                Log.i(TAG, "📦 Событие: $event")
            }
        }

        // Отправка событий
        runBlocking {
            sharedFlow.emit("Event 1")
            sharedFlow.emit("Event 2")
        }
    }

    /**
     * ❌ АНТИПАТТЕРН 8: Утечка SharedFlow через бесконечные подписки
     *
     * Проблемы:
     * - Подписка никогда не отменяется
     * - Удерживает ссылки на объекты
     * - Приводит к утечкам памяти
     */
    private fun antiPatternSharedFlowLeak() {
        // ❌ НЕПРАВИЛЬНО
        class MyViewModel {
            private val events = MutableSharedFlow<String>()

            init {
                // Подписка без сохранения Job
                CoroutineScope(Dispatchers.Main).launch { // <-- Скоуп теряется
                    events.collect { event ->
                        Log.i(TAG, "📦 $event")
                    }
                }
                // Невозможно отменить эту подписку!
            }
        }
    }

    /**
     * ✅ ПРАВИЛЬНО: Сохранение Job для отмены подписок
     */
    private fun correctSharedFlowSubscription() {
        class MyViewModel {
            private val viewModelScope = CoroutineScope(Dispatchers.Main + Job())
            private val events = MutableSharedFlow<String>()
            private val eventsJob: Job

            init {
                eventsJob = viewModelScope.launch { // <-- Сохраняем Job
                    events.collect { event ->
                        Log.i(TAG, "📦 $event")
                    }
                }
            }

            fun onCleared() {
                viewModelScope.cancel() // Отменяем все подписки
            }
        }
    }

    // ============================================================================================
    // БЛОК 6: ПРОИЗВОДИТЕЛЬНОСТЬ И BACKPRESSURE
    // ============================================================================================

    // ... (buffer, conflate демонстрации из предыдущей версии)

    /**
     * ❌ АНТИПАТТЕРН 9: Медленный коллектор без буферизации
     *
     * Проблемы:
     * - Эмиттер ждёт коллектор
     * - Снижение производительности
     * - Последовательная обработка
     */
    private fun antiPatternSlowCollector() {
        CoroutineScope(Dispatchers.IO).launch {
            Log.i(TAG, "\n❌ МЕДЛЕННЫЙ КОЛЛЕКТОР\n")

            val time = measureTimeMillis {
                flow {
                    repeat(10) {
                        emit(it)
                        Log.i(TAG, "📤 Эмиттим: $it")
                    }
                }
                    .collect { // <-- Медленный коллектор
                        delay(200) // Медленная обработка
                        Log.i(TAG, "📦 $it")
                    }
            }
            Log.i(TAG, "⏱️ Время: ${time}ms") // Медленно!
        }
    }

    /**
     * ✅ ПРАВИЛЬНО: Использование buffer для параллельной обработки
     */
    private fun correctBufferUsage() {
        CoroutineScope(Dispatchers.IO).launch {
            Log.i(TAG, "\n✅ С БУФЕРОМ\n")

            val time = measureTimeMillis {
                flow {
                    repeat(10) {
                        emit(it)
                        Log.i(TAG, "📤 Эмиттим: $it")
                    }
                }
                    .buffer(5) // <-- Буфер для параллельной обработки
                    .collect {
                        delay(200)
                        Log.i(TAG, "📦 $it")
                    }
            }
            Log.i(TAG, "⏱️ Время: ${time}ms") // Быстрее!
        }
    }

    // ============================================================================================
    // БЛОК 7: ОТМЕНА И ЖИЗНЕННЫЙ ЦИКЛ
    // ============================================================================================

    /**
     * ❌ АНТИПАТТЕРН 10: Отсутствие проверки на отмену
     *
     * Проблемы:
     * - Бесконечный цикл не реагирует на отмену
     * - Продолжает работу после уничтожения UI
     * - Утечки памяти
     */
    private fun antiPatternNoCancellationCheck() {
        val scope = CoroutineScope(Dispatchers.IO + Job())

        scope.launch {
            Log.i(TAG, "\n❌ БЕЗ ПРОВЕРКИ ОТМЕНЫ\n")

            flow {
                var i = 0
                while (true) { // <-- Бесконечный цикл
                    emit(i++)
                    delay(100)
                }
            }
                .collect { value ->
                    Log.i(TAG, "📦 $value")
                }
        }

        scope.launch {
            delay(500)
            Log.i(TAG, "🛑 Отменяем")
            scope.cancel() // Корутина отменена, но цикл продолжает работать!
        }
    }

    /**
     * ✅ ПРАВИЛЬНО: Проверка на отмену
     */
    private fun correctCancellationCheck() {
        val scope = CoroutineScope(Dispatchers.IO + Job())

        scope.launch {
            Log.i(TAG, "\n✅ С ПРОВЕРКОЙ ОТМЕНЫ\n")

            flow {
                var i = 0
                while (true) {
                    ensureActive() // <-- Проверяем отмену
                    emit(i++)
                    delay(100)
                }
            }
                .onCompletion { cause ->
                    if (cause is CancellationException) {
                        Log.i(TAG, "🛑 Корректно отменён")
                    }
                }
                .collect { value ->
                    Log.i(TAG, "📦 $value")
                }
        }

        scope.launch {
            delay(500)
            scope.cancel()
        }
    }

    // ============================================================================================
    // БЛОК 8: ПРАКТИЧЕСКИЕ ПРИМЕРЫ
    // ============================================================================================

    /**
     * ❌ АНТИПАТТЕРН 11: Множественные подписки на один холодный flow
     *
     * Проблемы:
     * - Каждая подписка создаёт новую эмиссию
     * - Повторная генерация данных
     * - Большая нагрузка
     */
    private fun antiPatternMultipleColdSubscriptions() {
        // ❌ НЕПРАВИЛЬНО
        val coldFlow = flow {
            Log.i(TAG, "⚡ Генерация дорогих данных") // <-- Выполнится 3 раза!
            emit("Expensive data")
        }

        // Три подписки на холодный поток
        listOf(1, 2, 3).forEach { index ->
            CoroutineScope(Dispatchers.IO).launch {
                coldFlow.collect { value ->
                    Log.i(TAG, "📦 Подписчик $index: $value")
                }
            }
        }
        // Данные сгенерируются 3 раза - дорого!
    }

    /**
     * ✅ ПРАВИЛЬНО: Использование shareIn для одного источника данных
     */
    private fun correctSharedFlowForMultipleSubscribers() {
        val scope = CoroutineScope(Dispatchers.IO)

        // Холодный поток с дорогой генерацией
        val expensiveFlow = flow {
            Log.i(TAG, "⚡ Генерация дорогих данных")
            delay(1000) // Тяжёлая операция
            emit("Expensive data")
        }

        // Превращаем в горячий с sharedIn
        val sharedFlow = expensiveFlow.shareIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            replay = 1
        )

        // Три подписки - данные сгенерируются 1 раз!
        listOf(1, 2, 3).forEach { index ->
            scope.launch {
                sharedFlow.collect { value ->
                    Log.i(TAG, "📦 Подписчик $index: $value")
                }
            }
        }
    }

    /**
     * ❌ АНТИПАТТЕРН 12: Использование mutable переменных в flow
     *
     * Проблемы:
     * - Непотокобезопасно
     * - Гонка состояний
     * - Непредсказуемое поведение
     */
    private fun antiPatternMutableStateInFlow() {
        // ❌ НЕПРАВИЛЬНО
        var counter = 0 // <-- Мутабельная переменная

        val flow = flow {
            while (counter < 10) {
                emit(counter)
                counter++ // <-- Изменяем из разных мест
            }
        }

        // Первая подписка
        CoroutineScope(Dispatchers.IO).launch {
            flow.collect { value ->
                Log.i(TAG, "📦 Подписчик 1: $value")
            }
        }

        // Вторая подписка на тот же поток
        CoroutineScope(Dispatchers.IO).launch {
            delay(100) // Дадим время стартовать первой
            flow.collect { value ->
                Log.i(TAG, "📦 Подписчик 2: $value") // Непредсказуемый результат!
            }
        }
    }

    /**
     * ✅ ПРАВИЛЬНО: Локальные переменные внутри flow
     */
    private fun correctStateInFlow() {
        val flow = flow {
            var counter = 0 // <-- Локальная переменная
            while (counter < 10) {
                emit(counter)
                counter++
            }
        }

        // Каждая подписка имеет свою локальную переменную
        CoroutineScope(Dispatchers.IO).launch {
            flow.collect { value ->
                Log.i(TAG, "📦 Подписчик 1: $value")
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            delay(100)
            flow.collect { value ->
                Log.i(TAG, "📦 Подписчик 2: $value") // Предсказуемо!
            }
        }
    }

    /**
     * ❌ АНТИПАТТЕРН 13: Неправильная очистка в callbackFlow
     *
     * Проблемы:
     * - Утечки слушателей
     * - Корутина продолжает жить
     * - Память не освобождается
     */
    private fun antiPatternCallbackFlowNoCleanup() {
        // ❌ НЕПРАВИЛЬНО
        class ButtonSimulator {
            private var listener: (() -> Unit)? = null

            fun setListener(listener: () -> Unit) {
                this.listener = listener
            }

            fun click() {
                listener?.invoke()
            }
        }

        val button = ButtonSimulator()

        val flow = callbackFlow {
            button.setListener { // <-- Слушатель установлен
                trySend(Unit)
            }
            // ❌ НЕТ awaitClose - утечка слушателя!
        }

        CoroutineScope(Dispatchers.IO).launch {
            flow.collect {
                Log.i(TAG, "📦 Клик!")
            }
        }
        // Слушатель никогда не будет удалён!
    }

    /**
     * ✅ ПРАВИЛЬНО: Обязательный awaitClose
     */
    private fun correctCallbackFlowCleanup() {
        class ButtonSimulator {
            private var listener: (() -> Unit)? = null

            fun setListener(listener: () -> Unit) {
                this.listener = listener
            }

            fun removeListener() {
                listener = null
            }

            fun click() {
                listener?.invoke()
            }
        }

        val button = ButtonSimulator()

        val flow = callbackFlow {
            button.setListener {
                trySend(Unit)
            }

            awaitClose { // <-- ОБЯЗАТЕЛЬНО!
                Log.i(TAG, "🧹 Удаляем слушателя")
                button.removeListener()
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            flow.collect {
                Log.i(TAG, "📦 Клик!")
            }
        }
        // Слушатель будет удалён при отмене
    }

    // ============================================================================================
    // ПОЛНЫЙ РАЗДЕЛ С АНТИПАТТЕРНАМИ
    // ============================================================================================

    /**
     * ============================================================================================
     * ГЛАВНЫЕ АНТИПАТТЕРНЫ FLOW (СВОДКА)
     * ============================================================================================
     *
     * 1. ❌ GlobalScope.launch { } - утечки памяти, нет управления
     * 2. ❌ CoroutineScope без сохранения - нельзя отменить корутины
     * 3. ❌ Тяжёлые операции на Main потоке - блокировка UI, ANR
     * 4. ❌ Неправильное использование flowOn - операции в неправильном контексте
     * 5. ❌ catch в неправильном месте - не перехватывает ошибки
     * 6. ❌ Игнорирование ошибок в collect - падение приложения
     * 7. ❌ StateFlow как SharedFlow - неправильное использование состояний
     * 8. ❌ Утечка подписок на SharedFlow - невозможность отмены
     * 9. ❌ Медленный коллектор без buffer - снижение производительности
     * 10. ❌ Отсутствие проверки на отмену - бесконечные циклы
     * 11. ❌ Множественные подписки на холодный flow - повторная генерация
     * 12. ❌ Мутабельные переменные в flow - гонка состояний
     * 13. ❌ Отсутствие awaitClose в callbackFlow - утечки слушателей
     * 14. ❌ Использование collect в launchIn - дублирование
     * 15. ❌ Игнорирование backpressure - потеря данных
     * ============================================================================================
     */

    /**
     * ❌ АНТИПАТТЕРН 14: collect в launchIn
     *
     * Проблемы:
     * - launchIn уже подписывается на collect
     * - Двойной collect не нужен
     * - Может вызвать ошибки
     */
    private fun antiPatternCollectInLaunchIn() {
        val scope = CoroutineScope(Dispatchers.IO)

        // ❌ НЕПРАВИЛЬНО
        (1..10).asFlow()
            .onEach { Log.i(TAG, "📦 $it") }
            .launchIn(scope)
//            .collect { // <-- Ошибка! collect вызывается на Job
//                // Это НЕ работает!
//            }
    }

    /**
     * ✅ ПРАВИЛЬНО: launchIn без collect
     */
    private fun correctLaunchInUsage() {
        val scope = CoroutineScope(Dispatchers.IO)

        // ✅ ПРАВИЛЬНО
        (1..10).asFlow()
            .onEach { Log.i(TAG, "📦 $it") }
            .launchIn(scope) // <-- Только launchIn, без collect
    }

    /**
     * ❌ АНТИПАТТЕРН 15: Игнорирование backpressure
     *
     * Проблемы:
     * - Потеря данных
     * - Переполнение буфера
     * - Нестабильность
     */
    private fun antiPatternBackpressureIgnored() {
        CoroutineScope(Dispatchers.IO).launch {
            // ❌ НЕПРАВИЛЬНО
            flow {
                repeat(1000) {
                    emit(it)
                    delay(1) // <-- Быстрая эмиссия
                }
            }
                .collect { value ->
                    delay(100) // <-- Медленная обработка
                    Log.i(TAG, "📦 $value") // Будут потеряны многие значения
                }
        }
    }

    /**
     * ✅ ПРАВИЛЬНО: Управление backpressure
     */
    private fun correctBackpressureHandling() {
        CoroutineScope(Dispatchers.IO).launch {
            // Вариант 1: Буферизация
            flow {
                repeat(1000) {
                    emit(it)
                    delay(1)
                }
            }
                .buffer(100) // <-- Буфер для быстрой эмиссии
                .collect { value ->
                    delay(100)
                    Log.i(TAG, "📦 $value")
                }

            // Вариант 2: Замедление эмиттера
            flow {
                repeat(1000) {
                    emit(it)
                    delay(10) // <-- Замедляем эмиссию
                }
            }
                .collect { value ->
                    delay(100)
                    Log.i(TAG, "📦 $value")
                }

            // Вариант 3: Пропуск промежуточных значений
            flow {
                repeat(1000) {
                    emit(it)
                    delay(1)
                }
            }
                .conflate() // <-- Сохраняем только последнее значение
                .collect { value ->
                    delay(100)
                    Log.i(TAG, "📦 $value") // Только последние значения
                }
        }
    }

    // ============================================================================================
    // ДЕМОНСТРАЦИЯ ВСЕХ АНТИПАТТЕРНОВ
    // ============================================================================================

    /**
     * Запускает демонстрацию всех антипаттернов
     * Используйте ТОЛЬКО для обучения!
     */
    suspend fun runAllAntiPatterns() {
        Log.i(TAG, "\n" + "=".repeat(80))
        Log.i(TAG, "⚠️ ДЕМОНСТРАЦИЯ АНТИПАТТЕРНОВ")
        Log.i(TAG, "Эти примеры показывают, как НЕ НАДО писать код!")
        Log.i(TAG, "=".repeat(80))

        // Блок 1: Создание и скоупы
        Log.i(TAG, "\n--- АНТИПАТТЕРНЫ СОЗДАНИЯ ---")
        antiPatternGlobalScope()
        delay(500)
        antiPatternLostScope()
        delay(500)

        // Блок 2: Контекст
        Log.i(TAG, "\n--- АНТИПАТТЕРНЫ КОНТЕКСТА ---")
        antiPatternMainThreadBlocking()
        delay(1000)
        antiPatternWrongFlowOn()
        delay(1000)

        // Блок 3: Ошибки
        Log.i(TAG, "\n--- АНТИПАТТЕРНЫ ОШИБОК ---")
        antiPatternWrongCatchPosition()
        delay(500)
        antiPatternIgnoringCollectErrors()
        delay(500)

        // Блок 4: Горячие потоки
        Log.i(TAG, "\n--- АНТИПАТТЕРНЫ ГОРЯЧИХ ПОТОКОВ ---")
        antiPatternStateFlowAsSharedFlow()
        delay(500)
        antiPatternSharedFlowLeak()
        delay(500)

        // Блок 5: Производительность
        Log.i(TAG, "\n--- АНТИПАТТЕРНЫ ПРОИЗВОДИТЕЛЬНОСТИ ---")
        antiPatternSlowCollector()
        delay(500)

        // Блок 6: Отмена
        Log.i(TAG, "\n--- АНТИПАТТЕРНЫ ОТМЕНЫ ---")
        antiPatternNoCancellationCheck()
        delay(1000)

        // Блок 7: Множественные подписки
        Log.i(TAG, "\n--- АНТИПАТТЕРНЫ ПОДПИСОК ---")
        antiPatternMultipleColdSubscriptions()
        delay(1000)

        // Блок 8: Мутабельный стейт
        Log.i(TAG, "\n--- АНТИПАТТЕРНЫ СОСТОЯНИЯ ---")
        antiPatternMutableStateInFlow()
        delay(1000)

        // Блок 9: CallbackFlow
        Log.i(TAG, "\n--- АНТИПАТТЕРНЫ CALLBACKFLOW ---")
        antiPatternCallbackFlowNoCleanup()
        delay(500)

        // Блок 10: Backpressure
        Log.i(TAG, "\n--- АНТИПАТТЕРНЫ BACKPRESSURE ---")
        antiPatternBackpressureIgnored()
        delay(1000)

        // Блок 11: Сборка
        Log.i(TAG, "\n--- АНТИПАТТЕРНЫ СБОРКИ ---")
        antiPatternCollectInLaunchIn()
        delay(500)

        Log.i(TAG, "\n" + "=".repeat(80))
        Log.i(TAG, "✅ ДЕМОНСТРАЦИЯ АНТИПАТТЕРНОВ ЗАВЕРШЕНА")
        Log.i(TAG, "ЗАПОМНИТЕ: Эти паттерны НЕЛЬЗЯ использовать в продакшн-коде!")
        Log.i(TAG, "=".repeat(80))
    }

    /**
     * Запускает все корректные демонстрации
     */
    suspend fun runAllCorrectDemos() {
        Log.i(TAG, "\n" + "=".repeat(80))
        Log.i(TAG, "✅ ПРАВИЛЬНЫЕ ДЕМОНСТРАЦИИ FLOW")
        Log.i(TAG, "=".repeat(80))

        // Здесь запускаются все правильные примеры из предыдущих блоков
        // (код из предыдущей версии tutorial)

        Log.i(TAG, "\n" + "=".repeat(80))
        Log.i(TAG, "✅ ВСЕ ПРАВИЛЬНЫЕ ДЕМОНСТРАЦИИ ЗАВЕРШЕНЫ")
        Log.i(TAG, "=".repeat(80))
    }

    /**
     * Итоговый чек-лист для проверки кода
     */
    fun codeReviewChecklist(): List<String> = listOf(
        "✅ Используется ли сохранённый CoroutineScope (viewModelScope/lifecycleScope)?",
        "✅ Все ли тяжёлые операции на background потоках?",
        "✅ Есть ли проверка на отмену (ensureActive) в долгих циклах?",
        "✅ Правильно ли расположен catch (до операторов, которые могут выбросить ошибку)?",
        "✅ Обработаны ли ошибки в collect через try-catch?",
        "✅ Используется ли awaitClose в callbackFlow?",
        "✅ Используется ли buffer для медленных коллекторов?",
        "✅ Сохранены ли Job для отмены подписок на SharedFlow?",
        "✅ Используется ли shareIn для множественных подписок на холодный flow?",
        "✅ Используется ли StateFlow для состояния, SharedFlow для событий?",
        "✅ Нет ли мутабельных переменных вне локального контекста flow?",
        "✅ Правильно ли используется flowOn (только для операторов ДО)?",
        "✅ Используется ли launchIn без collect?",
        "✅ Обработаны ли утечки слушателей в callbackFlow?",
        "✅ Есть ли управление backpressure (buffer, conflate)?"
    )

    companion object {
        private const val TAG = "FlowTutorial"
    }
}