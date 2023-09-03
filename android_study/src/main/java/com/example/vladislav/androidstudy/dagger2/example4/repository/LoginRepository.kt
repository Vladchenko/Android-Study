package com.example.vladislav.androidstudy.dagger2.example4.repository

import com.example.vladislav.androidstudy.dagger2.example4.models.LoginRequestModel
import com.example.vladislav.androidstudy.dagger2.example4.models.LoginResultModel

/**
 * Interface for login repository
 */
interface LoginRepository {
    fun getLoginData(loginModel: LoginRequestModel): LoginResultModel
}