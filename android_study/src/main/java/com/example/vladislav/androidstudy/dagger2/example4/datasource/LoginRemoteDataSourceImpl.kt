package com.example.vladislav.androidstudy.dagger2.example4.datasource

import com.example.vladislav.androidstudy.dagger2.example4.models.LoginRequestModel
import com.example.vladislav.androidstudy.dagger2.example4.models.LoginResultModel
import javax.inject.Inject
import javax.inject.Named

/**
 * Remote [LoginDataSource] implementation
 */
@Named("LoginRemoteDataSourceImpl")
class LoginRemoteDataSourceImpl @Inject constructor() : LoginDataSource {

    override fun getLoginData(loginModel: LoginRequestModel): LoginResultModel {
        return LoginResultModel("456789076543")
    }
}