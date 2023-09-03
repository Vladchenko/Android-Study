package com.example.vladislav.androidstudy.dagger2.example4.interactor

import com.example.vladislav.androidstudy.dagger2.example4.models.LoginRequestModel
import com.example.vladislav.androidstudy.dagger2.example4.models.LoginResultModel

/**
 *
 */
interface LoginInteractor {
    fun getLoginData(loginModel: LoginRequestModel): LoginResultModel
}