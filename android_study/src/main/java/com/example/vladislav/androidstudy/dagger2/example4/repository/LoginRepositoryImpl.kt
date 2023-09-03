package com.example.vladislav.androidstudy.dagger2.example4.repository

import com.example.vladislav.androidstudy.dagger2.example4.datasource.LoginDataSource
import com.example.vladislav.androidstudy.dagger2.example4.models.LoginRequestModel
import javax.inject.Inject
import javax.inject.Named

/**
 * [LoginRepository] implementation
 */
class LoginRepositoryImpl @Inject constructor(
    @Named("LoginLocalDataSourceImpl")
    private val loginLocalDataSource: LoginDataSource,
    @Named("LoginRemoteDataSourceImpl")
    private val loginRemoteDataSource: LoginDataSource
) : LoginRepository {

    override fun getLoginData(loginModel: LoginRequestModel) =
        loginLocalDataSource.getLoginData(loginModel)
}