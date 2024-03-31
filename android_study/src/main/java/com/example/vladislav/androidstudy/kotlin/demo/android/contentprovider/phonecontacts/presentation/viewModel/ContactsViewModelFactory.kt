package com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Factory to create a view model for phone contacts
 *
 * @param contactsRepository to fetch phone contacts
 */
class ContactsViewModelFactory(
    private val contactsRepository: ContactsRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContactsViewModel(contactsRepository) as T
    }
}