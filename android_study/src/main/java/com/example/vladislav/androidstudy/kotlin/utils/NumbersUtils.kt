package com.example.vladislav.androidstudy.kotlin.utils

import kotlin.math.pow

/**
 * Calculate two numbers with some operation. Entries are read from console.
 */
@Throws(IllegalArgumentException::class)
fun calculateTwoNumbers(): Double {
    val argument1 = retrieveNumber(readLine())
    val argument2 = retrieveNumber(readLine())
    val operation = readLine()
    val availableOperations = "%^&|*-+"
    return calculateTwoNumbers(argument1, argument2, operation, availableOperations)
}

/**
 * Calculate two numbers [argument1], [argument2] with some [operation]. Entries are read from console.
 * [availableOperations] present operations performable in this code.
 */
@Throws(IllegalArgumentException::class)
fun calculateTwoNumbers(argument1: Double, argument2: Double, operation: String?, availableOperations: String): Double {
    checkOperation(operation, availableOperations)
    return when (operation) {
        "%" -> argument1 % argument2
        "^" -> argument1.pow(argument2)
        "&" -> (argument1.toInt() and argument2.toInt()).toDouble()
        "|" -> (argument1.toInt() or argument2.toInt()).toDouble()
        "-" -> argument1 - argument2
        "+" -> argument1 + argument2
        "*" -> argument1 * argument2
        else -> throw IllegalArgumentException("Wrong operation")
    }
}

@Throws(IllegalArgumentException::class)
private fun retrieveNumber(argument: String?) =
    argument.toString().toDoubleOrNull() ?: throw IllegalArgumentException("Argument is not a number")

@Throws(IllegalArgumentException::class)
private fun checkOperation(operation: String?, availableOperations: String) =
    with (operation) {
        if(isNullOrBlank()) {
            throw IllegalArgumentException("Operation is empty")
        }
        if (this.length > 1) {
            throw IllegalArgumentException("Operation has to be 1 symbol long and be one of $availableOperations")
        }
        if (operation.toString() !in availableOperations) {
            throw IllegalArgumentException("Operation is not supported")
        }
    }

/**
 * Get all divisors when quotient is integral.
 */
fun Int.getAllDivisorsForZeroQuotient() =
    listOf(1..this/2).flatten().filter{ this.mod(it) == 0 }
// More imperative implementation way
//: List<Int> {
//    val ints = mutableListOf<Int>()
//    for (i in 2..this/2) {
//        if (this % i == 0) {
//            ints.add(i)
//        }
//    }
//    return ints
//}
