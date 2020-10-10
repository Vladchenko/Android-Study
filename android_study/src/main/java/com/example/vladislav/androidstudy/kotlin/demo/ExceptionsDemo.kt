package com.example.vladislav.androidstudy.kotlin.demo

import java.io.BufferedReader

/**
 * Demonstration of exceptions in Kotlin
 *
 * @author Yanchenko Vladislav
 * @since 14.02.2021
 */
class ExceptionsDemo {

    fun demonstrateException() {
        val percentage = 101
        if (percentage !in 0..100) {
            throw IllegalArgumentException(
                "A percentage value must be between 0 and 100: $percentage"
            )
        }
    }

    fun readNumber(reader: BufferedReader): Int? =
        try {
            val line = reader.readLine()
            Integer.parseInt(line)
        } catch (e: NumberFormatException) {
            println(e.message)
            -1
        } finally {
            reader.close()
        }

    // Number is calculated using an expression
    fun readNumber2(reader: BufferedReader) {
        val number = try {
            Integer.parseInt(reader.readLine())
        } catch (e: NumberFormatException) {
            return
        }
        println(number)
    }
}
