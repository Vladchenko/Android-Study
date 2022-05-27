package com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.repository.datasource

import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.models.WeatherForecastResponse
import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

/**
 * TODO
 */
interface WeatherForecastDataSource {
    suspend fun getWeatherForecastData(city: String): Response<WeatherForecastResponse>
}