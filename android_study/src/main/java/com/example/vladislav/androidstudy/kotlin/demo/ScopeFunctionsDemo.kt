package com.example.vladislav.androidstudy.kotlin.demo

/**
 * Check https://kotlinlang.org/docs/scope-functions.html?_gl=1%2Acumodz%2A_gcl_au%2AODU4MTE0NDU2LjE3NTc2ODk2MDk.%2A_ga%2ANzI4NDY5NTQwLjE3NTc2ODk2MTE.%2A_ga_9J976DJZ68%2AczE3NTc2OTMxNzIkbzIkZzEkdDE3NTc3MDAzMjUkajIxJGwwJGgw&_cl=MTsxOzE7Y1ZoeldHWThQZlM4VDlhU3FuNUxTa2JLdllWUW9ZbVcyZ3Jadmg5Y2kxZVNMVkVVQVJPUExOTDg3Y1RZNExqNTs%3D#scope-functions.md
 */
class ScopeFunctionsDemo {

    fun withDemo() {    // Calling multiple methods on an object instance (with)
        class Turtle {
            fun penDown() {
                /** Empty for demo */
            }

            fun penUp() {
                /** Empty for demo */
            }

            fun turn(degrees: Double) {
                /** Empty for demo */
            }

            fun forward(pixels: Double) {
                /** Empty for demo */
            }
        }

        val myTurtle = Turtle()
        with(myTurtle) {
            //draw a 100 pix square
            penDown()
            for (i in 1..4) {
                forward(100.0)
                turn(90.0)
            }
            penUp()
        }
    }
}