package com.example.vladislav.androidstudy.kotlin.demo.graphics.models

import android.graphics.Canvas
import android.graphics.Paint

/**
 * Represents a circle (geometric figure)
 *
 * @param x1 x axis initial ordinate
 * @param y1 y axis initial ordinate
 * @param radius a circle's radius
 */
class Circle(
    override val x1: Float,
    override val y1: Float,
    private val radius: Float
) : Figure(), Areable {
    override fun drawFigure(canvas: Canvas, paint: Paint) {
        canvas.drawCircle(x1, y1, radius, paint)
    }

    override fun calculateArea() = Math.PI * radius * radius
}