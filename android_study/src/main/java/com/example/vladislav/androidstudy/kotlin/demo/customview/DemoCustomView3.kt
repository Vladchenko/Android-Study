package com.example.vladislav.androidstudy.kotlin.demo.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import java.lang.Math.pow
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Demo of custom view that displays sectors in the center of screen resembling a menu.
 * This is an enhancement of DemoCustomView2. Each sector represents a clickable button that has a
 * different color and a black inscription on it. Button consists of a thick and thin border. Thin
 * border is drawn on top of a thicker one. Angle for each button is increased in a counterclockwise
 * direction beginning with a hand at 3pm position. Menu could be used as some audio/video player's
 * menu.
 *
 * @param context - Context of view
 * @param attributeSet - AttributeSet of view
 *
 * https://www.youtube.com/watch?v=AwaJTQB-hyY - drawTextOnPath example
 */
class DemoCustomView3(
    context: Context,
    attributeSet: AttributeSet
) : View(context, attributeSet) {

    private val sweepAngle = (Math.PI * 2 / SECTORS_NUMBER)
    private val pathsList = mutableListOf<Path>()

    private var index = 0
    private var radius = 0f
    private var centerX = 0f
    private var centerY = 0f
    private var currentAngle = 0.0
    private var previousButtonIndex = -1
    private var thinBorderAnglePairsList = mutableListOf<Pair<Float, Float>>()
    private var thickBorderAnglePairsList = mutableListOf<Pair<Float, Float>>()

    private val titlesList =
        listOf("Play", "Stop", "FF", "Rewind", "Pause", "Next", "Previous", "Open")

    // For thick borders
    private val paintList = MutableList(SECTORS_NUMBER) {
        Paint().apply {
            color = Color.argb(
                255,
                (80..200).random(),
                (80..200).random(),
                (80..200).random(),
            )
            style = Paint.Style.STROKE
            strokeWidth = SECTORS_BORDER_WIDTH
        }
    }

    // For thin borders
    private val paintList2 =
        paintList.toList().map {
            Paint().apply {
                color = Color.argb(
                    255,
                    it.color.red + (it.color.red / 4),
                    it.color.green + (it.color.green / 4),
                    it.color.blue + (it.color.blue / 4)
                )
                style = Paint.Style.STROKE
                strokeWidth = SECTORS_BORDER_WIDTH - 20
            }
        }.toMutableList()

    private val blackPaint = MutableList(SECTORS_NUMBER) {
        Paint().apply {
            color = Color.BLACK
            textSize = 48f
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w / 2f
        centerY = h / 2f
        radius = width.coerceAtMost(height) / 3f
        while (index < SECTORS_NUMBER) {
            pathsList.add(Path().apply {
                moveTo(
                    centerX + cos(currentAngle).toFloat() * (radius - TITLES_RADIUS_OFFSET),
                    centerY + sin(currentAngle).toFloat() * (radius - TITLES_RADIUS_OFFSET)
                )
                cubicTo(
                    centerX + cos(currentAngle).toFloat() * (radius - TITLES_RADIUS_OFFSET),
                    centerY + sin(currentAngle).toFloat() * (radius - TITLES_RADIUS_OFFSET),
                    centerX + cos(currentAngle + sweepAngle / 2).toFloat()
                            * (radius - TITLES_RADIUS_OFFSET) * 1.2f,
                    centerY + sin(currentAngle + sweepAngle / 2).toFloat()
                            * (radius - TITLES_RADIUS_OFFSET) * 1.2f,
                    centerX + cos(currentAngle + sweepAngle).toFloat()
                            * (radius - TITLES_RADIUS_OFFSET),
                    centerY + sin(currentAngle + sweepAngle).toFloat()
                            * (radius - TITLES_RADIUS_OFFSET),
                )
            })
            thickBorderAnglePairsList.add(
                Pair(
                    Math.toDegrees(currentAngle).toFloat() + SECTORS_GAP / 2,
                    Math.toDegrees(sweepAngle).toFloat() - SECTORS_GAP
                )
            )
            thinBorderAnglePairsList.add(
                Pair(
                    Math.toDegrees(currentAngle).toFloat() + SECTORS_GAP / 1.4f,
                    Math.toDegrees(sweepAngle).toFloat() - SECTORS_GAP * 1.45f
                )
            )
            currentAngle += sweepAngle
            index++
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        index = 0
        while (index < SECTORS_NUMBER) {
            // Thicker sector, drawn beneath a narrower one
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                thickBorderAnglePairsList[index].first,
                thickBorderAnglePairsList[index].second,
                false,
                paintList[index]
            )
            // Narrower sector, drawn on top of a thicker one
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                thinBorderAnglePairsList[index].first,
                thinBorderAnglePairsList[index].second,
                false,
                paintList2[index]
            )
            canvas.drawTextOnPath(
                titlesList[index],
                pathsList[index],
                getTextCircularOffset(titlesList[index]),
                0f,
                blackPaint[index]
            )
            index++
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("DemoCustomView3", "onTouchEvent X:${event?.x} Y:${event?.y}")
        if (event != null) {
            val buttonIndex = defineClickedButtonIndex(event.x, event.y)
            if (buttonIndex > -1) {
                turnPreviousButtonNotTapped()
                turnCurrentButtonTapped(buttonIndex)
                previousButtonIndex = buttonIndex
                invalidate()
            }
        }
        return super.onTouchEvent(event)
    }

    private fun turnCurrentButtonTapped(buttonIndex: Int) {
        blackPaint[buttonIndex] = Paint().apply {
            color = blackPaint[buttonIndex].color
            textSize = blackPaint[buttonIndex].textSize
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        }
    }

    private fun turnPreviousButtonNotTapped() {
        if (previousButtonIndex != -1) {
            blackPaint[previousButtonIndex] = Paint().apply {
                color = blackPaint[previousButtonIndex].color
                textSize = blackPaint[previousButtonIndex].textSize
                typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            }
        }
    }

    override fun onHoverEvent(event: MotionEvent?): Boolean {
        return super.onHoverEvent(event)
    }

    private fun getTextCircularOffset(text: String): Float {
        return if (text.length % 2 == 0) {
            (13 - text.length) * 10f
        } else {
            (12 - text.length) * 10f
        }
    }

    private fun defineClickedButtonIndex(x: Float, y: Float): Int {
        val (tappedAngle, tappedRadius) = computeTappedData(y, x)
        if (tappedRadius >= radius - SECTORS_BORDER_WIDTH / 2
            && tappedRadius <= radius + SECTORS_BORDER_WIDTH / 2
        ) {
            logTappedData(tappedAngle, tappedRadius)
            Log.d(TAG, "------ Buttons angles ----------------")
            thickBorderAnglePairsList.forEachIndexed { index, it ->
                Log.d(
                    TAG,
                    "\t[$index] [${it.first}..${it.first + it.second}], ${titlesList[index]}"
                )
                if (tappedAngle >= it.first
                    && tappedAngle <= it.first + it.second
                ) {
                    Log.d(TAG, "Tapped button - [$index], ${titlesList[index]}")
                    return index
                }
            }
            Log.d(TAG, "No button is tapped, since tapped position angle didn't match any button")
        } else {
            Log.d(
                TAG,
                "\tRadius\t$tappedRadius doesn't fall within ${radius - SECTORS_BORDER_WIDTH / 2}.."
                        + "${radius + SECTORS_BORDER_WIDTH / 2} pxs range. So no button is tapped."
            )
        }
        return -1
    }

    private fun computeTappedData(y: Float, x: Float): Pair<Float, Double> {
        var tappedAngle = atan2(centerY - y, x - centerX)
        val tappedRadius =
            sqrt(pow((centerX - x).toDouble(), 2.0) + pow((centerY - y).toDouble(), 2.0))
        if (y > centerY) {
            tappedAngle += Math.PI.toFloat() * 2
        }
        tappedAngle = makeAngleIncrementCounterClockwised(tappedAngle)
        tappedAngle = (tappedAngle * 180 / Math.PI).toFloat()
        return Pair(tappedAngle, tappedRadius)
    }

    private fun logTappedData(angle: Float, radiusForTappedPosition: Double) {
        Log.d(TAG, "------ Tapped spot data --------------")
        Log.d(TAG, "\tAngle\t$angle")
        Log.d(
            TAG,
            "\tRadius\t$radiusForTappedPosition falls within ${radius - SECTORS_BORDER_WIDTH / 2}.."
                    + "${radius + SECTORS_BORDER_WIDTH / 2} pxs range"
        )
    }

    private fun makeAngleIncrementCounterClockwised(angle: Float): Float {
        var newAngle = angle
        newAngle -= Math.PI.toFloat() * 2
        newAngle *= -1
        return newAngle
    }

    companion object {
        private const val SECTORS_GAP = 6
        private const val SECTORS_NUMBER = 8
        private const val TAG = "DemoCustomView3"
        private const val TITLES_RADIUS_OFFSET = 24
        private const val SECTORS_BORDER_WIDTH = 160f
    }
}