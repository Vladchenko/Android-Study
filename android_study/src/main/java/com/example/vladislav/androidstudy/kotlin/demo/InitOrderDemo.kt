package com.example.vladislav.androidstudy.kotlin.demo

/**
 * https://kotlinlang.org/docs/classes.html#constructors
 *
 * Having a name passed as Vlad, this class prints as follows:
 *
 * First property: Vlad
 * First initializer block that prints Vlad
 * Second property: 4
 * Second initializer block that prints 4
 *
 * https://kotlinlang.org/docs/inheritance.html#derived-class-initialization-order
 */
class InitOrderDemo(name: String) {
    val firstProperty = "First property: $name".also(::println)

    init {
        println("First initializer block that prints $name")
    }

    val secondProperty = "Second property: ${name.length}".also(::println)

    init {
        println("Second initializer block that prints ${name.length}")
    }
}