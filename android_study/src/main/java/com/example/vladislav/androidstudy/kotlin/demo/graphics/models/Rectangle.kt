package com.example.vladislav.androidstudy.kotlin.demo.graphics.models

import android.graphics.Canvas
import android.graphics.Paint

/**
 * TODO
 */
class Rectangle(
    override val x1: Float,
    override val y1: Float,
    private val x2: Float,
    private val y2: Float
) : Figure() {
    override fun drawFigure(canvas: Canvas, paint: Paint) {
        canvas.drawRect(x1, y1, x2, y2, paint)
    }
}