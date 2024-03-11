package com.example.vladislav.androidstudy.kotlin.demo

/** https://kotlinlang.org/docs/typecasts.html#smart-casts */
class TypeCheckAndCastsDemo {
    fun demoCasts() {
        val x: Any = 0
        // x is automatically cast to String on the right-hand side of `||`
        if (x !is String || x.length == 0) return

        // x is automatically cast to String on the right-hand side of `&&`
        if (x is String && x.length > 0) {
            print(x.length) // x is automatically cast to String
        }

        // Smart casts work for when expressions and while loops as well:
        when (x) {
            is Int -> print(x + 1)
            is String -> print(x.length + 1)
            is IntArray -> print(x.sum())
        }
    }
}