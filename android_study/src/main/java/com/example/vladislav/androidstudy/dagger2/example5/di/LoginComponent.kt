package com.example.vladislav.androidstudy.dagger2.example5.di

import com.example.vladislav.androidstudy.dagger2.example5.di.someotherfeature.SomeOtherFeatureApi
import com.example.vladislav.androidstudy.dagger2.example5.presentation.LoginActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [LoginModule::class], dependencies = [SomeOtherFeatureApi::class])
interface LoginComponent {
    fun inject(loginActivity: LoginActivity)
}