package com.example.vladislav.androidstudy.dagger2.example3.datasource.remote

import com.example.vladislav.androidstudy.dagger2.example3.models.LoginRequestModel
import com.example.vladislav.androidstudy.dagger2.example3.models.LoginResultModel
import javax.inject.Inject

/**
 * Remote [LoginRemoteDataSource] implementation
 */
class LoginRemoteDataSourceImpl @Inject constructor() : LoginRemoteDataSource {

    override fun getLoginData(loginModel: LoginRequestModel): LoginResultModel {
        return LoginResultModel("456789076543")
    }
}