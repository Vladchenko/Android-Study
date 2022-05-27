package com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.domain.WeatherForecastInteractor

/**
 * TODO
 */
class WeatherForecastViewModelFactory(
    private val app: Application,
    private val weatherForecastInteractor: WeatherForecastInteractor
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherForecastViewModel(
            app,
            weatherForecastInteractor
        ) as T
    }
}