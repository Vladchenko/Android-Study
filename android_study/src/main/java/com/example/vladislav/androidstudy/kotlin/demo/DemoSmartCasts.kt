package com.example.vladislav.androidstudy.kotlin.demo

/**
 * Demonstration of smartcasts.
 *
 * @author Yanchenko Vladislav
 * @since 07.02.2021
 */
class DemoSmartCasts {
    fun evaluate() = eval(Sum(Num(1), Num(2)))
}

interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr

fun eval(e: Expr): Int {
    when(e) {
        is Num -> e.value
        is Sum -> eval(e.right) + eval(e.left)
    }
    throw IllegalArgumentException("Unknown expression")
}
