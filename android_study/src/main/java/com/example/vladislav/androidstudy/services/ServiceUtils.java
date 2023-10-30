package com.example.vladislav.androidstudy.services;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import androidx.annotation.NonNull;

/**
 * Utils for {@link android.app.Service}
 */
public final class ServiceUtils {

    private ServiceUtils() {
        throw new IllegalStateException("Do not instantiate ServiceUtils");
    }

    /**
     * Check if service is running.
     *
     * @param activity     to getSystemService
     * @param serviceClass to define what service to check
     * @return true if service is still running, false if service is not running
     */
    public static boolean isMyServiceRunning(@NonNull Activity activity, @NonNull Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
