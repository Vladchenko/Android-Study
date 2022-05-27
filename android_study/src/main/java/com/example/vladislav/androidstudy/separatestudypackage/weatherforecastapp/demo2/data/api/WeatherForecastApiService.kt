package com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.api

import com.example.vladislav.androidstudy.BuildConfig
import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.models.WeatherForecastResponse
import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * TODO
 */
interface WeatherForecastApiService {

    @GET("/weather")
    suspend fun getWeatherForecastResponse(
        @Query("q")
        city:String,
        @Query("appid")
        apiKey: String = BuildConfig.API_KEY
    ): Response<WeatherForecastResponse>
}