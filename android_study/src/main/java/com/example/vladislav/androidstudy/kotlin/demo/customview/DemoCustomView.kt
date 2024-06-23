package com.example.vladislav.androidstudy.kotlin.demo.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

/**
 * Demo of simple custom view. Displays a small circles in circular manner.
 * Click on any area to change their color.
 *
 * @param context - Context of view
 * @param attributeSet - AttributeSet of view
 */
class DemoCustomView(
    context: Context,
    attributeSet: AttributeSet
) : View(context, attributeSet) {   // One could extend from, say, ImageView

    private var paintList = mutableListOf(Paint().apply {
        strokeWidth = 5f
        color = Color.RED
    }, Paint().apply {
        strokeWidth = 5f
        color = Color.GREEN
    }, Paint().apply {
        strokeWidth = 5f
        color = Color.BLUE
    })

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d("TestCustomView", "onMeasure")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.d("TestCustomView", "onLayout")
    }

    override fun onDraw(canvas: Canvas) {
        Log.d("TestCustomView", "onDraw")
        super.onDraw(canvas)
        for (i in 0.0..Math.PI * 2 step 0.2513) {
            canvas.drawCircle(
                (width / 2f + sin(i) * RADIUS).toFloat(),
                (height / 2f + cos(i) * RADIUS).toFloat(),
                45f,
                paintList[0]
            )
            canvas.drawCircle(
                (width / 2f + sin(i) * RADIUS).toFloat(),
                (height / 2f + cos(i) * RADIUS).toFloat(),
                30f,
                paintList[1]
            )
            canvas.drawCircle(
                (width / 2f + sin(i) * RADIUS).toFloat(),
                (height / 2f + cos(i) * RADIUS).toFloat(),
                15f,
                paintList[2]
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("TestCustomView", "onTouchEvent")
        Log.d("TestCustomView", event?.x.toString() + " " + event?.y.toString())
        shiftPaint(paintList)
        invalidate()
        return super.onTouchEvent(event)
    }

    private fun shiftPaint(paintList: MutableList<Paint>) {
        val firstItemPaint = Paint(paintList[0])
        for (i in 0 until paintList.lastIndex) {
            paintList[i] = paintList[i + 1]
        }
        paintList[paintList.lastIndex] = firstItemPaint
    }

    private infix fun ClosedRange<Double>.step(step: Double): Iterable<Double> {
        require(start.isFinite())
        require(endInclusive.isFinite())
        require(step > 0.0) { "Step must be positive, was: $step." }
        val sequence = generateSequence(start) { previous ->
            if (previous == Double.POSITIVE_INFINITY) return@generateSequence null
            val next = previous + step
            if (next > endInclusive) null else next
        }
        return sequence.asIterable()
    }

    companion object {
        private const val RADIUS = 400f
    }
}