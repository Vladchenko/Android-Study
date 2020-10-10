package com.example.vladislav.androidstudy.kotlin.utils

/**
 * This method is same to the one placed inside of a companion object of a class
 */
fun isNotDigit(c: Char) = c !in '0'..'9'

/**
 * Some utils class
 *
 * @author Yanchenko Vladislav on 05.08.2020.
 */
class Utils {

    companion object {

        fun charToInt(c: Char): Int {
            if (c !in '0'..'9') throw IllegalArgumentException("Argument is wrong -> $c, it has to be a digit")
            else return c.toInt() - 48
        }

        fun <T> printArray(array: Array<T>) {
//            array.forEach(System.out::print)
//            println(array.joinToString(" "))
            for (element in array) {
                println(element)
            }
        }

        fun isLetter(c: Char) = c in 'a'..'z' && c in 'A'..'Z'
    }
}
