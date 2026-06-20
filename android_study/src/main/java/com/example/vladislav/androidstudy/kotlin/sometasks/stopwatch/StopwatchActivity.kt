package com.example.vladislav.androidstudy.kotlin.sometasks.stopwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

/**
 * Main Activity responsible for displaying and managing the Stopwatch feature.
 *
 * This Activity uses Jetpack Compose for UI and delegates all business logic and state management
 * to [StopwatchViewModel]. It observes the ViewModel's [StopwatchState] and recomposes
 * the [StopwatchComposable] whenever the state changes.
 *
 * Key responsibilities:
 *   • Initializing the [StopwatchViewModel] via `viewModels()` delegate (scoped to Activity lifecycle).
 *   • Setting the Compose content and collecting the state with `collectAsState()`.
 *   • Wiring UI callbacks (`onSplitOrStopClick`, `onStopStartClick`, `onPauseOrContinueClick`)
 *     to corresponding ViewModel functions.
 *
 * This Activity is **stateless** — it only passes state and handlers to the Composable.
 * All time tracking, formatting, and business rules are handled in the ViewModel.
 */
class StopwatchActivity : ComponentActivity() {

    private val viewmodel by viewModels<StopwatchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val stopwatchState by viewmodel.stopwatchState.collectAsState()
            StopwatchComposable(
                stopwatchState,
                viewmodel::onSplitOrStopClick,
                viewmodel::onStopStartClick,
                viewmodel::onPauseOrContinueClick
            )
        }
    }
}