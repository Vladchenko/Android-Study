package com.example.vladislav.androidstudy.dagger2.example2.datasource.local

import com.example.vladislav.androidstudy.dagger2.example2.models.LoginRequestModel
import com.example.vladislav.androidstudy.dagger2.example2.models.LoginResultModel

/**
 * [LoginLocalDataSource] implementation
 */
class LoginLocalDataSourceImpl : LoginLocalDataSource {

    override fun getLoginData(loginModel: LoginRequestModel): LoginResultModel {
        return LoginResultModel("456789076543")
    }
}