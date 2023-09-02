package com.example.vladislav.androidstudy.dagger2.example2.datasource.remote

import com.example.vladislav.androidstudy.dagger2.example2.models.LoginRequestModel
import com.example.vladislav.androidstudy.dagger2.example2.models.LoginResultModel
import com.example.vladislav.androidstudy.dagger2.example2.interactor.LoginInteractor

/**
 * [LoginInteractor] implementation
 */
class LoginRemoteDataSourceImpl() : LoginRemoteDataSource {

    override fun getLoginData(loginModel: LoginRequestModel): LoginResultModel {
        return LoginResultModel("456789076543")
    }
}