package com.example.vladislav.androidstudy

import android.app.Application
import android.content.res.Configuration
import android.widget.Toast
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.di.ContactsComponent
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.di.DaggerContactsComponent

class MyApplication: Application() {

    lateinit var contactsComponent: ContactsComponent

    // // Reference to the application graph that is used across the whole app
    // val loginComponent: LoginComponent by lazy {
    //     // Creates an instance of AppComponent using its Factory constructor
    //     // We pass the applicationContext that will be used as Context in the graph
    //     DaggerLoginComponent.factory().create(DaggerLoginComponent_LoginDependenciesComponent.factory().create())
    // }

    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    override fun onCreate() {
        super.onCreate()
        // This way one passes an application context to dagger graph
        contactsComponent = DaggerContactsComponent.factory().create(applicationContext)
        // appComponent = DaggerApplicationComponent.create();
        Toast.makeText(
            this, resources.getText(R.string.application_created_text),
            Toast.LENGTH_SHORT
        ).show()
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is optional!
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    override fun onLowMemory() {
        super.onLowMemory()
        Toast.makeText(
            this, resources.getText(R.string.low_memory_text),
            Toast.LENGTH_SHORT
        ).show()
    }
}