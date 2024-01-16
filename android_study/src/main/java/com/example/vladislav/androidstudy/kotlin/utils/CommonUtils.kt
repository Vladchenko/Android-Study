package com.example.vladislav.androidstudy.kotlin.utils

/**
 * Print an [array] of type [T]
 */
fun <T> printArray(array: Array<T>) {
//            for (element in array) {
//                println(element)
//            }
//            array.forEach(System.out::print)
//            array.forEach(::print)
//            println(array.joinToString(" "))
    array.map { println(it) }
}

fun printArray(array: IntArray) {
    array.map { print("$it ") }
    println()
}