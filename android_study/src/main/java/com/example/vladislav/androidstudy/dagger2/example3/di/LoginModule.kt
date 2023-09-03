package com.example.vladislav.androidstudy.dagger2.example3.di

import com.example.vladislav.androidstudy.dagger2.example3.datasource.local.LoginLocalDataSource
import com.example.vladislav.androidstudy.dagger2.example3.datasource.local.LoginLocalDataSourceImpl
import com.example.vladislav.androidstudy.dagger2.example3.datasource.remote.LoginRemoteDataSource
import com.example.vladislav.androidstudy.dagger2.example3.datasource.remote.LoginRemoteDataSourceImpl
import com.example.vladislav.androidstudy.dagger2.example3.interactor.LoginInteractor
import com.example.vladislav.androidstudy.dagger2.example3.interactor.LoginInteractorImpl
import com.example.vladislav.androidstudy.dagger2.example3.repository.LoginRepository
import com.example.vladislav.androidstudy.dagger2.example3.repository.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck

/**
 * Dagger user login module
 */
@Module
@DisableInstallInCheck
abstract class LoginModule {

    @Binds
    abstract fun bindLoginLocalDataSource(loginLocalDataSourceImpl: LoginLocalDataSourceImpl): LoginLocalDataSource

    @Binds
    abstract fun bindLoginRemoteDataSource(loginRemoteDataSourceImpl: LoginRemoteDataSourceImpl): LoginRemoteDataSource
    @Binds
    abstract fun bindLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun bindLoginInteractor(loginInteractorImpl: LoginInteractorImpl): LoginInteractor
}