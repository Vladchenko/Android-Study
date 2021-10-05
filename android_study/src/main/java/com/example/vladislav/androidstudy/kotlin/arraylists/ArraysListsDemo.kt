package com.example.vladislav.androidstudy.kotlin.arraylists

/**
 * Arrays and lists demonstration
 *
 * @author Yanchenko Vladislav on 13.06.2020.
 */
class ArraysListsDemo {

    private val array = arrayOf(1, 2, 3)
    private val array2 = Array(3) { i -> (i + i).toString() }    // Array with 3 elements,
    // each one initialized with i + i value, beginning with 0
    private val array3 = arrayOfNulls<String>(3)       // Array of strings, having each item
    // initialized to null

    private val intArray: IntArray = intArrayOf(1, 2, 3)
    private val intArray2 = IntArray(5)   // Array of int of size 5 with values [0, 0, 0, 0, 0]
    private val intArray3 = IntArray(5) { 42 }  // Array of int of size 5 with values [42, 42, 42, 42, 42]
    private val array4 = IntArray(5) { it * 1 + 500 }  // Array of int of size 5 with values [500, 501, 502, 503, 504]

    fun demo() {
        println(array2.forEach { println(it) })
        println(intArray2.forEach { println(it) })
        println(intArray3.forEach { println(it) })
        println(array4.forEach { println(it) })
    }
}
