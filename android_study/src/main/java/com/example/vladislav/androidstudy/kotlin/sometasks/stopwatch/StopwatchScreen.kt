package com.example.vladislav.androidstudy.kotlin.sometasks.stopwatch

import androidx.compose.runtime.Composable

/**
 * A thin composable screen that wires the [StopwatchViewModel] to the UI.
 *
 * Collects the latest [StopwatchState] from the ViewModel and passes it,
 * along with action callbacks, to [StopwatchComposable].
 *
 * This screen follows the **container pattern**: it handles state observation and
 * action wiring, leaving pure UI rendering to [StopwatchComposable].
 *
 * @param viewModel the [StopwatchViewModel] instance providing state and actions.
 */
@Composable
fun StopwatchScreen(viewModel: StopwatchViewModel) {

    StopwatchComposable(
        stopwatchTime = StopwatchStateSource(viewModel.stopwatchTime),
        stopwatchStateSource = StopwatchStateSource(viewModel.stopwatchState),
        stopSplitClickListener = viewModel::onSplitOrStopClick,
        startTimeClickListener = viewModel::onStopStartClick,
        pauseContinueClickListener = viewModel::onPauseOrContinueClick
    )
}