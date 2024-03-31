package com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.di

import android.content.Context
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.view.ContactsActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/** Dagger component */
@Singleton
@Component(modules = [ContactsModule::class])
interface ContactsComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ContactsComponent
    }
    fun inject(activity: ContactsActivity)
}