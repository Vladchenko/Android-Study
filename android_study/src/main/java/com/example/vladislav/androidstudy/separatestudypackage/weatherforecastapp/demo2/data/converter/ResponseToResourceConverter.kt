package com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.converter

import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.models.WeatherForecastResponse
import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.util.Resource
import retrofit2.Response

/**
 * Converting Response to Resource
 */
class ResponseToResourceConverter {

    fun convert(response: Response<WeatherForecastResponse>): Resource<WeatherForecastResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(Throwable(response.message()))
    }

}