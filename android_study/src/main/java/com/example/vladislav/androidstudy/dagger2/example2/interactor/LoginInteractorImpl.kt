package com.example.vladislav.androidstudy.dagger2.example2.interactor

import com.example.vladislav.androidstudy.dagger2.example2.models.LoginRequestModel
import com.example.vladislav.androidstudy.dagger2.example2.repository.LoginRepository

/**
 * [LoginInteractor] implementation
 */
class LoginInteractorImpl(
    private val loginRepository: LoginRepository,
) : LoginInteractor {

    override fun getLoginData(loginModel: LoginRequestModel) =
        loginRepository.getLoginData(loginModel)
}