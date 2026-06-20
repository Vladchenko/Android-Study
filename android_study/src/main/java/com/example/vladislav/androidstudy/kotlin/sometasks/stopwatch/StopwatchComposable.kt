package com.example.vladislav.androidstudy.kotlin.sometasks.stopwatch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A Jetpack Compose composable that renders a lap/split stopwatch UI.
 *
 * This composable is **pure UI**: it displays formatted time and splits provided by the ViewModel,
 * and forwards user actions to click handlers. No time formatting or business logic happens here.
 *
 * @param stopwatchState The current state of the stopwatch, containing all data needed for rendering:
 *        - `timeText`: pre-formatted current time (e.g., `"00:12.345"`)
 *        - `splits`: list of pre-formatted split/lap time strings
 *        - `leftButtonName`: label for the Pause/Continue button
 *        - `rightButtonName`: label for the Stop/Split button
 *        - and the base state (e.g., [StopwatchState.Started], [Stopped], etc.).
 * @param stopSplitClickListener A lambda invoked when the right (Stop/Split) button is clicked.
 * @param startTimeClickListener A lambda invoked when the Start button is clicked (only visible in [Stopped] state).
 * @param pauseContinueClickListener A lambda invoked when the left (Pause/Continue) button is clicked.
 *
 * UI structure:
 *   • Top (≈70% of height): A white, elevated `LazyColumn` showing all recorded splits as large text items.
 *   • Bottom (≈30% of height):
 *     - Current time (already formatted by the ViewModel).
 *     - Controls:
 *       - If `stopwatchState` is [StopwatchState.Stopped]: only a **Start** button is shown.
 *       - Otherwise: two buttons — **Pause/Continue** (left) and **Stop/Split** (right).
 *
 * Button labels (`leftButtonName`, `rightButtonName`) are taken directly from `stopwatchState.data`.
 *
 * ⚠️ **Contract with ViewModel**:
 *   - All `StopwatchState` variants must contain a `data: StopwatchData` property.
 *   - `data.timeText` must be a non-null, pre-formatted string (e.g., `"00:12.345"`).
 *   - `data.splits` must be a list of non-null, pre-formatted strings.
 *   - Button names must be descriptive and context-aware (e.g., `"Pause"` vs `"Resume"`).
 */
@Composable
fun StopwatchComposable(
    stopwatchState: StopwatchState,
    stopSplitClickListener: () -> Unit,
    startTimeClickListener: () -> Unit,
    pauseContinueClickListener: () -> Unit,
) {
    val stopwatchData = when (stopwatchState) {
        is StopwatchState.Paused -> stopwatchState.data
        is StopwatchState.Stopped -> stopwatchState.data
        is StopwatchState.Running -> stopwatchState.data
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            elevation = 8.dp,
            color = Color.White,
            shape = MaterialTheme.shapes.medium
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(stopwatchData.splits.size) { index ->
                    Text(
                        text = stopwatchData.splits[index],
                        fontSize = 32.sp
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = stopwatchData.time,
                fontSize = 48.sp
            )
            if (stopwatchState is StopwatchState.Stopped) {
                Button(onClick = startTimeClickListener) {
                    Text(text = START_BUTTON_NAME)
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = pauseContinueClickListener) {
                        Text(stopwatchData.leftButtonName)
                    }
                    Button(onClick = stopSplitClickListener) {
                        Text(stopwatchData.rightButtonName)
                    }
                }
            }
        }
    }
}