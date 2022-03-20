package com.example.vladislav.androidstudy.kotlin.demo.graphics

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import com.example.vladislav.androidstudy.R
import com.example.vladislav.androidstudy.kotlin.demo.graphics.models.Figure
import com.example.vladislav.androidstudy.kotlin.demo.graphics.models.Circle
import com.example.vladislav.androidstudy.kotlin.demo.graphics.models.Line
import com.example.vladislav.androidstudy.kotlin.demo.graphics.models.Rectangle
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

/**
 * TODO
 */
class MyCanvasView(context: Context) : View(context) {

    private var currentX = 0f
    private var currentY = 0f
    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f
    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap
    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop
    private val backgroundColor = ResourcesCompat.getColor(resources, R.color.colorBackground, null)
    private val drawColor = ResourcesCompat.getColor(resources, R.color.colorPaint, null)
    private val drawableItems = listOf(
        Circle(100f,90f,50f),
        Line(520f, 120f, 630f, 30f),
        Rectangle(340f, 40f, 460f, 60f),
        Figure(250f, 120f)
    )

    // Set up the paint with which to draw.
    private val paint = Paint().apply {
        color = drawColor
        // Smooths out edges of what is drawn without affecting shape.
        isAntiAlias = true
        // Dithering affects how colors with higher-precision than the device are down-sampled.
        isDither = true
        style = Paint.Style.STROKE // default: FILL
        strokeJoin = Paint.Join.ROUND // default: MITER
        strokeCap = Paint.Cap.ROUND // default: BUTT
        strokeWidth = STROKE_WIDTH // default: Hairline-width (really thin)
    }

    private var path = Path()

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(backgroundColor)
        // if (extraBitmap != null && !extraBitmap.isRecycled) {
        //     extraBitmap.recycle();
        // }
        // if (::extraBitmap.isInitialized) extraBitmap.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var dX = 0
        var dY = 0
        var radius = 200;
        var i = 0f
        // canvas.drawBitmap(extraBitmap, 0f, 0f, null)
        while (i < Math.PI * 2) {
            i += 0.01f
            dX = (sin(i) * radius).roundToInt()
            dY = (cos(i) * radius).roundToInt()
            canvas.drawLine(
                canvas.width / 2f,
                canvas.height / 2f,
                canvas.width / 2f + dX,
                canvas.height / 2f + dY, paint
            )
        }
        drawableItems.map {
            it.drawFigure(canvas, paint)    // Polymorphism demo
        }
    }

    companion object {
        private const val STROKE_WIDTH = 12f // has to be float
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }
        return true
    }

    private fun touchStart() {
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }

    private fun touchMove() {
        val dx = Math.abs(motionTouchEventX - currentX)
        val dy = Math.abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            // QuadTo() adds a quadratic bezier from the last point,
            // approaching control point (x1,y1), and ending at (x2,y2).
            path.quadTo(currentX, currentY, (motionTouchEventX + currentX) / 2, (motionTouchEventY + currentY) / 2)
            currentX = motionTouchEventX
            currentY = motionTouchEventY
            // Draw the path in the extra bitmap to cache it.
            extraCanvas.drawPath(path, paint)
        }
        invalidate()
    }

    private fun touchUp() {
        // Reset the path so it doesn't get drawn again.
        path.reset()
    }

}