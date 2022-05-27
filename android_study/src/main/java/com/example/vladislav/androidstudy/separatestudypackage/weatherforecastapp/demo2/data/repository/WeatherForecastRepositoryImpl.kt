package com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.repository

import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.converter.ResponseToResourceConverter
import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.models.WeatherForecastResponse
import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.repository.datasourceimpl.WeatherForecastDataSourceImpl
import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.util.Resource
import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.domain.WeatherForecastRepository

/**
 * TODO
 */
class WeatherForecastRepositoryImpl(private val weatherForecastDataSourceImpl: WeatherForecastDataSourceImpl,
                                    private val converter: ResponseToResourceConverter) : WeatherForecastRepository {
    override suspend fun getWeatherForecastData(city: String): Resource<WeatherForecastResponse> {
        return converter.convert(weatherForecastDataSourceImpl.getWeatherForecastData(city))
    }
}