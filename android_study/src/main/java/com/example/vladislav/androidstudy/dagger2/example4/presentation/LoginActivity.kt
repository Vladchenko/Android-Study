package com.example.vladislav.androidstudy.dagger2.example4.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vladislav.androidstudy.dagger2.example4.di.DaggerLoginComponent
import com.example.vladislav.androidstudy.dagger2.example4.di.LoginComponent
import com.example.vladislav.androidstudy.dagger2.example4.interactor.LoginInteractor
import com.example.vladislav.androidstudy.dagger2.example4.models.LoginRequestModel
import javax.inject.Inject

/**
 * Empty activity for now
 */
class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var loginInteractor: LoginInteractor

    private lateinit var loginComponent: LoginComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        loginComponent = DaggerLoginComponent.builder().build()
        super.onCreate(savedInstanceState)
        loginComponent.inject(this)
        println(loginInteractor.getLoginData(LoginRequestModel("Vladchenko","somePassword")))
    }
}