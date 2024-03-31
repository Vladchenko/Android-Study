package com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.lang.ref.WeakReference

/**
 * Provides permission to read contacts from a cell-phone.
 */
class ContactsPermissionDelegate {

    /**
     * Request permission to read cell-phone contacts
     * @param activityReference keeps a weak reference to an activity
     */
    fun requestContactsReadingPermission(activityReference: WeakReference<Activity>): PermissionStatus {
        if (activityReference.get() == null) {
            Log.e(TAG, "Activity reference is null, so permission cannot be requested, program quits")
            return PermissionStatus.NotGranted
        }
        // Check whether the permission is already granted or not.
        return if (ContextCompat.checkSelfPermission(activityReference.get()!!, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activityReference.get()!!,
                arrayOf(PERMISSION_READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACTS
            )
            PermissionStatus.Pending
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            PermissionStatus.GrantedBefore
        }
    }

    companion object {
        private const val TAG = "ContactsPermissionDelegate"
        // Request code for READ_CONTACTS. It can be any number > 0.
        const val PERMISSIONS_REQUEST_READ_CONTACTS = 100
        private const val PERMISSION_READ_CONTACTS = "android.permission.READ_CONTACTS"
    }
}