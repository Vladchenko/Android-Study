package com.example.vladislav.androidstudy.kotlin.sometasks.timer

/**
 * Represents all possible states of a timer.
 *
 * This sealed class defines the complete state machine for a timer:
 *   - [Initial]: The timer has not yet been started (initial/default state).
 *   - [Paused]: The timer was running but has been paused.
 *   - [Started]: The timer was started (first time) and is counting down/up.
 *   - [Resumed]: The timer was paused and has been resumed.
 *   - [Stopped]: The timer was stopped (typically reset to initial duration).
 *
 * Sealed classes are ideal here because they restrict the hierarchy to a known set
 * of states, enabling exhaustive `when` expressions in UI logic or business logic.
 */
sealed class TimerState {
    object Initial :TimerState()
    object Paused :TimerState()
    object Started :TimerState()
    object Resumed :TimerState()
    object Stopped :TimerState()
}