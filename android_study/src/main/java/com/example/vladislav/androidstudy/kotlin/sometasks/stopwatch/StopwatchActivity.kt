package com.example.vladislav.androidstudy.kotlin.sometasks.stopwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

/**
 * Активити секундомера
 */
class StopwatchActivity : ComponentActivity() {

    private val viewmodel = StopwatchViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val timerStateValues by viewmodel.timerState.collectAsState()
            val splitsStateValues by viewmodel.splitsState.collectAsState()
            val stopwatchStateValues by viewmodel.stopwatchState.collectAsState()
            val splitStopButtonNameStateValues by viewmodel.splitStopButtonNameState.collectAsState()
            val pauseContinuePauseButtonNameStateValues by viewmodel.pauseContinuePauseButtonNameState.collectAsState()
            StopwatchComposable(
                splitsStateValues,
                timerStateValues,
                splitStopButtonNameStateValues,
                pauseContinuePauseButtonNameStateValues,
                stopwatchStateValues,
                viewmodel::onSplitOrStopClick,
                viewmodel::onStopStartClick,
                viewmodel::onPauseOrContinueClick
            )
        }
    }
}