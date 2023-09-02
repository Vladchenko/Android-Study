package com.example.vladislav.androidstudy.dagger2.example2.repository

import com.example.vladislav.androidstudy.dagger2.example2.models.LoginRequestModel
import com.example.vladislav.androidstudy.dagger2.example2.models.LoginResultModel

/**
 *
 */
interface LoginRepository {
    fun getLoginData(loginModel: LoginRequestModel): LoginResultModel
}