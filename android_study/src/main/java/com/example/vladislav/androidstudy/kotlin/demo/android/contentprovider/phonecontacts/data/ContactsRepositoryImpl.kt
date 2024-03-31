package com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.data

import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.dispatchers.CoroutinesDispatchers
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.model.ContactInfoModel
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.viewModel.ContactsRepository
import kotlinx.coroutines.withContext

/**
 * Provides contacts from a cell-phone
 *
 * @param contactsProvider provides data on a phone contacts
 * @param dispatchers for coroutines
 */
class ContactsRepositoryImpl(
    private val contactsProvider: ContactsProvider,
    private val dispatchers: CoroutinesDispatchers
) : ContactsRepository {

    /**
     * @return list of [ContactInfoModel] cell phone contacts
     * @param uri - phone contacts uri
     */
    override suspend fun downloadContacts(uri: String): List<ContactInfoModel> {
        return withContext(dispatchers.io) {
            val contactsList = contactsProvider.fetchContacts(uri)
            contactsList
        }
    }
}