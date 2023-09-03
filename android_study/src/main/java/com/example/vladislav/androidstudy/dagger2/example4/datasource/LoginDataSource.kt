package com.example.vladislav.androidstudy.dagger2.example4.datasource

import com.example.vladislav.androidstudy.dagger2.example4.models.LoginRequestModel
import com.example.vladislav.androidstudy.dagger2.example4.models.LoginResultModel

/**
 *
 */
interface LoginDataSource {
    fun getLoginData(loginModel: LoginRequestModel): LoginResultModel
}