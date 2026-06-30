package com.example.vladislav.androidstudy.kotlin.sometasks.stopwatch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

/**
 * ViewModel managing the state and timing logic of a lap/split stopwatch.
 *
 * This ViewModel tracks elapsed time, handles Start/Pause/Resume/Stop/Split actions,
 * and exposes the current state ([StopwatchState]) to the UI layer.
 *
 * Time calculation:
 *   - Elapsed time = `System.currentTimeMillis() - initialTime - pausedTime`
 *   - Updates ~60 times per second (every 16 ms) while running.
 *   - Pauses are handled via [SuspensionManager], which suspends the timer coroutine
 *     without blocking the main thread.
 *
 * State transitions (simplified):
 *   Stopped ↔ Running ↔ Paused
 *
 * Formatting (`Long → String`) is performed in [formatTime] (external extension),
 * ensuring separation of concerns: ViewModel provides *formatted* strings for display.
 *
 * ⚠️ **Assumptions & Requirements**:
 *   - All time operations use [System.currentTimeMillis] for wall-clock tracking.
 *   - [formatTime] extension is available (e.g., on [Long]) and returns strings like `"MM:SS.mmm"`.
 *   - [SuspensionManager] provides `suspendIndefinitely()`, `resume()` methods.
 *   - No explicit thread-safety on `currentTime`/`pausedTime` is required —
 *     updates occur in a single `viewModelScope` coroutine.
 */
class StopwatchViewModel : ViewModel() {

    private var currentTime = 0L
    private var initialTime: Long = 0
    private var pausedTime: Long = 0
    private var pauseStartTime: Long = 0L
    private var lastEmittedMillis = -1L
    private var timerJob: Job? = null

    private val _stopwatchTime = MutableStateFlow(DEFAULT_TIME_STRING)
    val stopwatchTime: StateFlow<String> = _stopwatchTime.asStateFlow()

    private val _stopwatchState: MutableStateFlow<StopwatchState> =
        MutableStateFlow(
            StopwatchState.Stopped(
                StopwatchData(
                    leftButtonName = ButtonName.Start.name,
                    rightButtonName = ButtonName.Stop.name,
                    splits = persistentListOf()
                )
            )
        )

    /**
     * Exposes the current stopwatch state as an immutable [StateFlow].
     *
     * UI collects this to recompose on state changes. Values are complete snapshots
     * of [StopwatchState] ([Stopped], [Running], [Paused]) with pre-formatted time, splits, and button labels.
     */
    val stopwatchState: StateFlow<StopwatchState> = _stopwatchState.asStateFlow()

    private val suspensionManager = SuspensionManager()

    /**
     * Handles user clicks on the main button (Start or Stop).
     *
     * Transitions:
     *   - [Stopped] → [Running]
     *   - [Running] → [Stopped]
     *   - [Paused] is **not** handled — use [onPauseOrContinueClick] first.
     */
    fun onStopStartClick() {
        when (val currentState = _stopwatchState.value) {
            is StopwatchState.Running -> {
                // Stopping stopwatch
                _stopwatchState.value = StopwatchState.Stopped(currentState.data)
                timerJob?.cancel()
                timerJob = null
            }
            is StopwatchState.Stopped -> {
                // Starting stopwatch
                val state = (_stopwatchState.value as StopwatchState.Stopped)
                _stopwatchState.value = StopwatchState.Running(
                    data = state.data.copy(
                        leftButtonName = ButtonName.Pause.name,
                        rightButtonName = ButtonName.Split.name,
                    )
                )
                initialTime = System.currentTimeMillis()
                timerJob = tellTime()
            }
            is StopwatchState.Paused -> {
                // Do nothing
            }
        }
    }

    /**
     * Handles user clicks on the secondary button (Pause or Continue).
     *
     * Transitions:
     *   - [Running] → [Paused]
     *   - [Paused] → [Running]
     */
    fun onPauseOrContinueClick() {
        when (val currentState = _stopwatchState.value) {
            is StopwatchState.Paused -> {
                // Continue stopwatch
                _stopwatchState.value = StopwatchState.Running(
                    data = currentState.data.copy(
                        leftButtonName = ButtonName.Pause.name,
                        rightButtonName = ButtonName.Split.name,
                    )
                )
                suspensionManager.resume()

                // Restores elapsed time by adding the duration between pause and resume
                pausedTime += System.currentTimeMillis() - pauseStartTime
            }
            is StopwatchState.Running-> {
                // Pause stopwatch
                _stopwatchState.value = StopwatchState.Paused(
                    data = currentState.data.copy(
                        leftButtonName = ButtonName.Continue.name,
                        rightButtonName = ButtonName.Stop.name,
                    )
                )

                // Memorize pause begin time
                pauseStartTime = System.currentTimeMillis()
            }
            is StopwatchState.Stopped -> {
                // Do nothing
            }
        }
    }

    /**
     * Handles user clicks on the third button (Split or Stop).
     *
     * Transitions:
     *   - [Running] → [Running] (adds split)
     *   - [Paused] → [Stopped] (stops from paused state)
     */
    fun onSplitOrStopClick() {
        if (_stopwatchState.value is StopwatchState.Running) {
            val state = (_stopwatchState.value as StopwatchState.Running)
            // No need to pass to Split state, since Running can also provide splits
            _stopwatchState.value = StopwatchState.Running(
                data = state.data.copy(
                    splits = persistentListOf<String>()
                        .addAll(state.data.splits)
                        .add(currentTime.formatTime()),
                )
            )
            return
        }
        if (_stopwatchState.value is StopwatchState.Paused) {
            val state = (_stopwatchState.value as StopwatchState.Paused)
            _stopwatchState.value = StopwatchState.Stopped(
                data = state.data.copy(
                    splits = persistentListOf()
                )
            )
            pausedTime = 0
            initialTime = 0
            currentTime = 0
            pauseStartTime = 0
            _stopwatchTime.value = "0:00:00"
            return
        }
    }

    private fun tellTime() =
        viewModelScope.launch(Dispatchers.Main) {
            while (isActive) {
                try {
                    android.os.Trace.beginSection("UpdateTime")
                    // Computing time
                    currentTime = System.currentTimeMillis() - initialTime - pausedTime

                    // ✅ Обновляем только при реальном изменении
                    if (currentTime != lastEmittedMillis) {
                        android.os.Trace.beginSection("UpdateState")
                        lastEmittedMillis = currentTime
                        _stopwatchTime.value = currentTime.formatTime()
                        android.os.Trace.endSection()
                    }

                    // Wait for 16ms, that is about 60 times per second
                    delay(UPDATE_INTERVAL_MS)

                    // If stopped — leaving to "suspended" state
                    if (_stopwatchState.value is StopwatchState.Paused) {
                        suspensionManager.suspendIndefinitely()
                    }
                    android.os.Trace.endSection()
                } catch (e: CancellationException) {
                    Log.i(TAG,"Timer was cancelled.")
                    throw e
                }  catch (e: Exception) {
                    // If any exception, then just leave the loop
                    Log.e(TAG, e.message.orEmpty())
                    break
                }
            }
        }

    companion object {
        private const val TAG = "StopwatchViewmodel"
        private const val UPDATE_INTERVAL_MS = 16L
        private const val DEFAULT_TIME_STRING = "00:00:000"
    }
}