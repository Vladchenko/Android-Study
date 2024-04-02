package com.example.vladislav.androidstudy.services.playaudio.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/** Implementation of [CoroutinesDispatchers] */
class CoroutinesDispatchersImpl : CoroutinesDispatchers {
    override val IO: CoroutineDispatcher
        get() = Dispatchers.IO
    override val Default: CoroutineDispatcher
        get() = Dispatchers.IO
    override val Main: CoroutineDispatcher
        get() = Dispatchers.IO
    override val Unconfined: CoroutineDispatcher
        get() = Dispatchers.IO
}