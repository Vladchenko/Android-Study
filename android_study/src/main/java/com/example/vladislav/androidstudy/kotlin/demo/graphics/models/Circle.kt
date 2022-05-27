package com.example.vladislav.androidstudy.kotlin.demo.graphics.models

import android.graphics.Canvas
import android.graphics.Paint

/**
 * TODO
 */
class Circle(
    override val x1: Float,
    override val y1: Float,
    private val radius: Float
) : Figure() {
    override fun drawFigure(canvas: Canvas, paint: Paint) {
        canvas.drawCircle(x1, y1, radius, paint)
    }
}