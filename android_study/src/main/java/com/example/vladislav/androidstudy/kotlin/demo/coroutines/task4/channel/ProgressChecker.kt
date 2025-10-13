package com.example.vladislav.androidstudy.kotlin.demo.coroutines.task4.channel

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *  Отcлеживатель прогресса из получаемых в канале(Channel) сообщений.
 */
class ProgressChecker {

    fun checkProgress(scope: CoroutineScope, progressChannel: Channel<String>) {
        scope.launch {
            // while(true) { - плохой вариант цикла приёма сообщений из канала
            for (progressValue in progressChannel) { // Безопасный прием сообщений из канала
                // Использование стандартного цикла for (message in progress) автоматически прекращает приём сообщений, когда канал закрывается. Никаких специальных проверок или исключений ловить не нужно.
                delay(500)
                Log.i(TAG, progressValue)
                if (progressValue.contains("100")) {
                    Log.i(TAG, "ProgressChecker finished monitoring")
                    break
                }
            }
            // Не стоит здесь делать cancel скоупу,
            // тк он может ещё что-то делать где-то снаружи
        }
    }

    companion object {
        private const val TAG = "ProgressChecker"
    }
}