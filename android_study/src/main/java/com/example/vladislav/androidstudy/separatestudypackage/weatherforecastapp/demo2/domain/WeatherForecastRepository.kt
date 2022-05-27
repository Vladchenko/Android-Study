package com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.domain

import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.models.WeatherForecastResponse
import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * TODO
 */
interface WeatherForecastRepository {
    suspend fun getWeatherForecastData(city: String): Resource<WeatherForecastResponse>
}