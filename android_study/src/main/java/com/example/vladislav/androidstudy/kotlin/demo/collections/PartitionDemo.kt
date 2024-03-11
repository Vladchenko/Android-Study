package com.example.vladislav.androidstudy.kotlin.demo.collections

/**
     * https://play.kotlinlang.org/byExample/05_Collections/11_partition
 */
class PartitionDemo {

    private val numbers = listOf(1, -2, 3, -4, 5, -6)                // 1
    private val evenOdd = numbers.partition { it % 2 == 0 }           // 2

    fun getPartitionValue() {
        println(evenOdd)    // ([-2, -4, -6], [1, 3, 5])
        val (positives, negatives) = numbers.partition { it > 0 } // 3
        println(positives)  // [1, 3, 5]
        println(negatives)  // [-2, -4, -6]
    }
}