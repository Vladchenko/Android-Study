package com.example.vladislav.androidstudy.kotlin.demo.coroutines.task4.sharedflow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

/**
 * Логика выполнения фиктивной задачи
 *
 * Экземпляр SharedFlow можно создать с помощью функции callbackFlow, stateFlow или напрямую с помощью MutableSharedFlow.
 */
class Executor: ProgressProvider {

    private val progressFlow = MutableSharedFlow<String>(replay = 5) // Сохраняем последние 5 сообщений, без этого сообщений не будет

    override fun executeTask(scope: CoroutineScope) {
        var percentInc = 20
        scope.launch {
            delay(3000) // Задержка чтобы посмотреть как поведёт себя progressChecker в случае отсутствия сообщений в начале
            repeat(5) {
                delay((1000 + Math.random() * 2000).toLong())
                progressFlow.tryEmit("Progress: $percentInc%")
                percentInc += 20
            }
        }
    }

    override fun getProgressFlow(): SharedFlow<String> {
        return progressFlow
    }
}