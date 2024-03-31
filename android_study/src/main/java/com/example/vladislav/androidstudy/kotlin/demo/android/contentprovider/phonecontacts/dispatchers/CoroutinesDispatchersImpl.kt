package com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/** Implementation of [CoroutinesDispatchers] */
class CoroutinesDispatchersImpl : CoroutinesDispatchers {
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}