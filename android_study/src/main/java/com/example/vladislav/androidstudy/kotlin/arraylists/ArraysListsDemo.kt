package com.example.kotlinstudy.arraylists

/**
 * Arrays and lists demonstration
 *
 * @author Yanchenko Vladislav on 13.06.2020.
 */
class ArraysListsDemo {

    private val mArray = arrayOf(1, 2, 3)
    private val mArray2 = Array(3) { i -> (i + i).toString() }    // Array with 3 elements,
    // each one initialized with i + i value, beginning with 0
    private val mArray3 = arrayOfNulls<String>(3)       // Array of strings, having each item
    // initialized to null

    private val mIntArray: IntArray = intArrayOf(1, 2, 3)
    private val mIntArray2 = IntArray(5)   // Array of int of size 5 with values [0, 0, 0, 0, 0]
    private val mIntArray3 = IntArray(5) { 42 }  // Array of int of size 5 with values [42, 42, 42, 42, 42]
    private val mIntArray4 = IntArray(5) { it * 1 + 500 }  // Array of int of size 5 with values [42, 42, 42, 42, 42]

    fun demo() {
        println(mArray2.forEach { println(it) })
        println(mIntArray2.forEach { println(it) })
        println(mIntArray3.forEach { println(it) })
        println(mIntArray4.forEach { println(it) })
    }
}
