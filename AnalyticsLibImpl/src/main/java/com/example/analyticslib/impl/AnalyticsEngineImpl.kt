package com.example.analyticslib.impl

import com.example.analyticslib.api.AnalyticsEngine

/** Presumable analytics engine implementation */
class AnalyticsEngineImpl(): AnalyticsEngine {
    override fun sendEvent() {
        println("Analytics event sent")
    }
}