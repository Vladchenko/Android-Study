package com.example.vladislav.androidstudy.kotlin.demo.coroutines.task4.sharedflow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow

interface ProgressProvider {
    fun executeTask(scope: CoroutineScope)
    fun getProgressFlow(): SharedFlow<String>
}