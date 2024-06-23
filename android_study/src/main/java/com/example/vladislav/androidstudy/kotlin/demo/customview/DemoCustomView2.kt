package com.example.vladislav.androidstudy.kotlin.demo.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

/**
 * Demo of simple custom view. Displays sectors in circular manner.
 *
 * @param context - Context of view
 * @param attributeSet - AttributeSet of view
 */
class DemoCustomView2(
    context: Context,
    attributeSet: AttributeSet
) : View(context, attributeSet) {

    private var radius = 0f

    private val paintList = MutableList(SECTORS_NUMBER) {
        Paint().apply {
            strokeWidth = 2f
            color = Color.argb(
                255,
                (0..255).random(),
                (0..255).random(),
                (0..255).random(),
            )
        }
    }

    private val centerCirclePaint = Paint().apply {
        strokeWidth = 2f
        color = Color.WHITE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var index = 0
        radius = Math.min(width, height) / 2.7f
        val centerX = width / 2f
        val centerY = height / 2f
        var currentAngle = 0.0
        val sweepAngle = (Math.PI * 2 / SECTORS_NUMBER)
        while (index < SECTORS_NUMBER) {
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                Math.toDegrees(currentAngle).toFloat() + SECTORS_GAP / 2,
                Math.toDegrees(sweepAngle).toFloat() - SECTORS_GAP,
                true,
                paintList[index]
            )
            currentAngle += sweepAngle
            index++
        }
        canvas.drawCircle(centerX, centerY, radius * 0.6f, centerCirclePaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("TestCustomView", "onTouchEvent")
        Log.d("TestCustomView", event?.x.toString() + " " + event?.y.toString())
        shiftPaint(paintList)
        invalidate()
        return super.onTouchEvent(event)
    }

    override fun onHoverEvent(event: MotionEvent?): Boolean {
        return super.onHoverEvent(event)
    }

    private fun shiftPaint(paintList: MutableList<Paint>) {
        val firstItemPaint = Paint(paintList[0])
        for (i in 0 until paintList.lastIndex) {
            paintList[i] = paintList[i + 1]
        }
        paintList[paintList.lastIndex] = firstItemPaint
    }

    companion object {
        private const val SECTORS_NUMBER = 12
        private const val SECTORS_GAP = 2
    }
}