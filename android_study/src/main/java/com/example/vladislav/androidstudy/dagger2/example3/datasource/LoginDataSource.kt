package com.example.vladislav.androidstudy.dagger2.example3.datasource

import com.example.vladislav.androidstudy.dagger2.example3.models.LoginRequestModel
import com.example.vladislav.androidstudy.dagger2.example3.models.LoginResultModel

/**
 * Common datasource interface
 */
interface LoginDataSource {
    fun getLoginData(loginModel: LoginRequestModel): LoginResultModel
}