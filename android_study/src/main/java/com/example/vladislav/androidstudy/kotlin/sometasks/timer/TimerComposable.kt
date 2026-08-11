package com.example.vladislav.androidstudy.kotlin.sometasks.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow

/**
 * Stable wrapper around [StateFlow] for safe use as a composable parameter.
 *
 * [StateFlow] is unstable in Compose — passing it directly may cause recomposition
 * even when the *value* hasn't changed, if the *reference* changes (e.g., re-creation).
 * This class avoids that by encapsulating `collectAsStateWithLifecycle()` in a `@Stable`
 * holder, ensuring recomposition occurs **only** when the flow emits a new value.
 *
 * ⚠️ Must be created once (e.g., with [remember]) and never recreated per composition.
 */
@Stable
class ButtonLabelSource(
    private val flow: StateFlow<String>
) {
    @Composable
    fun getLabel(): String = flow.collectAsStateWithLifecycle().value
}

/**
 * A Composable UI component that displays a timer and provides controls to start/stop/resume it.
 *
 * @param timerValue The current timer value (typically in milliseconds) to be formatted and displayed.
 * @param stopButtonName The text label to show on the stop/reset button.
 * @param stopTimerClickListener A lambda invoked when the stop/reset button is clicked.
 * @param toggleTimerClickListener A lambda invoked when the start/pause/resume button is clicked.
 * @param toggleTimerButtonNameSource A [StateFlow] that emits the current label for the toggle button
 *        (e.g., "Start", "Pause", "Resume") based on the [TimerState].
 *
 * This composable observes [toggleTimerButtonNameSource] using `collectAsStateWithLifecycle()` to keep the button text up-to-date.
 * The timer value is assumed to be formatted using an extension function `.formatTime()`.
 */
@Composable
fun TimerComposable(
    timerValue: Long,
    stopButtonName: String,
    stopTimerClickListener: () -> Unit,
    toggleTimerClickListener: () -> Unit,
    toggleTimerButtonNameSource: ButtonLabelSource,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 32.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = timerValue.formatTime(),
            fontSize = 48.sp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = toggleTimerClickListener) {
                Text(toggleTimerButtonNameSource.getLabel())
            }
            Button(onClick = stopTimerClickListener) {
                Text(stopButtonName)
            }
        }
    }
}