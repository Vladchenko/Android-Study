package com.example.vladislav.androidstudy.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun CounterDemo1() {
    var counter = 0     // This var doesn't get updated
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { counter++ },
        text = counter.toString(),
        textAlign = TextAlign.Center,
        fontSize = 64.sp
    )
}

@Composable
fun CounterDemo2() {
    val counter = mutableStateOf(0)     // This val doesn't get updated also
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { counter.value++ },
        text = counter.value.toString(),
        textAlign = TextAlign.Center,
        fontSize = 64.sp
    )
}

@Composable
fun CounterDemo3() {
    val counter = remember { mutableStateOf(0) }     // This val gets updated
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { counter.value++ },
        text = counter.value.toString(),
        textAlign = TextAlign.Center,
        fontSize = 64.sp
    )
}

@Composable
fun CounterDemo4(
    counterState: State<Int>,
    onClick: () -> Unit
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        text = counterState.value.toString(),
        textAlign = TextAlign.Center,
        fontSize = 64.sp
    )
}

@Composable
fun CounterDemo5(
counter: Int,
onClick: () -> Unit
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        text = counter.toString(),
        textAlign = TextAlign.Center,
        fontSize = 64.sp
    )
}