package com.example.vladislav.androidstudy.dagger2.example3.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vladislav.androidstudy.dagger2.example3.di.DaggerLoginComponent
import com.example.vladislav.androidstudy.dagger2.example3.di.LoginComponent
import com.example.vladislav.androidstudy.dagger2.example3.interactor.LoginInteractorImpl
import com.example.vladislav.androidstudy.dagger2.example3.models.LoginRequestModel
import javax.inject.Inject

/**
 * Empty activity for now
 */
class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var loginInteractor: LoginInteractorImpl

    private lateinit var loginComponent: LoginComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        loginComponent = DaggerLoginComponent.builder().build()
        super.onCreate(savedInstanceState)
        loginComponent.inject(this)
        println(loginInteractor.getLoginData(LoginRequestModel("Vladchenko","somePassword")))
    }
}