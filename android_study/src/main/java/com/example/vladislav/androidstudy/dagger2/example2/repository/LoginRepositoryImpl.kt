package com.example.vladislav.androidstudy.dagger2.example2.repository

import com.example.vladislav.androidstudy.dagger2.example2.models.LoginRequestModel
import com.example.vladislav.androidstudy.dagger2.example2.datasource.LoginDataSource

/**
 * [LoginRepository] implementation
 */
class LoginRepositoryImpl(
    private val loginLocalDataSource: LoginDataSource,
    private val loginRemoteDataSource: LoginDataSource
) : LoginRepository {

    override fun getLoginData(loginModel: LoginRequestModel) =
        loginLocalDataSource.getLoginData(loginModel)
}