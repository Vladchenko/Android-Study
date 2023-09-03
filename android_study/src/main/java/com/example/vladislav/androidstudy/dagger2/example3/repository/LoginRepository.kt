package com.example.vladislav.androidstudy.dagger2.example3.repository

import com.example.vladislav.androidstudy.dagger2.example3.models.LoginRequestModel
import com.example.vladislav.androidstudy.dagger2.example3.models.LoginResultModel

/**
 * Interface for login repository
 */
interface LoginRepository {
    fun getLoginData(loginModel: LoginRequestModel): LoginResultModel
}