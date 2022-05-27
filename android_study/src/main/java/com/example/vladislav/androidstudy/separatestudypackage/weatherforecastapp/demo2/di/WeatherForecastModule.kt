package com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.di

import android.app.Application
import com.example.vladislav.androidstudy.BuildConfig
import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.api.WeatherForecastApiService
import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.converter.ResponseToResourceConverter
import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.repository.WeatherForecastRepositoryImpl
import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.data.repository.datasourceimpl.WeatherForecastDataSourceImpl
import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.domain.WeatherForecastInteractor
import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.domain.WeatherForecastRepository
import com.example.vladislav.androidstudy.separatestudypackage.weatherforecastapp.demo2.presentation.viewmodel.WeatherForecastViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WeatherForecastModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
        }.build()
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherForecastApiService(retrofit: Retrofit): WeatherForecastApiService {
        return retrofit.create(WeatherForecastApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherForecastRemoteDataSource(weatherForecastApiService: WeatherForecastApiService): WeatherForecastDataSourceImpl {
        return WeatherForecastDataSourceImpl(weatherForecastApiService)
    }

    @Singleton
    @Provides
    fun provideConverter(): ResponseToResourceConverter {
        return ResponseToResourceConverter()
    }

    @Singleton
    @Provides
    fun provideWeatherForecastRepository(
        weatherForecastDataSourceImpl: WeatherForecastDataSourceImpl,
        converter: ResponseToResourceConverter
    ): WeatherForecastRepository {
        return WeatherForecastRepositoryImpl(weatherForecastDataSourceImpl, converter)
    }

    @Singleton
    @Provides
    fun provideWeatherForecastInteractor(weatherForecastRepository: WeatherForecastRepository): WeatherForecastInteractor {
        return WeatherForecastInteractor(weatherForecastRepository)
    }

    @Singleton
    @Provides
    fun provideViewModelFactory(app: Application,
                                weatherForecastInteractor: WeatherForecastInteractor): WeatherForecastViewModelFactory {
        return WeatherForecastViewModelFactory(
            app,
            weatherForecastInteractor)
    }
}