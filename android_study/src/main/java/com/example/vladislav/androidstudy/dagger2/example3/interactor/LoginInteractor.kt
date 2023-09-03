package com.example.vladislav.androidstudy.dagger2.example3.interactor

import com.example.vladislav.androidstudy.dagger2.example3.models.LoginRequestModel
import com.example.vladislav.androidstudy.dagger2.example3.models.LoginResultModel

/**
 * Interface for Login interactor
 */
interface LoginInteractor {
    fun getLoginData(loginModel: LoginRequestModel): LoginResultModel
}