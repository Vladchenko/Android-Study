package com.example.vladislav.androidstudy.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CheckBoxDemo(
    text: String,
    check: Boolean,
    modifier: Modifier,
    onCheckedChange: (Boolean) -> Unit,) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = check, onCheckedChange = onCheckedChange)
        Text(text, modifier.clickable { onCheckedChange.invoke(check) })
    }
}

@Composable
fun CheckBoxDemo2(
    text: String,
    checkState: State<Boolean>,
    modifier: Modifier,
    onCheckedChange: (Boolean) -> Unit,) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checkState.value, onCheckedChange = onCheckedChange)
        Text(text, modifier.clickable { onCheckedChange.invoke(checkState.value) })
    }
}