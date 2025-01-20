package com.example.vladislav.androidstudy.compose

import OutlinedTextFieldDemo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * Android Jetpack Compose demo. The very Basics.
 */
class ComposeDemoActivity : ComponentActivity() {

    private val counterState = mutableIntStateOf(0)
    private var counter by mutableIntStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PreviewComposable()
        }
    }

    @Preview
    @Composable
    fun PreviewComposable() {
//        CardsSample()
//        BoxSample()
//        TextDemo()
//        CounterDemo1()
//        CounterDemo2()
//        CounterDemo3()
//        CounterDemo4(counterState, { counterState.value++ })
//        CounterDemo5(counter, { counter++ })
//        var checked by remember { mutableStateOf(true) }
//        CheckBoxDemo("Some text", checked, Modifier, { checked = !checked })
//        val checked2 = remember { mutableStateOf(true) }
//        CheckBoxDemo2("Some text", checked2, Modifier, { checked2.value = !checked2.value })

        val text = remember { mutableStateOf("") }
        OutlinedTextFieldDemo(
            value = text.value,
            modifier = Modifier,
            onValueChange = { newValue -> text.value = newValue }
        )
//        TextCenterAlignment()
//        Pager(modifier = Modifier)
//        Row {
//            Column {
//                Text("Hello World")
//                Text("Hello World")
//                Text("Hello World")
//            }
//            Row {
//                Text("Hello World")
//                Text("Hello World")
//                Text("Hello World")
//            }
//        }
    }
}