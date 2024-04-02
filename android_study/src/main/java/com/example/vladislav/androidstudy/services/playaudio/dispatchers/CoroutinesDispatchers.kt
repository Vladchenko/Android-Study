package com.example.vladislav.androidstudy.services.playaudio.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

/** Interface for coroutines dispatchers to provide them in DI. */
interface CoroutinesDispatchers {
    /** IO dispatcher for coroutines. */
    val IO: CoroutineDispatcher
    /** Default dispatcher for coroutines. */
    val Default: CoroutineDispatcher
    /** Main dispatcher for coroutines. */
    val Main: CoroutineDispatcher
    /** Unconfined dispatcher for coroutines. */
    val Unconfined: CoroutineDispatcher
}