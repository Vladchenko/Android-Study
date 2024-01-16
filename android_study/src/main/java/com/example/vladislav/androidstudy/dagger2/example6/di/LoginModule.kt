package com.example.vladislav.androidstudy.dagger2.example6.di

import com.example.vladislav.androidstudy.dagger2.example4.datasource.LoginDataSource
import com.example.vladislav.androidstudy.dagger2.example4.datasource.LoginLocalDataSourceImpl
import com.example.vladislav.androidstudy.dagger2.example4.datasource.LoginRemoteDataSourceImpl
import com.example.vladislav.androidstudy.dagger2.example4.interactor.LoginInteractor
import com.example.vladislav.androidstudy.dagger2.example4.interactor.LoginInteractorImpl
import com.example.vladislav.androidstudy.dagger2.example4.repository.LoginRepository
import com.example.vladislav.androidstudy.dagger2.example4.repository.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Named

/**
 * Dagger user login module
 */
@Module
@DisableInstallInCheck
abstract class LoginModule {

    @Binds
    @Named("LoginLocalDataSourceImpl")
    abstract fun bindLoginLocalDataSource(loginLocalDataSourceImpl: LoginLocalDataSourceImpl): LoginDataSource

    @Binds
    @Named("LoginRemoteDataSourceImpl")
    abstract fun bindLoginRemoteDataSource(loginRemoteDataSourceImpl: LoginRemoteDataSourceImpl): LoginDataSource

    @Binds
    abstract fun bindLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun bindLoginInteractor(loginInteractorImpl: LoginInteractorImpl): LoginInteractor
}