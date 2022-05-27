package com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.domain

import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.models.WeatherForecastResponse
import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * TODO
 */
class WeatherForecastInteractor(private val weatherForecastRepository: WeatherForecastRepository) {
    suspend fun execute(city: String): Resource<WeatherForecastResponse> {
        return weatherForecastRepository.getWeatherForecastData(city)
    }
}