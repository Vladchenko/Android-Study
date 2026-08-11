package com.example.vladislav.androidstudy.kotlin.sometasks.timer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlin.time.ExperimentalTime

/**
 * Main Activity for the Timer feature.
 *
 * This activity sets up the Jetpack Compose UI using [TimerComposable] and integrates
 * with [TimerViewModel] to manage the timer state and logic.
 *
 * It initializes the ViewModel, launches the timer countdown/counter on creation,
 * and composes the UI with observed state from the ViewModel.
 *
 * Note: Requires the experimental Kotlin time APIs (`@OptIn(ExperimentalTime::class)`).
 */
@ExperimentalTime
class TimerActivity : ComponentActivity() {

    private val viewmodel by viewModels<TimerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val timerElapsedStateValues by viewmodel.timerElapsedState.collectAsStateWithLifecycle()
            TimerComposable(
                timerElapsedStateValues,
                STOP_BUTTON_NAME,
                viewmodel::stopTimerAction,
                viewmodel::toggleTimer,
                ButtonLabelSource(viewmodel.triggerTimerButtonNameState),
            )
        }
    }
}