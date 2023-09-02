package com.example.vladislav.androidstudy.dagger2.example2.datasource

import com.example.vladislav.androidstudy.dagger2.example2.models.LoginRequestModel
import com.example.vladislav.androidstudy.dagger2.example2.models.LoginResultModel

/**
 *
 */
interface LoginDataSource {
    fun getLoginData(loginModel: LoginRequestModel): LoginResultModel
}