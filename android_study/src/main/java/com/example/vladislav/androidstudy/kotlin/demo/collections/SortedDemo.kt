package com.example.vladislav.androidstudy.kotlin.demo.collections

import java.lang.Math.abs

/**
 * https://play.kotlinlang.org/byExample/05_Collections/14_sorted
 */
class SortedDemo {

    private val shuffled = listOf(5, 4, 2, 1, 3, -10)                   // 1
    private val natural = shuffled.sorted()                             // 2
    private val inverted = shuffled.sortedBy { -it }                    // 3
    private val descending = shuffled.sortedDescending()                // 4
    private val descendingBy = shuffled.sortedByDescending { abs(it)  } // 5

    fun printSorted() {
        println(natural)        // [-10, 1, 2, 3, 4, 5]
        println(inverted)       // [5, 4, 3, 2, 1, -10]
        println(descending)     // [5, 4, 3, 2, 1, -10]
        println(descendingBy)   // [-10, 5, 4, 3, 2, 1]
    }
}