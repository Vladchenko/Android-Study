package com.example.vladislav.androidstudy.dagger2.example1;

import javax.inject.Inject;

/**
 * Created by Влад on 11.07.2018.
 */

public class Main {

    @Inject
    BackEndService backendService; //

    private final MyComponent component;

    private Main() {
        component = DaggerMyComponent.builder().build();
        component.inject(this);
    }

    private void callServer() {
        boolean callServer = backendService.callServer();
        if (callServer) {
            System.out.println("Server call was successful. ");
        } else {
            System.out.println("Server call failed. ");
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.callServer();
    }
}
