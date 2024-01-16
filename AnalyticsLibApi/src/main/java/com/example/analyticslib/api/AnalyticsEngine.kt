package com.example.analyticslib.api

/** Presumable analytics engine interface */
interface AnalyticsEngine {
    /** Send some analytics event */
    fun sendEvent()
}