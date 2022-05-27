package com.example.vladislav.androidstudy.kotlin.demo.graphics.models

import android.graphics.Canvas
import android.graphics.Paint
import com.example.vladislav.androidstudy.kotlin.demo.graphics.drawers.Drawer

/**
 * This is class is for other shapes to be extended from.
 */
open class Figure(
    open val x1: Float = 0f,
    open val y1: Float = 0f
) : Drawer {
    override fun drawFigure(canvas: Canvas, paint: Paint) {
        canvas.drawPoint(x1, y1, paint)
    }
}