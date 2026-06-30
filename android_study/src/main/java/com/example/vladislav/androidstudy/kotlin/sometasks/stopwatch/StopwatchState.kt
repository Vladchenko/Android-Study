package com.example.vladislav.androidstudy.kotlin.sometasks.stopwatch

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList

/**
 * Represents all possible states of the stopwatch and the data required to render its UI.
 *
 * This sealed class encodes the stopwatch lifecycle: [Running], [Paused], and [Stopped].
 * Each state contains a [StopwatchData] object that provides:
 *   - `time`: pre-formatted current elapsed time (e.g., `"00:12.345"`)
 *   - `splits`: immutable list of recorded split times
 *   - `leftButtonName`: context-aware label for the Pause/Resume button
 *   - `rightButtonName`: context-aware label for the Stop/Split button
 *
 * The UI layer (`StopwatchComposable`) uses this sealed hierarchy to render:
 *   - the list of splits (top half)
 *   - the current time (formatted by ViewModel)
 *   - the appropriate set of buttons based on state.
 *
 * ⚠️ **Contract**:
 *   - All [Running] and [Paused] states must include non-empty [splits] if splits were ever recorded.
 *   - [Stopped] always resets [splits] and sets [time] to `"00:00.000"`.
 *   - Button names must reflect current context (e.g., `"Pause"` in [Running] → `"Resume"` in [Paused]).
 */
@Stable
sealed class StopwatchState {

    /**
     * The stopwatch is currently paused.
     *
     * Time display remains frozen; buttons allow resuming or stopping.
     */
    @Stable
    data class Paused(val data: StopwatchData) : StopwatchState()

    /**
     * The stopwatch has been stopped and reset.
     *
     * Time is zero, splits are cleared, and only the "Start" button is valid.
     */
    @Stable
    data class Stopped(val data: StopwatchData) : StopwatchState()

    /**
     * The stopwatch is actively counting time.
     *
     * Time updates continuously; buttons allow pausing or recording a split.
     */
    @Stable
    data class Running(val data: StopwatchData) : StopwatchState()
}

/**
 * Immutable container for all data needed to render the stopwatch UI.
 *
 * This class is part of the [StopwatchState] hierarchy and holds:
 *   - `splits`: read-only list of recorded split times, each pre-formatted
 *   - `leftButtonName`: label for the primary control (Pause/Resume), e.g., `"Pause"`
 *   - `rightButtonName`: label for secondary control (Split/Stop), e.g., `"Split"`
 *
 * It is designed to be:
 *   • immutable (all properties are `val`)
 *   • type-safe (passed as part of [StopwatchState])
 *   • UI-ready (strings are already formatted, lists are pre-persisted).
 */
@Stable
data class StopwatchData(
    val splits: ImmutableList<String>,
    val leftButtonName: String,
    val rightButtonName: String
)

/**
 * Defines the set of possible button labels for the stopwatch UI.
 *
 * This enum ensures type-safe, consistent naming of controls across all stopwatch states:
 *   - [Start]: Activates the timer (only visible in [Stopped] state).
 *   - [Pause]: Suspends time tracking (visible in [Running] state).
 *   - [Continue]: Resumes from pause (visible in [Paused] state).
 *   - [Split]: Records a lap time (visible in [Running] state).
 *   - [Stop]: Resets timer and clears splits (visible in [Running] and [Paused] states).
 *
 * All button names are used directly in [StopwatchData] for UI rendering.
 *
 * ⚠️ **Contract**:
 *   • Only these five values must be used — no custom labels.
 *   • Each name corresponds to a specific action and UI context.
 *   • Enum values are stable and safe to store/transmit.
 */
@Stable
enum class ButtonName { Start, Pause, Continue, Split, Stop }