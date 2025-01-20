package com.example.vladislav.androidstudy.kotlin.demo.collections

/**
 * https://play.kotlinlang.org/byExample/05_Collections/14_sorted
 */
class SortedDemo {

    private val shuffled = listOf(5, 4, 2, 1, 3, -10)
    private val natural = shuffled.sorted()
    private val inverted = shuffled.sortedBy { -it }
    private val descending = shuffled.sortedDescending()
    private val descendingBy = shuffled.sortedByDescending { kotlin.math.abs(it) }

    fun printSorted() {
        println(natural)        // [-10, 1, 2, 3, 4, 5]
        println(inverted)       // [5, 4, 3, 2, 1, -10]
        println(descending)     // [5, 4, 3, 2, 1, -10]
        println(descendingBy)   // [-10, 5, 4, 3, 2, 1]
    }
}