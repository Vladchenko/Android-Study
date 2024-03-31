package com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.data

import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.model.ContactInfoModel

/** Provides cell-phone contacts */
interface ContactsProvider {
    fun fetchContacts(uri: String): List<ContactInfoModel>
}