package com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.model.ContactInfoModel
import kotlinx.coroutines.launch

/**
 * ViewModel (part of MVVM pattern) passing contacts data to activity
 *
 * @param contactsRepository to fetch contacts
 */
class ContactsViewModel(private val contactsRepository: ContactsRepository) : ViewModel() {

    private val _contactsListLiveData = MutableLiveData<List<ContactInfoModel>>()
    private val _phoneCallLiveData = MutableLiveData<String>()

    /** Live data to present contacts data */
    val contactsListLiveData: LiveData<List<ContactInfoModel>>
        get() = _contactsListLiveData

    /** Live data to make a phone call */
    val phoneCallLiveData: LiveData<String>
        get() = _phoneCallLiveData

    /** Download cell-phone contacts by its [uri] */
    fun downloadContacts(uri: String) {
        viewModelScope.launch {
            _contactsListLiveData.postValue(contactsRepository.downloadContacts(uri))
        }
    }

    /** Make a call on some [phoneNumber] */
    fun makeCall(phoneNumber: String) {
        _phoneCallLiveData.postValue(phoneNumber)
    }
}