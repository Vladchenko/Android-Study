package com.example.vladislav.androidstudy.dagger2.example3.datasource.local

import com.example.vladislav.androidstudy.dagger2.example3.models.LoginRequestModel
import com.example.vladislav.androidstudy.dagger2.example3.models.LoginResultModel
import javax.inject.Inject

/**
 * Local [LoginDataSource] implementation
 */
class LoginLocalDataSourceImpl @Inject constructor() : LoginLocalDataSource {

    override fun getLoginData(loginModel: LoginRequestModel): LoginResultModel {
        return LoginResultModel("456789076543")
    }
}