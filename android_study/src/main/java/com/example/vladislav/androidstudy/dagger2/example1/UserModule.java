package com.example.vladislav.androidstudy.dagger2.example1;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.migration.DisableInstallInCheck;

/**
 * Created by Влад on 11.07.2018.
 */

@Module
@DisableInstallInCheck
public class UserModule {

    @Provides
    @Singleton
    User providesUser() {
        return new User("Lars", "Vogel");
    }
}