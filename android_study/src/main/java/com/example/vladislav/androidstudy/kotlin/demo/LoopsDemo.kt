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

//        for ((index, value) in array.withIndex()) {
//            println("the element at $index is $value")
//        }
    for (x in 1..10 step 2) {
        print(x)
    }
    println()

    for (x in 9 downTo 0 step 3) {  // This one doesn't work
        print(x)
    }
    // No iteration for float / double
    val fios = listOf<FIO>()
    for (fio in fios) print(fio)
    println()

    print("forEach: ")
    y.forEach { print(it) }
    println()

    print("forEach(Consumer: ")
    y.forEach(Consumer { print(it) })
    println()

    print("y.map: ")
    y.map {
        print(it)
    }
    println()

    print("y.map 2: ")
    print(y.map {
        it
    })
    println()

    // for (item: Int in ints) {
    //     // ...
    // }

    for (c in 'A'..'F') {
        println(Integer.toBinaryString(c.toInt()))
    }

    for (с in "abc") {
        print(с + 1)
    }   // "bcd"
    println()

    for (i in 0..array2.size - 1) {
        println(array2[i])
    }
    for (i in array2.indices) {
        println("item at $i is ${array2[i]}")
    }
    for ((index, value) in array.withIndex()) {
        println("the element at $index is $value")
    }
    for (i in 0..array2.size - 1) {
        println(array2[i])
    }
    for (i in array2.indices) {
        println("item at $i is ${array2[i]}")
    }
    for ((index, value) in array.withIndex()) {
        println("the element at $index is $value")
    }
    println(array2.joinToString(" "))   // Another way of printing array
    array.forEach {                         // Another way of printing array
            item ->
        println(item)
    }
    array.forEachIndexed { i, item -> println("Item $item is at index $i") }  // Another way of printing array
    array.forEach {
        // Another way of printing array
        println(it)
    }
    array.forEach(System.out::println)       // Another way of printing array. This is a function reference.
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
