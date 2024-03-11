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
import javax.inject.Singleton

/**
 * Dagger user login module
 */
@Module
@DisableInstallInCheck
abstract class LoginModule {

    @Binds
    @Singleton
    abstract fun bindLoginLocalDataSource(loginLocalDataSourceImpl: LoginLocalDataSourceImpl): LoginLocalDataSource

    @Binds
    @Singleton
    abstract fun bindLoginRemoteDataSource(loginRemoteDataSourceImpl: LoginRemoteDataSourceImpl): LoginRemoteDataSource
    @Binds
    @Singleton
    abstract fun bindLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    @Singleton
    abstract fun bindLoginInteractor(loginInteractorImpl: LoginInteractorImpl): LoginInteractor
}