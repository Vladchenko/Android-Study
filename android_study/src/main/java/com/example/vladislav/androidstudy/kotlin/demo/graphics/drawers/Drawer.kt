package com.example.vladislav.androidstudy.kotlin.demo.graphics.drawers

import android.graphics.Canvas
import android.graphics.Paint

interface Drawer {
    fun drawFigure(canvas: Canvas, paint: Paint)
}