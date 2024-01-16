package com.example.vladislav.androidstudy.dagger2.example6.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.analyticslib.api.AnalyticsEngine
import com.example.vladislav.androidstudy.dagger2.example6.di.LoginComponent
import javax.inject.Inject

/** Empty activity (no contents, since for study purposes only) */
class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var analyticsEngine: AnalyticsEngine

    private lateinit var loginComponent: LoginComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        // loginComponent = DaggerLoginComponent.factory().create(
        //     DaggerLoginComponent_LoginDependenciesComponent.factory().create()
        // )
        super.onCreate(savedInstanceState)
        // loginComponent.inject(this)
        // println(analyticsEngine)
    }
}