package com.example.vladislav.androidstudy.kotlin.demo.graphics.models

import android.graphics.Canvas
import android.graphics.Paint

/**
 * Represents a rectangle (geometric figure)
 *
 * @param x1 x axis initial ordinate
 * @param y1 y axis initial ordinate
 * @param x2 x axis terminal ordinate
 * @param y2 y axis terminal ordinate
 */
class Rectangle(
    override val x1: Float,
    override val y1: Float,
    private val x2: Float,
    private val y2: Float
) : Figure(), Areable {
    override fun drawFigure(canvas: Canvas, paint: Paint) {
        canvas.drawRect(x1, y1, x2, y2, paint)
    }

    override fun calculateArea() = (x1-x2)*(y1-y2).toDouble()
}