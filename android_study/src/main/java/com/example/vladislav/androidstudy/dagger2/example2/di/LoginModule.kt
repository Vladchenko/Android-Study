package com.example.vladislav.androidstudy.dagger2.example2.di

import com.example.vladislav.androidstudy.dagger2.example2.datasource.local.LoginLocalDataSource
import com.example.vladislav.androidstudy.dagger2.example2.datasource.local.LoginLocalDataSourceImpl
import com.example.vladislav.androidstudy.dagger2.example2.datasource.remote.LoginRemoteDataSource
import com.example.vladislav.androidstudy.dagger2.example2.datasource.remote.LoginRemoteDataSourceImpl
import com.example.vladislav.androidstudy.dagger2.example2.interactor.LoginInteractor
import com.example.vladislav.androidstudy.dagger2.example2.interactor.LoginInteractorImpl
import com.example.vladislav.androidstudy.dagger2.example2.repository.LoginRepository
import com.example.vladislav.androidstudy.dagger2.example2.repository.LoginRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Singleton

/**
 * Dagger user login module
 */
@Module
@DisableInstallInCheck
class LoginModule {

    @Provides
    @Singleton
    fun provideLoginLocalDataSource(): LoginLocalDataSource {
        return LoginLocalDataSourceImpl()
    }

    @Provides
    @Singleton
    fun provideLoginRemoteDataSource(): LoginRemoteDataSource {
        return LoginRemoteDataSourceImpl()
    }

    @Provides
    @Singleton
    fun provideLoginRepository(loginLocalDataSource: LoginLocalDataSource,
                               loginRemoteDataSource: LoginRemoteDataSource): LoginRepository {
        return LoginRepositoryImpl(loginLocalDataSource, loginRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideLoginInteractor(loginRepository: LoginRepository): LoginInteractor {
        return LoginInteractorImpl(loginRepository)
    }
}