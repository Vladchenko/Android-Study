package com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.contactsprovider

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.data.ContactsProvider
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.model.ContactInfoModel

/**
 * Implementation of [ContactsProvider].
 * This class is made to remove dependency on android class [android.content.ContentResolver]
 * from ContactsRepository.
 *
 * @param contentResolver to access cell-phone contacts
 */
class ContactsProviderImpl(private val contentResolver: ContentResolver) : ContactsProvider {
    override fun fetchContacts(uri: String): List<ContactInfoModel> {
        val contactsList = mutableListOf<ContactInfoModel>()
        val selection = ContactsContract.Contacts.HAS_PHONE_NUMBER
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.Contacts._ID
        )
        contentResolver.query(
            Uri.parse(uri),
            projection,
            selection,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        ).use { cursor ->
            while (cursor!!.moveToNext()) {
                getContactInfo(cursor)?.let { contactsList.add(it) }
            }
        }
        return contactsList.distinctBy { it.phoneNumber }
    }

    private fun getContactInfo(cursor: Cursor): ContactInfoModel? {
        val name =
            cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Data.DISPLAY_NAME))
        val phoneNumberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
        val phoneNumber = cursor.getString(phoneNumberIndex).orEmpty()
        if (name != phoneNumber) {
            return ContactInfoModel(
                cursor.getInt(
                    cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID)
                ),
                name,
                phoneNumber,
            )
        }
        return null
    }
}