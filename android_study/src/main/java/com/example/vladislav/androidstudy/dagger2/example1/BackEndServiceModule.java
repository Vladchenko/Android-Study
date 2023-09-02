package com.example.vladislav.androidstudy.dagger2.example1;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.migration.DisableInstallInCheck;

/**
 * Created by Влад on 11.07.2018.
 */

@Module
@DisableInstallInCheck
public class BackEndServiceModule {

    @Provides
    @Singleton
    BackEndService provideBackendService(@Named("serverUrl") String serverUrl) {
        return new BackEndService(serverUrl);
    }

    @Provides
    @Named("serverUrl")
    String provideServerUrl() {
        return "http://www.vogella.com";
    }

    @Provides
    @Named("anotherUrl")
    String provideAnotherUrl() {
        return "http://www.google.com";
    }
}
