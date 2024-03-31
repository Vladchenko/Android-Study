package com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

/** Interface for coroutines dispatchers to provide them in DI. */
interface CoroutinesDispatchers {
    /** IO dispatcher for coroutines. */
    val io: CoroutineDispatcher
    /** Default dispatcher for coroutines. */
    val default: CoroutineDispatcher
    /** Main dispatcher for coroutines. */
    val main: CoroutineDispatcher
    /** Unconfined dispatcher for coroutines. */
    val unconfined: CoroutineDispatcher
}