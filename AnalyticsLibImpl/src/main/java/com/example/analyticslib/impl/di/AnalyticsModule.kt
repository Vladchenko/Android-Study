package com.example.analyticslib.impl.di

import com.example.analyticslib.api.AnalyticsEngine
import com.example.analyticslib.impl.AnalyticsEngineImpl
import dagger.Module
import dagger.Provides

/** Dagger module */
@Module
class AnalyticsModule {

    // @Binds
    // fun bindAnalyticsEngine(analyticsEngineImpl: AnalyticsEngineImpl): AnalyticsEngine

    @Provides
    fun provideAnalyticsEngine(): AnalyticsEngine {
        return AnalyticsEngineImpl()
    }
}