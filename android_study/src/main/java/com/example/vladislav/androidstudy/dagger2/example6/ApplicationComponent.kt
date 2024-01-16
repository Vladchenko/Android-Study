// package com.example.vladislav.androidstudy.dagger2.example6
//
// import com.example.analyticslib.api.di.AnalyticsApi
// import com.example.vladislav.androidstudy.dagger2.example6.di.LoginComponent
// import dagger.Component
// import javax.inject.Singleton
//
// @Singleton
// @Component(
//     dependencies = [
//         LoginComponent::class,
//         AnalyticsApi::class]
// )
// interface ApplicationComponent {
//
//     @Component.Factory
//     interface Factory {
//         fun create(analyticsApi: AnalyticsApi): ApplicationComponent
//     }
// }