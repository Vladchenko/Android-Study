package com.example.vladislav.androidstudy.dagger2.example5.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vladislav.androidstudy.dagger2.example5.di.DaggerLoginComponent
import com.example.vladislav.androidstudy.dagger2.example5.di.LoginComponent
import com.example.vladislav.androidstudy.dagger2.example5.di.someotherfeature.SomeOtherFeature
import javax.inject.Inject

/**
 * Leaving activity empty, since no need to fill
 */
class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var someOtherFeatureType: SomeOtherFeature

    private lateinit var loginComponent: LoginComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        loginComponent = DaggerLoginComponent.builder().build()
        super.onCreate(savedInstanceState)
        loginComponent.inject(this)
        println(someOtherFeatureType)
    }
}