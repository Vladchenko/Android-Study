package com.example.vladislav.androidstudy.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
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

    @OptIn(ExperimentalMaterialApi::class)
    @Preview
    @Composable
    fun PreviewComposable() {
//        TextDemo()
//        TextCenterAlignment()
//        BoxSample()
//        BoxCenteredSample()
//        CardsPreview()
//        CardWithExpandableSubtitleItem()
        CardWithExpandableSubtitleItem2()
//        CounterDemo1()
//        CounterDemo2()
//        CounterDemo3()
//        CounterDemo4(counterState, { counterState.value++ })
//        CounterDemo5(counter, { counter++ })
//        var checked by remember { mutableStateOf(true) }
//        CheckBoxDemo("Some text", checked, Modifier, { checked = !checked })
//        val checked2 = remember { mutableStateOf(true) }
//        CheckBoxDemo2("Some text", checked2, Modifier, { checked2.value = !checked2.value })

//        val text = remember { mutableStateOf("") }
//        OutlinedTextFieldDemo(
//            value = text.value,
//            modifier = Modifier,
//            onValueChange = { newValue -> text.value = newValue }
//        )

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