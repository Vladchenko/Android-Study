package com.example.vladislav.androidstudy.dagger2.example5.di.someotherfeature

import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck

@Module
@DisableInstallInCheck
interface SomeOtherFeatureModule {

    @Binds
    fun bindSomeOtherFeatureTypeToItsImpl(impl: SomeOtherFeatureImpl):SomeOtherFeature
}