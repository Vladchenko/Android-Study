package com.example.analyticslib.api.di

import com.example.analyticslib.api.AnalyticsEngine

/** Api to provide some functionality to other modules. */
interface AnalyticsApi {
    fun getAnalyticsEngine(): AnalyticsEngine
}