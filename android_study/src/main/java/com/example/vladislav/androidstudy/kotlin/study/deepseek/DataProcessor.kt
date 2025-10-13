package com.example.vladislav.androidstudy.kotlin.study.deepseek

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

sealed class ProcessingResult {
    object Loading : ProcessingResult()
    data class Success(val data: String) : ProcessingResult()
    data class Error(val message: String, val cause: Throwable? = null) : ProcessingResult()
}

/**
 * Условие: Класс DataProcessor со списком строк.
 * Метод processData(): Flow<ProcessingResult> где:
 * ProcessingResult — sealed class (Loading, Success, Error)
 * Flow эмитит состояния по порядку
 * Есть простой кэш (без времени, просто при повторном вызове сразу Success)
 *
 * @property strings
 */
class DataProcessor(val strings: List<String>) {

    private var cachedResult: ProcessingResult? = null
    private var flow: Flow<ProcessingResult>? = null

    fun processData(): Flow<ProcessingResult> = flow {
        // Если есть валидный кэш - возвращаем его
        cachedResult?.let {
            emit(it)
            return@flow
        }

        // Если кэша нет - обрабатываем
        emit(ProcessingResult.Loading)
        try {
            // Твоя логика обработки...
            cachedResult = ProcessingResult.Success("Результат") // Сохраняем в кэш
            emit(cachedResult!!)
        } catch (e: Exception) {
            val error = ProcessingResult.Error(e.message ?: "Ошибка", e)
            cachedResult = error
            emit(error)
        }
    }
}