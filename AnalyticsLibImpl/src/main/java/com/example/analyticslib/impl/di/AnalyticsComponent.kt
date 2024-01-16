package com.example.analyticslib.impl.di

import com.example.analyticslib.api.di.AnalyticsApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AnalyticsModule::class])
interface AnalyticsComponent: AnalyticsApi {
    @Component.Factory
    interface Factory {
        fun create(): AnalyticsComponent
    }
}