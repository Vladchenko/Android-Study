package com.example.vladislav.androidstudy.kotlin.study.deepseek

/**
 * sealed class и when (более сложная задача)
 * Условие: Мы моделируем состояние загрузки данных.
 * Объяви sealed class LoadState.
 * Создай три наследника:
 *
 * object Loading : LoadState() (объект, т.к. состояние одно)
 * data class Success(val data: String) : LoadState()
 * data class Error(val message: String, val cause: Throwable? = null) : LoadState()
 *
 * Напиши функцию handleState(state: LoadState), которая возвращает строку в зависимости от состояния:
 * Для Loading -> "Загрузка..."
 * Для Success -> "Данные: ${state.data}" (используй smart cast)
 * Для Error -> "Ошибка: ${state.message}" (используй smart cast)
 *
 * Требование: Используй выражение when. Оно должно быть exhaustive (полным), без ветки else, благодаря использованию sealed class.
 */


sealed class LoadState
object Loading : LoadState() //(объект, т.к. состояние одно)
data class Success(val data: String) : LoadState()
data class Error(val message: String, val cause: Throwable? = null) : LoadState()

fun handleState(state: LoadState) {
    return when (state) {
        is Loading -> println("Загрузка...")
        is Success -> println("Данные: ${state.data}")
        is Error -> println("Ошибка: ${state.message}")
    }
}