package com.example.vladislav.androidstudy.kotlin.demo

/**
 * Demo of numbers in Kotlin
 *
 * @author Yanchenko Vladislav
 * @since 25.01.2021
 */
class NumbersDemo {

    private var variable = 255      // This is a variable, type is automatically inferred as int
    private var int: Int = 0xFF     // Explicit type denotation
    private var bigInt: Int = Int.MAX_VALUE     // Integer variable with max value
    private var doubleVar: Double = Double.MIN_VALUE     // Double variable with min value
    private var longValue = 1L             // Long value
    private var longValue2 = 3000000000    // Long value

    fun numbersDemo() {
        println(bigInt)
        println("int = $int")
        println("int.toString() = ${int.toString()}")
        println("bigInt = $bigInt")
        if (variable is Int) {
            println("variable is of Int type")
        } else {
            println("variable is NOT of Int type")
        }
        println("doubleVar is Double = ${doubleVar is Double}")
//         println("bigInt is Char = ${bigInt is Char}")
        println("3.14 to Int = " + (3.14.toInt()))
        println("65 to Char = " + (65.toChar()))
        println("char to Double = " + ('░'.code.toDouble()))
//        int = longValue   // Error - Type mismatch. Because Kotlin has no implicit conversion.
        int = longValue.toInt();  // This way will do
    }

    fun generateAnswerString5() = if (variable == 42) "yes" else "no"
}
