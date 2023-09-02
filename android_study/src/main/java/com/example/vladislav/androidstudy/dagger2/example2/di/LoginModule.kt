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

/**
 * Dagger user login module
 */
@Module
@DisableInstallInCheck
class LoginModule {

    @Provides
    fun provideLoginLocalDataSource(): LoginLocalDataSource {
        return LoginLocalDataSourceImpl()
    }

    @Provides
    fun provideLoginRemoteDataSource(): LoginRemoteDataSource {
        return LoginRemoteDataSourceImpl()
    }

    @Provides
    fun provideLoginRepository(loginLocalDataSource: LoginLocalDataSource,
                               loginRemoteDataSource: LoginRemoteDataSource): LoginRepository {
        return LoginRepositoryImpl(loginLocalDataSource, loginRemoteDataSource)
    }

    @Provides
    fun provideLoginInteractor(loginRepository: LoginRepository): LoginInteractor {
        return LoginInteractorImpl(loginRepository)
    }
}