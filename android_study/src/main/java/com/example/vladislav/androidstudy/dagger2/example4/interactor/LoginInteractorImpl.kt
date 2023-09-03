package com.example.vladislav.androidstudy.dagger2.example4.interactor

import com.example.vladislav.androidstudy.dagger2.example4.models.LoginRequestModel
import com.example.vladislav.androidstudy.dagger2.example4.repository.LoginRepository
import javax.inject.Inject

/**
 * [LoginInteractor] implementation
 */
class LoginInteractorImpl @Inject constructor(
    private val loginRepository: LoginRepository,
) : LoginInteractor {

    override fun getLoginData(loginModel: LoginRequestModel) =
        loginRepository.getLoginData(loginModel)
}