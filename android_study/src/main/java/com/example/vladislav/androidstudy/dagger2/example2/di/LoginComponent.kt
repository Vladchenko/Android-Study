package com.example.vladislav.androidstudy.dagger2.example2.di

import com.example.vladislav.androidstudy.dagger2.example2.presentation.LoginActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [LoginModule::class])
interface LoginComponent {
    fun inject(loginActivity: LoginActivity)
}