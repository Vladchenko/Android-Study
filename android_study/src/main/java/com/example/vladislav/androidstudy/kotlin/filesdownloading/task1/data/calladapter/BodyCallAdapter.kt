package com.example.vladislav.androidstudy.kotlin.filesdownloading.task1.data.calladapter

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import java.lang.reflect.Type

/**
 * Adapter for body part in {@link retrofit2.Response}
 */
internal class BodyCallAdapter<T>(
    private val responseType: Type
) : CallAdapter<T, Flow<T>> {

    override fun responseType(): Type =
        responseType

    @OptIn(kotlinx.coroutines.InternalCoroutinesApi::class)
    override fun adapt(call: Call<T>): Flow<T> = flow {
        emit(suspendCancellableCoroutine { continuation ->
            call.registerCallback(continuation) { response ->
                continuation.resumeWith(kotlin.runCatching {
                    if (response.isSuccessful) {
                        response.body() ?: throw NullPointerException("Response body is null: $response")
                    } else {
                        throw HttpException(response)
                    }
                })
            }

            call.registerOnCancellation(continuation)
        })
    }
}