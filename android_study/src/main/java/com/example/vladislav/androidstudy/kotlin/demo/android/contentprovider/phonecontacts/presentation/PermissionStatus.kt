package com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation

/** Status on permission granting from user. */
sealed class PermissionStatus {
    object Granted: PermissionStatus()
    object Pending: PermissionStatus()
    object NotGranted: PermissionStatus()
    object GrantedBefore: PermissionStatus()
}