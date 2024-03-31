package com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.viewModel

import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.model.ContactInfoModel

/** Provides contacts from a cell-phone */
interface ContactsRepository {

    /** @return list of cell-phone contacts */
    suspend fun downloadContacts(uri: String): List<ContactInfoModel>
}