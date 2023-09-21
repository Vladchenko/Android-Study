package com.example.vladislav.androidstudy.dagger2.example5.di.someotherfeature

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SomeOtherFeatureModule::class])
interface SomeOtherFeatureComponent {
    fun getSomeOtherFeatureDependency(): SomeOtherFeature
}