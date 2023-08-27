package com.example.vladislav.androidstudy.kotlin.demo

class Temperature(var tempInCelsius: Float)

var Temperature.tempInFahrenheit: Float     // This is an extension property
    get() = (tempInCelsius * 9 / 5) + 32
    set(value) {
        tempInCelsius = (value - 32) * 5 / 9
    }

fun extensionPropertyDemo() {
    val temp = Temperature(100f)
    println(temp.tempInFahrenheit)
}


fun extensionFunctionDemo() {
    println("Convert this to camelcase".spaceToCamelCase())
    println("Last char here".last())
    setOf(1, 2, 3, 4).joinToString()
    val sum = listOf(1)
    sum.sum()   // Strangely, not calling sum from ExtensionFunctionsDemo
}

fun String.spaceToCamelCase() {
    this.length
}

fun String.lastChar(): Char = this.get(this.length - 1) //Take a look to a next row, some code can be skipped.
// fun String.lastChar() = get(length - 1)
val String.lastChar: Char get() = get(length - 1)   // Replacing a fun with an extension property.
// Taken from "Kotlin in action" 3.3.5 Extension properties

/**
 * Note that extension functions don’t allow you to break encapsulation. Unlike methods defined in the class, extension
 * functions don’t have access to private or protected members of the class.
 *
 * Under the hood, an extension function is a static method that accepts the receiver object as its first argument.
 */

// Almost same to .joinToString of an array. Could be used, day for list
fun <T> Collection<T>.joinToString(
    separator: String = ", ", prefix: String = "", postfix: String = ""
): String {
    val result = StringBuilder(prefix)
    for ((index, element) in this.withIndex()) { if (index > 0)
        result.append(separator)
        result.append(element) }
    result.append(postfix)
    return result.toString()
}

/**
 * Note
 * If the class has a member function with the same signature as an extension function, the member function always takes
 * precedence. You should keep this in mind when extending the API of classes: if you add a member function with the same
 * signature as an extension function that a client of your class has defined, and they then recompile their code, it will
 * change its meaning and start referring to the new member function.
 */

// This one can be added to an Int identifier
fun List<Int>.sum(list: List<Int>): Int {
    var result = 0
    for (i in list) {
        result += i
    }
    return result
}

