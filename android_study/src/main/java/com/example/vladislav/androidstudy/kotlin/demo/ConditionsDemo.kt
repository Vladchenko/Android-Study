package com.example.vladislav.androidstudy.kotlin.demo

import java.lang.reflect.Array.getInt

/**
 * Conditions demonstration.
 *
 * @author Yanchenko Vladislav
 * @since 09.03.2021
 */

fun ifDemo(param: Int) {
    val result = if (param == 1) {
        "one"
    } else if (param == 2) {
        "two"
    } else {
        "three"
    }
    // In Kotlin, there is no ternary operator, because, unlike in Java, the if expression returns a value.
}

fun ifDemo2(param: Int) = if (param == 1) {
    "one"
} else if (param == 2) {
    "two"
} else {
    "three"
}

fun whenDemo() {
    val answerString: String = when {
        COUNT == 42 -> "I have the answer."
        COUNT > 35 -> "The answer is close."
        else -> "The answer eludes me."
    }
    println(answerString)
}

fun whenDemo2(color: String): Int {
    return when (color) {
        "Red" -> 0
        "Green" -> 1
        "Blue" -> 2
        else -> throw IllegalArgumentException("Invalid color param value")
    }
}

fun whenDemo3(color: String) = when (color) {
    "Red" -> 0
    "Green" -> 1
    "Blue" -> 2
    else -> throw IllegalArgumentException("Invalid color param value")
}

fun whenDemo4(x: Int) =
    when (x) {
        -1, 0 -> print("0 or 1")
        getInt(x) -> print("s encodes x")
        in 1..10 -> print("x is in the range")
        in validNumbers -> print("x is valid")
        !in 10..20 -> print("x is outside the range")
        else -> print("none of the above")
    }

fun whenDemo5(c1: Color, c2: Color) = when (setOf(c1, c2)) {
    setOf(Color.RED, Color.YELLOW) -> Color.ORANGE
    setOf(Color.YELLOW, Color.BLUE) -> Color.GREEN
    setOf(Color.BLUE, Color.VIOLET) -> Color.INDIGO
    else -> throw Exception("Dirty color")
}

fun whenDemo6(c1: Color, c2: Color) =
    when {
        (c1 == Color.RED && c2 == Color.YELLOW)
            || (c1 == Color.YELLOW && c2 == Color.RED) -> Color.ORANGE
        (c1 == Color.YELLOW && c2 == Color.BLUE)
            || (c1 == Color.BLUE && c2 == Color.YELLOW) -> Color.GREEN
        (c1 == Color.BLUE && c2 == Color.VIOLET)
            || (c1 == Color.VIOLET && c2 == Color.BLUE) -> Color.INDIGO
        else -> throw Exception("Dirty color")
    }

// Another demo of "when"
fun hasPrefix(x: Any) =
    when (x) {
        is String -> x.startsWith("prefix")
        else -> false
    }

fun getInt(x: Int): Int = x * x

private const val COUNT = 42       // This is a constant, type is automatically inferred as int
private val validNumbers = arrayOf(1, 2, 3)
