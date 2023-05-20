package com.example.vladislav.androidstudy.kotlin.demo.graphics.drawers

import android.graphics.Canvas
import android.graphics.Paint

/** Interface to draw a figure. */
interface Drawable {
    /** Draw a figure on a [canvas] with a [paint]. */
    fun drawFigure(canvas: Canvas, paint: Paint)
}