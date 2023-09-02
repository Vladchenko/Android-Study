package com.example.vladislav.androidstudy.dagger2.example2.interactor

import com.example.vladislav.androidstudy.dagger2.example2.models.LoginRequestModel
import com.example.vladislav.androidstudy.dagger2.example2.models.LoginResultModel

/**
 *
 */
interface LoginInteractor {
    fun getLoginData(loginModel: LoginRequestModel): LoginResultModel
}