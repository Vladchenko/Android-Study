package com.example.vladislav.androidstudy.kotlin.sometasks.stopwatch

/**
 * Все состояния секундомера
 */
sealed class StopwatchState {
    object Paused : StopwatchState()
    object Initial : StopwatchState()
    object Started : StopwatchState()
    object Stopped : StopwatchState()
    object Continue : StopwatchState()
    data class Split(val time: Long) : StopwatchState()
    data class TellTime(val time: Long) : StopwatchState()
}