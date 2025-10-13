package com.example.vladislav.androidstudy.kotlin.demo.coroutines.task4.sharedflow

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

/**
 *  Отcлеживатель прогресса из получаемых из флоу сообщений.
 */
class ProgressChecker(progressProvider: ProgressProvider) {

    private val progressFlow: SharedFlow<String> = progressProvider.getProgressFlow()

    fun checkProgress(scope: CoroutineScope) {
        scope.launch {
            progressFlow.collect { progressValue ->
                delay(500)
                Log.i(TAG, progressValue)
                if (progressValue.contains("100")) {
                    Log.i(TAG, "ProgressChecker finished monitoring")
                }
            }
            // Не стоит здесь делать cancel скоупу,
            // тк он может ещё что-то делать где-то снаружи
        }
    }

    companion object {
        private val TAG = "ProgressChecker"
    }
}