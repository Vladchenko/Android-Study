Секундомер, подобный тому что есть в мобилах.
Реализация без помощи ИИ, оптимизация Deepseek, документация - Гигачат.
Реализация: вью модель, компоузбл, активити, модель данных, 2 флоу состояния, код максимально приближен а продакшн-ready.
    TODO: Вью модель имеет несколько ответственностей, хорошо бы разделить.

Запрос: (ПРИМЕРНЫЙ ЗАПРОС) Кажется главный поток слишком нагружен...

Ответ Гигачата:
Вы: Считаете время в IO (правильно), но обновляете состояние на Main (что не обязательно!).

🔴 _tellTimeState.value = ... — это не UI-операция, это обновление MutableStateFlow, которое не требует главного потока.

✅ Решение: Обновляйте StateFlow в Dispatchers.Default, а не в Main
StateFlow — это потокобезопасная структура данных. Она не требует выполнения на главном потоке.
👉 Обновлять MutableStateFlow можно в любом диспетчере, включая Dispatchers.Default или IO.
...
collectAsStateWithLifecycle() в Composable — автоматически собирает на главном потоке, даже если StateFlow обновлялся в фоне.
...
✅ То есть: вы можете спокойно обновлять StateFlow в Default, а Composable автоматически отреагирует на это в Main.

✅ Дополнительная оптимизация: Избегайте лишних обновлений
Если вы обновляете StateFlow 100 раз в секунду, но UI обновляется только 60 раз — это лишняя нагрузка.
👉 Используйте delay(16) — этого достаточно для плавного отображения.

✅ Альтернатива: Использовать callbackFlow + ticker (для высокой точности)
Если хотите ещё больше контроля, можно использовать callbackFlow:
val timerFlow: Flow<Long> = callbackFlow {
    while (true) {
        delay(16)
        trySend(System.currentTimeMillis() - initialTime - pausedTime)
    }
}.flowOn(Dispatchers.Default)

Но в вашем случае — StateFlow + фоновое обновление — достаточно и проще.
✅ Итог: Как должно быть
private fun tellTime() = viewModelScope.launch(Dispatchers.Default) {
    while (isActive) {
        if (!isPaused) {
            currentTime = System.currentTimeMillis() - initialTime - pausedTime
            _tellTimeState.value = StopwatchState.TellTime(currentTime)
        }
        delay(16) // 60 FPS
    }
}

🧠 collectAsStateWithLifecycle() в Composable автоматически переключится на Main, когда нужно отобразить.

✅ Проверка: Это безопасно?
Да! MutableStateFlow:
    Потокобезопасен (если вы не делаете сложные mutable operations).
    Может обновляться из любого потока.
    viewModelScope гарантирует отмену при уничтожении ViewModel.