package com.example.vladislav.androidstudy.dagger2.example5.di.someotherfeature

/**
 * This is an api that some other feature is to provide.
 * In fact, it should lie in an api gradle module of that feature.
 */
interface SomeOtherFeatureApi {
    fun getOtherFeatureSomeDependency(): SomeOtherFeature
}