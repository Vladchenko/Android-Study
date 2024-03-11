package com.example.vladislav.androidstudy.kotlin.demo

/**
 * Demonstration of a custom getter.
 * https://kotlinlang.org/docs/properties.html#getters-and-setters
 *
 * @author Yanchenko Vladislav
 * @since 07.02.2021
 */
class CustomAccessor {

    /*
     * Suppose you declare a rectangle that can say whether it’s a square. You don’t need to store that information as
     * a separate field, because you can check whether the height is equal to the width on the go
     */
    class Rectangle(val height: Int, val width: Int) {
        val isSquare: Boolean
            get() {
                return height == width
            }
    }

    class Rectangle2(val height: Int, val width: Int) {
        val isSquare: Boolean
            get() = height == width     // More concise way to write a getter
    }
}
