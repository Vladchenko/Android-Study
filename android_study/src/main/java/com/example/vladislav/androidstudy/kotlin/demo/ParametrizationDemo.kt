package com.example.vladislav.androidstudy.kotlin.demo

/**
 * Up to write some stuff
 */
class ParametrizationDemo {

    val item = Item(2)
    val items = listOf(Item(1), Item(2), Item(3))

    fun showT() = items.map { it.printT() }

    class Item<T>(val t: T) {
        var value = t
        fun printT() = println(t)
    }
}