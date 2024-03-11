package com.example.vladislav.androidstudy.kotlin.demo.collections

/**
 * Arrays and lists demonstration
 *
 * https://kotlinlang.org/docs/arrays.html ->
 *
 * If you use primitives in an object-type array, this has a performance impact because your
 * primitives are boxed into objects. To avoid boxing overhead, use primitive-type arrays instead.
 *
 * You can use the equality operator (==) to check if collections are structurally equal.
 * You can't use this operator for arrays. Instead, you have to use a special function, which you
 * can read more about in https://kotlinlang.org/docs/arrays.html#compare-arrays.
 *
 * Don't use equality (==) and inequality (!=) operators to compare the contents of arrays.
 * These operators check whether the assigned variables point to the same object.
 * To learn more about why arrays in Kotlin behave this way, see our blog post.
 *
 * Check https://kotlinlang.org/docs/arrays.html#primitive-type-arrays
 *
 * @author Yanchenko Vladislav on 13.06.2020.
 */
class ArraysListsDemo {

    private val array = arrayOf(1, 2, 3)

    // Array with 3 elements, each one initialized with i + i value, beginning with 0
    private val array2 = Array(3) { i -> (i + i).toString() }

    // Array of strings, having each item initialized to null
    private val array3 = arrayOfNulls<String>(3)
    private val arrayList = arrayListOf(1, 2, 3)

    private val intArray: IntArray = intArrayOf(1, 2, 3)

    // Array of int of size 5 with values [0, 0, 0, 0, 0]
    private val intArray2 = IntArray(5)

    // Array of int of size 5 with values [42, 42, 42, 42, 42]
    private val intArray3 = IntArray(5) { 42 }

    // Array of int of size 5 with values [500, 501, 502, 503, 504]
    private val array4 = IntArray(5) { it * 1 + 500 }

    private val list = listOf(array)    // List with an entries from array

    fun arraysDemo() {
        array2.forEach { print("$it ") }    //  0 2 4
        println()
        intArray2.forEach { print("$it ") } // 0 0 0 0 0
        println()
        intArray3.forEach { print("$it ") } // 42 42 42 42 42
        println()
        array4.forEach { print("$it ") }    // 500 501 502 503 504
        println()
        // The .sum() function can only be used with arrays of numeric data types, such as Int.
        println(intArray.sum())             // 6

        val simpleArray = arrayOf(1, 2, 3)
        // Shuffles elements
        simpleArray.shuffle()
        println(simpleArray.joinToString())

        val pairArray = arrayOf("apple" to 120, "banana" to 150, "cherry" to 90, "apple" to 140)
        // Converts to a Map
        // The keys are fruits and the values are their number of calories
        // Note how keys must be unique, so the latest value of "apple" overwrites the first
        println(pairArray.toMap())      // {apple=140, banana=150, cherry=90}
    }

    fun arraysDemo2() {
        val array: Array<Int> = arrayOf(1, 2, 3)
        val array2 = arrayOf(1, 2.3, "3.4", 567, 789.01)    // Type maybe omitted
        val array3 = byteArrayOf(1, 2, 3, 4, 2, 3, 1, 2, 3, 4, 1).distinct()  // Only unique values - 1,2,3,4
        val array4 = byteArrayOf(1, 2, 3, 4, 2, 3, 1, 2, 3, 4, 1)

        println(array2)     // [Ljava.lang.Object;@7480ee6
        println(array2[0])   // 1st item of array, also can be printed by println(array.get(0))
        println("array.size = ${array2.size}")   // Printing a size of an array
        println("array contains 345 = ${array2.contains(345)}")
        println("index of 567 = ${array2.indexOf(567)}")
//        array2.remove     // No remove method
        array3.forEach { // Check a LoopsDemo.kt to see a several ways to traverse through collections
            println(it)     // Printing an array
        }
        for (item in array3) print(item)
        println()
        println("distinctBy: " + array2.distinctBy {
            it.toString().toIntOrNull()    // Prints [1, 2.3, 567], 2.3 is present because it yields null and null is unique value among others
        })   // TODO Understand distinctBy and implement other methods
        println(array2.getOrNull(40) ?: "Неизвестный индекс")
        println(
                array3.toList() // Turns array into list
                        .distinct() // Takes only unique items
                        .map { it * it }    // Multiplies item onto itself
                        .filter { it > 3 }  // Takes items greater than 3
        )   // [4, 9, 16]
        array3.toByteArray()
        array3.toHashSet()
        println("array4.toHashSet() = ${array4.toHashSet()}")   // array4.toHashSet() = [1, 2, 3, 4]
        array3.toMutableList()
        array3.toMutableSet()
        array3.toSet()
        array3.toSortedSet()
    }
}
