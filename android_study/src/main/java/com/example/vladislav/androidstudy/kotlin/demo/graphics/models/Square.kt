package com.example.vladislav.androidstudy.kotlin.demo.graphics.models

import android.graphics.Canvas
import android.graphics.Paint

/**
 * Represents a square (geometric figure)
 *
 * @param x1 x axis ordinate
 * @param y1 y axis ordinate
 * @param side lenght of a side
 */
class Square(
    override val x1: Float,
    override val y1: Float,
    private val side: Float,
): Figure() {
    override fun drawFigure(canvas: Canvas, paint: Paint) {
        canvas.drawRect(x1, y1, x1 + side, y1 + side, paint)
    }
}