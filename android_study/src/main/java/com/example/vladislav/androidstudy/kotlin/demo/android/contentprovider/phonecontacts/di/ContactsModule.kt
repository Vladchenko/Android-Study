package com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.di

import android.content.Context
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.data.ContactsProvider
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.data.ContactsRepositoryImpl
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.dispatchers.CoroutinesDispatchers
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.dispatchers.CoroutinesDispatchersImpl
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.contactsprovider.ContactsProviderImpl
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.viewModel.ContactsRepository
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.viewModel.ContactsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Singleton

/** Dagger module for cell-phone contacts */
@Module
@DisableInstallInCheck
class ContactsModule {

    @Singleton
    @Provides
    fun provideCoroutinesDispatchers(): CoroutinesDispatchers = CoroutinesDispatchersImpl()

    @Singleton
    @Provides
    fun provideContentResolverProvider(
        applicationContext: Context,
    ) : ContactsProvider = ContactsProviderImpl(applicationContext.contentResolver)

    @Singleton
    @Provides
    fun provideContactsRepository(
        dispatchers: CoroutinesDispatchers,
        contactsProvider: ContactsProvider,
    ): ContactsRepository = ContactsRepositoryImpl(contactsProvider, dispatchers)

    @Singleton
    @Provides
    fun provideContactsViewModelFactory(contactsRepository: ContactsRepository) =
        ContactsViewModelFactory(contactsRepository)
}