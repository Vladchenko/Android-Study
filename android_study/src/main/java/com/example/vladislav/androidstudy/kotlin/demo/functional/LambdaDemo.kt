package com.example.vladislav.androidstudy.kotlin.demo.functional

class LambdaDemo() {

    // Return "name", if it is not null, else return "no name"
    private fun elvisOperatorDemo(name: String?) = name ?: "no name"

    // Almost same as - fun Int.isEven(): Boolean = this % 2 == 0
    private fun isEven(i: Int): Boolean = i % 2 == 0

    fun lambdaDemo() {
        val predicate = ::elvisOperatorDemo
        println(predicate)  // function elvisOperatorDemo (Kotlin reflection is not available)
        // println(::predicate)  // Unsupported - References to a variables are not supported yet
        val fruits = listOf("banana", "avocado", "apple", "kiwifruit")
        val ordinalsList = listOf(1, 2, 3, 4)
        fruits
                .filter { it.startsWith("a") }
                .sortedBy { it }
                .map { it.uppercase() }
                .forEach { println(it) }    // APPLE    AVOCADO
        // Passes through each items in ordinalsList and runs isEven on them.
        ordinalsList.any(::isEven) // true
        ordinalsList.filter { isEven(it) }  // [2, 4]
        // same as
        ordinalsList.filter(::isEven)  // [2, 4]
        println({ string: String -> string.uppercase() }("hello"))
    }

    /** https://kotlinlang.org/docs/kotlin-tour-functions.html#lambdas-exercise-1 */
    fun lambdaDemo2() {
        val actions = listOf("title", "year", "author")
        val prefix = "https://example.com/book-info"
        val id = 5
        val urls: (Int, String, List<String>) -> List<String> = { id, prefix, actions ->
            actions.map { prefix.plus('/').plus(id).plus('/').plus(it) }
        }
        println(urls(id, prefix, actions))
    }
}