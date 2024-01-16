package com.example.vladislav.androidstudy.dagger2.example6.di

import com.example.analyticslib.api.di.AnalyticsApi
import com.example.vladislav.androidstudy.dagger2.example6.presentation.LoginActivity
import dagger.Component
import javax.inject.Singleton

// @Singleton
@Component(modules = [LoginModule::class], dependencies = [LoginDependencies::class])
interface LoginComponent {

    fun inject(loginActivity: LoginActivity)

    /** dagger компонент, предоставляющий зависимости для {@link LoginComponent} */
    @Component(dependencies = [AnalyticsApi::class])
    interface LoginDependenciesComponent: LoginDependencies {
        @Component.Factory
        interface Factory {
            fun create(analyticsApi: AnalyticsApi): LoginDependenciesComponent
        }
        // @Component.Builder
        // interface Builder {
        //     @BindsInstance
        //     fun create(analyticsApi: AnalyticsApi): Builder
        //     fun build(): LoginDependenciesComponent
        // }
    }

    @Singleton
    @Component.Factory
    interface Factory {
        fun create(loginDependencies: LoginDependencies): LoginComponent
    }
}