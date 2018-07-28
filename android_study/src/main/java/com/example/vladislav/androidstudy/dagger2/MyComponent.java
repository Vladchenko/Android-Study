package com.example.vladislav.androidstudy.dagger2;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Влад on 11.07.2018.
 */

@Singleton
@Component(modules = {
        UserModule.class,
        BackEndServiceModule.class
})
public interface MyComponent {

    // provide the dependency for dependent components
    // (not needed for single-component setups)
    BackEndService provideBackendService();

    // allow to inject into our Main class
    // method name not important
    void inject(Main main);
}
