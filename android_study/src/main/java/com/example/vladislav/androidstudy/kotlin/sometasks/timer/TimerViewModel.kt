package com.example.vladislav.androidstudy.kotlin.sometasks.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

/**
 * ViewModel managing the logic and state of a countdown/count-up timer.
 *
 * This ViewModel uses [TimerState] to represent the timer's lifecycle and emits updates to:
 *   - [timerElapsedState]: current elapsed time in milliseconds (as [Long])
 *   - [triggerTimerButtonNameState]: text for the main button (e.g., "Start", "Pause", "Resume")
 *
 * It implements pausing/resuming logic via a [Mutex] to suspend/resume the timer coroutine safely.
 * The timer runs on a dedicated coroutine launched via [launchTimer] and updates elapsed time
 * every ~33 ms using [TimeSource.Monotonic].
 *
 * Key states:
 *   - [Initial]: Timer not yet started; elapsed time is 0.
 *   - [Started]: Timer is running and updating elapsed time.
 *   - [Paused]: Timer is paused (coroutine blocked via [pauseMutex]).
 *   - [Resumed]: Timer resumed after a pause, with adjusted mark time.
 *   - [Stopped]: Timer job cancelled and state reset to initial.
 *
 * Note: Requires `@OptIn(ExperimentalTime::class)` due to usage of Kotlin time APIs.
 */
@ExperimentalTime
class TimerViewModel : ViewModel() {

    private var timerJob: Job? = null
    private val pauseMutex = Mutex()
    private var mark = TimeSource.Monotonic.markNow()
    private var pausedDuration: Duration = Duration.ZERO

    private val _timerElapsedState = MutableStateFlow(0L)
    /**
     * Exposes the current elapsed time of the timer in milliseconds.
     *
     * The value is updated approximately every 33 ms while the timer is running.
     * Reset to `0` on [TimerState.Stopped] or [TimerState.Initial].
     */
    val timerElapsedState: StateFlow<Long> = _timerElapsedState.asStateFlow()

    private val _triggerTimerButtonNameState = MutableStateFlow(START_BUTTON_NAME)
    /**
     * Exposes the current label for the main toggle button.
     *
     * Values depend on the current [TimerState]:
     *   - `START_BUTTON_NAME` ("Start") for [TimerState.Initial], [TimerState.Stopped]
     *   - `PAUSE_BUTTON_NAME` ("Pause") for [TimerState.Started], [TimerState.Resumed]
     *   - `CONTINUE_BUTTON_NAME` ("Resume") for [TimerState.Paused]
     */
    val triggerTimerButtonNameState: StateFlow<String> = _triggerTimerButtonNameState.asStateFlow()

    private val _timerState = MutableStateFlow<TimerState>(TimerState.Initial)

    init {
        observeTimerState()
    }

    /**
     * Toggles the timer state forward through its lifecycle.
     *
     * State transitions:
     *   - `Initial → Started`
     *   - `Started → Paused`
     *   - `Paused → Resumed`
     *   - `Resumed → Paused`
     *   - `Stopped → Started`
     *
     * Calling this method does **not** directly start/stop the timer — it only
     * emits the next state to `_timerState`. Actual logic is handled in `observeTimerState()`.
     */
    fun toggleTimer() {
        when (_timerState.value) {
            TimerState.Initial -> _timerState.value = TimerState.Started
            TimerState.Started -> _timerState.value = TimerState.Paused
            TimerState.Paused -> _timerState.value = TimerState.Resumed
            TimerState.Resumed -> _timerState.value = TimerState.Paused
            TimerState.Stopped -> _timerState.value = TimerState.Started
        }
    }

    /**
     * Immediately stops and resets the timer to initial state.
     *
     * Effects:
     *   - Cancels the timer coroutine (`timerJob`)
     *   - Unlocks the pause mutex (in case it was locked)
     *   - Resets elapsed time to `0L`
     *   - Sets button label to "Start"
     *
     * After calling this, the timer is effectively in `Stopped` state.
     * To start again, call [toggleTimer()] (which transitions `Stopped → Started`).
     *
     * Equivalent to [toggleTimer()] followed by a direct `Stopped` transition.
     */
    fun stopTimerAction() {
        _timerState.value = TimerState.Stopped
    }

    private fun observeTimerState() {
        viewModelScope.launch {
            _timerState.collect { state ->
                when (state) {
                    TimerState.Initial -> resetTimer()
                    TimerState.Started -> startTimer()
                    TimerState.Paused -> pauseTimer()
                    TimerState.Resumed -> resumeTimer()
                    TimerState.Stopped -> stopTimer()
                }
            }
        }
    }

    private fun startTimer() {
        if (timerJob?.isActive == true) return

        mark = TimeSource.Monotonic.markNow()
        _triggerTimerButtonNameState.value = PAUSE_BUTTON_NAME

        timerJob = viewModelScope.launch {
            while (isActive) {
                pauseMutex.withLock { } // Ждем, если на паузе
                delay(16)
                _timerElapsedState.value = mark.elapsedNow().inWholeMilliseconds
            }
        }
    }

    private fun pauseTimer() {
        if (!pauseMutex.isLocked) {
            pauseMutex.tryLock()
            pausedDuration = mark.elapsedNow()
            _triggerTimerButtonNameState.value = CONTINUE_BUTTON_NAME
        }
    }

    private fun resumeTimer() {
        if (pauseMutex.isLocked) {
            pauseMutex.unlock()
            mark = TimeSource.Monotonic.markNow() - pausedDuration
            _triggerTimerButtonNameState.value = PAUSE_BUTTON_NAME
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
        if (pauseMutex.isLocked) {
            pauseMutex.unlock()
        }
        _timerElapsedState.value = 0L
        _triggerTimerButtonNameState.value = START_BUTTON_NAME
        pausedDuration = Duration.ZERO
        mark = TimeSource.Monotonic.markNow()
    }

    private fun resetTimer() {
        stopTimer()
        _timerState.value = TimerState.Initial
    }
}