package com.example.vladislav.androidstudy.kotlin.demo.coroutines.task5

sealed class Result {
    object Success: Result()
    data class Failure(val reason: String) : Result()
}