package com.example.vladislav.androidstudy.kotlin.sometasks.stopwatch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vladislav.androidstudy.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Stable wrapper around [StateFlow] to prevent unnecessary recomposition when passing state to composables.
 *
 * [StateFlow] itself is *unstable* — Compose treats it as non-recomposition-safe. Passing it directly may
 * trigger recomposition on every recomposition pass, even if the value hasn't changed (e.g., if the flow
 * reference changes).
 *
 * This class avoids that by:
 *   • Wrapping `collectAsStateWithLifecycle()` inside a `@Stable` holder.
 *   • Ensuring recomposition happens only when the flow emits a *new value*.
 *
 * ⚠️ Must be created once per composition (e.g., via [remember]) and reused.
 * Creating it on every recomposition leaks resources (new collector per recomposition).
 */
@Stable
class StopwatchStateSource<T>(
    private val flow: StateFlow<T>
) {
    /**
     * Returns the current value from the underlying [StateFlow].
     * Recomposes only when the flow emits a new value.
     */
    @Composable
    fun getState(): T = flow.collectAsStateWithLifecycle().value
}

/**
 * Displays the stopwatch UI with performance-optimized recomposition.
 *
 * Architecture:
 *   • [TimeDisplay] recomposes only when time changes (~60 FPS).
 *   • Other parts (splits, buttons) recompose only when relevant state changes.
 *
 * @param stopwatchTime stable source for the current time string.
 * @param stopwatchStateSource stable source for current [StopwatchState].
 * @param stopSplitClickListener handler for stop/split button.
 * @param startTimeClickListener handler for start button.
 * @param pauseContinueClickListener handler for pause/continue button.
 *
 * ⚠️ [stopwatchTime] and [stopwatchStateSource] must be stable — created once (e.g., via [remember]).
 */
@Composable
fun StopwatchComposable(
    stopwatchTime: StopwatchStateSource<String>,
    stopwatchStateSource: StopwatchStateSource<StopwatchState>,
    stopSplitClickListener: () -> Unit,
    startTimeClickListener: () -> Unit,
    pauseContinueClickListener: () -> Unit,
) {
    val stopwatchState = stopwatchStateSource.getState()
    // ✅ Запоминаем данные, которые редко меняются
    val stopwatchData = remember(stopwatchState) {
        when (stopwatchState) {
            is StopwatchState.Paused -> stopwatchState.data
            is StopwatchState.Stopped -> stopwatchState.data
            is StopwatchState.Running -> stopwatchState.data
        }
    }

    val isStopped = remember(stopwatchState) {
        stopwatchState is StopwatchState.Stopped
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            elevation = 8.dp,
            color = Color.White,
            shape = MaterialTheme.shapes.medium
        ) {
            SplitsList(splits = stopwatchData.splits)
        }

        // ✅ BottomSection рекомпозируется, но кнопки внутри - нет
        BottomSection(
            stopwatchTime = stopwatchTime,
            isStopped = isStopped,
            leftButtonText = stopwatchData.leftButtonName,
            rightButtonText = stopwatchData.rightButtonName,
            onStartClick = startTimeClickListener,
            onPauseContinueClick = pauseContinueClickListener,
            onStopSplitClick = stopSplitClickListener
        )
    }
}

@Composable
private fun BottomSection(
    stopwatchTime: StopwatchStateSource<String>,
    isStopped: Boolean,
    leftButtonText: String,
    rightButtonText: String,
    onStartClick: () -> Unit,
    onPauseContinueClick: () -> Unit,
    onStopSplitClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ✅ Только этот компонент рекомпозируется при обновлении времени
        TimeDisplay(stopwatchTime = stopwatchTime)

        // ✅ Кнопки НЕ рекомпозируются
        if (isStopped) {
            Button(
                onClick = onStartClick,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(text = ButtonName.Start.name)
            }
        } else {
            ControlsRow(
                leftButtonText = leftButtonText,
                rightButtonText = rightButtonText,
                onLeftClick = onPauseContinueClick,
                onRightClick = onStopSplitClick
            )
        }
    }
}

// ✅ Этот компонент рекомпозируется только при изменении splits
@Composable
private fun SplitsList(splits: ImmutableList<String>) {
    val items = remember(splits) { splits }

    if (items.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = LocalContext.current.getString(R.string.no_splits_yet),
                color = Color.Gray,
                fontSize = 20.sp
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(
                items = items,
                key = { it.hashCode() }
            ) { split ->
                Text(
                    text = split,
                    fontSize = 32.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

// ✅ ТОЛЬКО этот компонент рекомпозируется 60 раз/сек
// ✅ TimeDisplay сам получает время через collectAsState
@Composable
private fun TimeDisplay(stopwatchTime: StopwatchStateSource<String>) {
    Text(
        text = stopwatchTime.getState(),
        style = TextStyle(fontSize = 48.sp),
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun ControlsRow(
    leftButtonText: String,
    rightButtonText: String,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = onLeftClick) {
            Text(text = leftButtonText)
        }
        Button(onClick = onRightClick) {
            Text(text = rightButtonText)
        }
    }
}


@Preview(
    name = "Stopwatch Preview",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    device = "id:pixel_4"
)
@Composable
fun PreviewStopwatchComposable() {
    // Создаём тестовые данные для превью
    val testSplits = persistentListOf(
        "01:23.45",
        "01:25.12",
        "01:28.33",
        "01:30.01"
    )

    // Создаём тестовый ViewModel (или используем мок)
    val testViewModel = StopwatchViewModel()

    // Создаём тестовое состояние
    val testState = StopwatchState.Running(
        data = StopwatchData(
            leftButtonName = "Pause",
            rightButtonName = "Split",
            splits = testSplits
        )
    )

    val testStateFlow: StateFlow<StopwatchState> = remember {
        MutableStateFlow<StopwatchState>(testState)
    }.asStateFlow()

    // ✅ Сохраняем источники между рекомпозициями
    val timeSource = remember { StopwatchStateSource(testViewModel.stopwatchTime) }
    val stateSource = remember { StopwatchStateSource(testStateFlow) }

    // MaterialTheme обёртка для корректного отображения
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            StopwatchComposable(
                stopwatchTime = timeSource,
                stopwatchStateSource = stateSource,
                stopSplitClickListener = { /* Тестовый клик */ },
                startTimeClickListener = { /* Тестовый клик */ },
                pauseContinueClickListener = { /* Тестовый клик */ }
            )
        }
    }
}

// Также можно добавить превью для пустого состояния
@Preview(
    name = "Stopwatch Preview - Empty",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun PreviewStopwatchComposableEmpty() {
    val testViewModel = StopwatchViewModel()
    val testState = StopwatchState.Stopped(
        data = StopwatchData(
            leftButtonName = "Continue",
            rightButtonName = "Stop",
            splits = persistentListOf() // Пустой список
        )
    )

    val testStateFlow: StateFlow<StopwatchState> = remember {
        MutableStateFlow<StopwatchState>(testState)
    }.asStateFlow()

    val timeSource = remember { StopwatchStateSource(testViewModel.stopwatchTime) }
    val stateSource = remember { StopwatchStateSource(testStateFlow) }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            StopwatchComposable(
                stopwatchTime = timeSource,
                stopwatchStateSource = stateSource,
                stopSplitClickListener = { /* Тестовый клик */ },
                startTimeClickListener = { /* Тестовый клик */ },
                pauseContinueClickListener = { /* Тестовый клик */ }
            )
        }
    }
}