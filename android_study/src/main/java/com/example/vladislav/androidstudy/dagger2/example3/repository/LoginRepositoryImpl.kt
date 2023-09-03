package com.example.vladislav.androidstudy.dagger2.example3.repository

import com.example.vladislav.androidstudy.dagger2.example3.datasource.local.LoginLocalDataSource
import com.example.vladislav.androidstudy.dagger2.example3.datasource.remote.LoginRemoteDataSource
import com.example.vladislav.androidstudy.dagger2.example3.models.LoginRequestModel
import javax.inject.Inject

/**
 * [LoginRepository] implementation
 */
class LoginRepositoryImpl @Inject constructor(
    private val loginLocalDataSource: LoginLocalDataSource,
    private val loginRemoteDataSource: LoginRemoteDataSource
) : LoginRepository {

    override fun getLoginData(loginModel: LoginRequestModel) =
        loginLocalDataSource.getLoginData(loginModel)
}