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
import kotlinx.collections.immutable.ImmutableList

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
                text = (tellTimeItems as StopwatchState.TellTime).time.formatTime(),
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