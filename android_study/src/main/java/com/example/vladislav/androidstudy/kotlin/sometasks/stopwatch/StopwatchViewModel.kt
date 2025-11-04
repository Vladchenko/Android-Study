package com.example.vladislav.androidstudy.kotlin.sometasks.stopwatch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * Вью модель секундомера
 */
class StopwatchViewModel : ViewModel() {

    private var currentTime = 0L
    private var initialTime: Long = 0
    private var pausedTime: Long = 0
    private var pauseStartTime: Long = 0L
    private var timerJob: Job? = null
    private var isPaused = false

    private val splitsList = mutableListOf<String>()

    private val _splitsState: MutableStateFlow<ImmutableList<String>> =
        MutableStateFlow(listOf("").toImmutableList())
    val splitsState: StateFlow<ImmutableList<String>> = _splitsState.asStateFlow()

    private val suspensionManager = SuspensionManager()

    private val _stopwatchState: MutableStateFlow<StopwatchState> =
        MutableStateFlow(StopwatchState.Stopped)
    val stopwatchState: StateFlow<StopwatchState> = _stopwatchState.asStateFlow()

    private val _tellTimeState: MutableStateFlow<StopwatchState> =
        MutableStateFlow(StopwatchState.TellTime(0))
    val timerState: StateFlow<StopwatchState> = _tellTimeState.asStateFlow()

    private var splitStopStopwatchState: StopwatchState = StopwatchState.Initial

    private val _splitStopButtonNameState: MutableStateFlow<String> =
        MutableStateFlow(SPLIT_BUTTON_NAME)
    val splitStopButtonNameState: StateFlow<String> = _splitStopButtonNameState.asStateFlow()

    private var pauseContinuePauseStopwatchState: StopwatchState = StopwatchState.Continue

    private val _pauseContinuePauseButtonNameState: MutableStateFlow<String> =
        MutableStateFlow(PAUSE_BUTTON_NAME)
    val pauseContinuePauseButtonNameState: StateFlow<String> =
        _pauseContinuePauseButtonNameState.asStateFlow()

    /** Нажатие на кнопку Start или Stop */
    fun onStopStartClick() {
        if (_stopwatchState.value == StopwatchState.Started) {
            // Stopping stopwatch
            _stopwatchState.value = StopwatchState.Stopped
            timerJob?.cancel()
            timerJob = null
            return
        }
        if (_stopwatchState.value == StopwatchState.Stopped) {
            // Starting stopwatch
            isPaused = false
            _splitStopButtonNameState.value = SPLIT_BUTTON_NAME
            _pauseContinuePauseButtonNameState.value = PAUSE_BUTTON_NAME
            _stopwatchState.value = StopwatchState.Started
            pauseContinuePauseStopwatchState = StopwatchState.Continue
            initialTime = System.currentTimeMillis()
            timerJob = tellTime()
            return
        }
    }

    /** Нажатие на кнопку Pause или Continue (может быть в двух состояниях) */
    fun onPauseOrContinueClick() {
        if (pauseContinuePauseStopwatchState == StopwatchState.Paused) {
            // Continue stopwatch
            isPaused = false
            suspensionManager.resume()
            pauseContinuePauseStopwatchState = StopwatchState.Continue
            _splitStopButtonNameState.value = SPLIT_BUTTON_NAME
            _pauseContinuePauseButtonNameState.value = PAUSE_BUTTON_NAME

            // Восстанавливаем время, добавляя разницу между паузой и возобновлением
            pausedTime += System.currentTimeMillis() - pauseStartTime
            return
        }
        if (pauseContinuePauseStopwatchState == StopwatchState.Continue) {
            // Pause stopwatch
            isPaused = true
            pauseContinuePauseStopwatchState = StopwatchState.Paused
            _splitStopButtonNameState.value = STOP_BUTTON_NAME
            _pauseContinuePauseButtonNameState.value = CONTINUE_BUTTON_NAME

            // Запоминаем время начала паузы
            pauseStartTime = System.currentTimeMillis()
            return
        }
    }

    /** Нажатие на кнопку Split или Stop (может быть в двух состояниях) */
    fun onSplitOrStopClick() {
        if (pauseContinuePauseStopwatchState == StopwatchState.Continue) {
            splitStopStopwatchState = StopwatchState.Split(currentTime)
            splitsList.add(
                (splitsList.size + 1).toString()
                        + ".  "
                        + (splitStopStopwatchState as StopwatchState.Split).time.formatTime()
            )
            _splitsState.value = splitsList.toImmutableList()
            return
        }
        if (pauseContinuePauseStopwatchState == StopwatchState.Paused) {
            _stopwatchState.value = StopwatchState.Stopped
            pausedTime = 0
            initialTime = 0
            currentTime = 0
            pauseStartTime = 0
            _tellTimeState.value = StopwatchState.TellTime(0)
            return
        }
    }

    private fun tellTime() =
        viewModelScope.launch {
            while (isActive) {
                try {
                    // Вычисляем время — в любом фоновом потоке
                    currentTime = System.currentTimeMillis() - initialTime - pausedTime

                    // Обновляем StateFlow — тоже можно в фоне!
                    _tellTimeState.value = StopwatchState.TellTime(currentTime)

                    // Ждём 10 мс (или 16 мс — зависит от нужной частоты обновления)
                    delay(16) // ~60 раз в секунду — достаточно для плавного UI

                    // Если приостановлен — уходим в "suspended" состояние
                    if (isPaused) {
                        suspensionManager.suspendIndefinitely()
                    }
                } catch (e: Exception) {
                    // Если получено исключение "Paused", просто выход из цикла
                    Log.e(TAG, e.message.orEmpty())
                    break
                }
            }
        }

    companion object {
        private const val TAG = "StopwatchViewmodel"
    }
}