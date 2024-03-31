package com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.model

/**
 * Model representing data of some person contact in cell-phone.
 *
 * @property id some unique id
 * @property name usually name and lastname
 * @property phoneNumber to call that person on a phone
 */
data class ContactInfoModel(val id: Int, val name: String, val phoneNumber: String)
