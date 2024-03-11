package com.example.vladislav.androidstudy.dagger2.example1;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by Влад on 11.07.2018.
 */

public class BackEndService {

    @Inject
    public User user;

    private String serverUrl;

    @Inject
    public BackEndService(@Named("serverUrl") String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public boolean callServer() {
        if (user !=null && serverUrl!=null && serverUrl.length()>0) {
            System.out.println("User: " + user + " ServerUrl: "  + serverUrl);
            return true;
        }
        return false;
    }

}
