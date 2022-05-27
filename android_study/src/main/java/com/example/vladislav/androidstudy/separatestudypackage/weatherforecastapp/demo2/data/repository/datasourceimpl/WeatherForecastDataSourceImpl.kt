package com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.repository.datasourceimpl

import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.api.WeatherForecastApiService
import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.models.WeatherForecastResponse
import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.repository.datasource.WeatherForecastDataSource
import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

/**
 * TODO
 */
class WeatherForecastDataSourceImpl(private val newsApiService: WeatherForecastApiService) : WeatherForecastDataSource {

    override suspend fun getWeatherForecastData(city:String): Response<WeatherForecastResponse> {
        return newsApiService.getWeatherForecastResponse(city)
    }
}