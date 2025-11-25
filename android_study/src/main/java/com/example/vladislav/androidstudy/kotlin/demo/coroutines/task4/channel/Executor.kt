package com.example.vladislav.androidstudy.kotlin.demo.coroutines.task4.channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Логика выполнения фиктивной задачи
 */
class Executor {

    /**
     * Отправляем прогресс в канал
     *
     * @param scope в котором выполняется задача
     * @param channel канал для отправки прогресса
     */
    fun executeTask(scope: CoroutineScope, channel: Channel<String>): Job {
        var percentInc = 20
        return scope.launch {
            repeat(5) {
                delay((1000 + Math.random() * 2000).toLong())
                channel.send("Progress: $percentInc%")
                percentInc += 20
            }
            channel.close() // !!! Закрываем канал после завершения всех этапов
        }
    }
}