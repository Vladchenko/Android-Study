package com.example.vladislav.androidstudy.kotlin.demo.graphics.models

import android.graphics.Canvas
import android.graphics.Paint
import com.example.vladislav.androidstudy.kotlin.demo.graphics.drawers.Drawable

/**
 * Represents an abstract geometric figure. This class is for a real shapes to be extended from.
 *
 *  @param x1 x axis ordinate
 *  @param y1 y axis ordinate
 */
open class Figure(
    open val x1: Float = 0f,
    open val y1: Float = 0f
) : Drawable {
    override fun drawFigure(canvas: Canvas, paint: Paint) {
        canvas.drawPoint(x1, y1, paint)
    }
}