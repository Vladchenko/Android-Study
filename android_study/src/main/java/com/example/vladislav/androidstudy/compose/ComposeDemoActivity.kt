package com.example.vladislav.androidstudy.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/**
 * Android Jetpack Compose demo. The very Basics.
 */
class ComposeDemoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewMoreInfo()
        }
    }

    @Preview
    @Composable
    fun ViewMoreInfo()
    {
        Text("!")
    }
}