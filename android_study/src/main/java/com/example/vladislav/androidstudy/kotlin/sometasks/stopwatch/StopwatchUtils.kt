package com.example.vladislav.androidstudy.kotlin.sometasks.stopwatch

const val STOP_BUTTON_NAME = "Stop"
const val START_BUTTON_NAME = "Start"
const val SPLIT_BUTTON_NAME = "Split"
const val PAUSE_BUTTON_NAME = "Pause"
const val CONTINUE_BUTTON_NAME = "Continue"

/**
 * Форматирует заданное количество миллисекунд в строку формата "MM:SS.MM", где:
 *
 * - MM — минуты (с ведущими нулями, 2 знака),
 * - SS — секунды (с ведущими нулями, 2 знака),
 * - MM — миллисекунды, округлённые до ближайших 10 (2 знака).
 *
 * Пример:
 * - `123456L` → `"02:03.40"`
 * - `987654L` → `"16:26.50"`
 *
 * @param timeInMillis Длительность в миллисекундах, которую нужно отформатировать.
 * @return Отформатированная строка в виде "MM:SS.MM".
 */
fun Long.formatTime(): String {
    val totalMillis = this
    val minutes = (totalMillis / 60000).toInt()
    val seconds = ((totalMillis % 60000) / 1000).toInt()
    val millis = (totalMillis % 1000).toInt()

    // Округляем миллисекунды до ближайших 10
    val roundedMillis = (millis / 10) * 10

    return "${minutes.toString().padStart(2, '0')}:${
        seconds.toString().padStart(2, '0')
    }.${roundedMillis.toString().padStart(2, '0')}"
}