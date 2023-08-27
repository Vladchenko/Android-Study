package com.example.vladislav.androidstudy.kotlin.demo

import com.example.vladislav.androidstudy.kotlin.models.FIO
import java.util.function.Consumer

/**
 * Loops demonstration
 *
 * @author Yanchenko Vladislav
 * @since 09.03.2021
 */

fun loopsDemo() {

    // Take a look to an arraysDemo() as well
    var x = 10
    val y = 10..20
    val array = arrayOf(1, 2.3, "3.4", 567, 789.01)
    val array2 = Array(5, { x -> x * x })

    for ((index, value) in array.withIndex()) {
        println("element at index[$index] has value=$value,  ")
    }
    for (x in 1..10 step 2) {
        print("$x ")
    }
    println()

    for (x in 9 downTo 0 step 3) {  // This one doesn't work
        print("$x ")
    }
    // No iteration for float / double
    val fios = listOf<FIO>()
    for (fio in fios) print("$fio ")
    println()

    print("forEach: ")
    y.forEach { print("$it ") }
    println()

    print("forEach(Consumer: ")
    y.forEach(Consumer { print("$it ") })
    println()

    print("y.map: ")
    y.map { print("$it ") }
    println()

    print("y.map 2: ")
    print(y.map { it })
    println()

    // for (item: Int in ints) {
    //     // ...
    // }

    for (c in 'A'..'F') {
        print(Integer.toBinaryString(c.code) + " ")
    }
    println()

    for (с in "abc") {
        print(с + 1)
    }   // "bcd"
    println()

    for (i in 0..array2.size - 1) {
        print("$array2[i],  ")
    }
    println()
    for (i in array2.indices) {
        print("item at $i is ${array2[i]},  ")
    }
    println()
    for ((index, value) in array.withIndex()) {
        print("element at $index is $value,  ")
    }
    println()
    for (i in 0..array2.size - 1) {
        print("$array2[i],  ")
    }
    println()
    for (i in array2.indices) {
        print("item at $i is ${array2[i]}, ")
    }
    println()
    for ((index, value) in array.withIndex()) {
        print("element at $index is $value,  ")
    }
    println()
    println(array2.joinToString(" "))   // Another way of printing array
    array.forEach { item -> print(item) }                         // Another way of printing array
    println()
    array.forEachIndexed { i, item -> print("Item $item is at index $i,  ") }  // Another way of printing array
    array.forEach { print(it) }     // Another way of printing array
    println()
    array.forEach(System.out::print)       // Another way of printing array. This is a function reference.
    println()
    println(array.map { it })

    while (x > 0) {
        x--
    }

//        do {
//            val y = retrieveData()
//        } while (y != null) // y is visible here!
}

fun sayHello(map: Map<String, String>) =
    map.forEach { (greeting, subject) -> println("$greeting, $subject") }

fun sayHello(greeting: String, list: List<String>) =
    list.forEach { item -> println("$greeting, $item") }
