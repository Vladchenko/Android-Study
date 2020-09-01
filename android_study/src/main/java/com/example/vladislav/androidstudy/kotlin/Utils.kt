package com.example.kotlinstudy

/**
 * Some utils class
 *
 * @author Yanchenko Vladislav on 05.08.2020.
 */
public class Utils {

    companion object {

        public fun charToInt(c: Char): Int {
            if (c !in '0'..'9') throw IllegalArgumentException("Argument is wrong -> $c, it has to be a digit")
            else return c.toInt() - 48
        }

        public fun <T> printArray(array: Array<T>) {
//            array.forEach(System.out::print)
//            println(array.joinToString(" "))
            for (element in array) {
                println(element)
            }
        }
    }
}
