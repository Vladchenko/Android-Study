package com.example.vladislav.androidstudy.dagger2.example1;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Dagger component
 */
@Singleton
@Component(modules = {
        UserModule.class,
        BackEndServiceModule.class
})
public interface MyComponent {

    // Provide the dependency for dependent components (not needed for single-component setups)
    BackEndService provideBackendService();

    // Allows to inject into our Main class. Method name not important
    void inject(Main main);
}
