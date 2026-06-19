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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vladislav.androidstudy.R
import kotlinx.collections.immutable.ImmutableList

/**
 * A Jetpack Compose composable that displays a lap/split stopwatch UI with list of splits
 * and control buttons (Start, Pause/Continue, Stop/Split).
 *
 * @param splitsItems An immutable list of formatted split/lap time strings (e.g., `"00:12.345"`).
 *        Each item represents a recorded split at a given moment.
 * @param tellTimeItems The current internal timer state, expected to be a [StopwatchState.TellTime]
 *        to extract the current elapsed time. If not, the UI will crash at runtime.
 * @param stopSplitButtonName The label to display on the Stop/Split button (e.g., "Stop", "Split").
 * @param pauseContinueButtonName The label for the Pause/Continue button (e.g., "Pause", "Resume").
 * @param stopwatchState The current high-level state of the stopwatch (e.g., [StopwatchState.Stopped]).
 *        Controls which controls are visible: only Start, or both Pause/Continue + Stop/Split.
 * @param stopSplitClickListener A lambda invoked when the Stop/Split button is clicked.
 * @param startTimeClickListener A lambda invoked when the Start button is clicked.
 * @param pauseContinueClickListener A lambda invoked when the Pause/Continue button is clicked.
 *
 * UI layout:
 *   - Top section: Scrollable `LazyColumn` of split/lap times (white surface, elevated).
 *   - Bottom section: Timer display + controls.
 *     - If [stopwatchState] is [TimerState.Stopped]: only "Start" button is shown.
 *     - Otherwise: row with two buttons — "Pause/Resume" and "Stop/Split".
 *
 * ⚠️ **Assumptions**:
 *   - `tellTimeItems` is always a [StopwatchState.TellTime]; casting with `as` will crash otherwise.
 *   - [formatTime] extension is defined on the time value (typically [Long] or [Duration]).
 */
@Composable
fun StopwatchComposable(
    splitsItems: ImmutableList<String>,
    tellTimeItems: StopwatchState,
    stopSplitButtonName: String,
    pauseContinueButtonName: String,
    stopwatchState: StopwatchState,
    stopSplitClickListener: () -> Unit,
    startTimeClickListener: () -> Unit,
    pauseContinueClickListener: () -> Unit,
) {
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
                items(splitsItems.size) { index ->
                    Text(
                        text = splitsItems[index],
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
                text = (tellTimeItems as? StopwatchState.TellTime)?.time?.formatTime()
                    ?: LocalContext.current.getString(
                        R.string.stopwatch_initial_time_format
                    ),
                fontSize = 48.sp
            )
            if (stopwatchState == StopwatchState.Stopped) {
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
                        Text(pauseContinueButtonName)
                    }
                    Button(onClick = stopSplitClickListener) {
                        Text(stopSplitButtonName)
                    }
                }
            }
        }
    }
}