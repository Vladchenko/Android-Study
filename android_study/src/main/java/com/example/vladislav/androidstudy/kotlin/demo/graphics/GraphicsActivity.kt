package com.example.vladislav.androidstudy.kotlin.demo.graphics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import com.example.vladislav.androidstudy.R

/**
 * Android graphics exmple taken from
 * https://developer.android.com/codelabs/advanced-android-kotlin-training-canvas#5
 */
class GraphicsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myCanvasView = MyCanvasView(this)
        myCanvasView.systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN
        myCanvasView.contentDescription = getString(R.string.canvasContentDescription)
        setContentView(myCanvasView)
    }
}