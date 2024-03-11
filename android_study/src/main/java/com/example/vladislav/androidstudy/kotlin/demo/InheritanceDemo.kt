package com.example.vladislav.androidstudy.kotlin.demo

/**
 * Inheritance demo
 * Also check https://kotlinlang.org/docs/inheritance.html#derived-class-initialization-order
 */
class InheritanceDemo {

    open class Base(p: Int)

    class Derived(p: Int) : Base(p) {   // Constructor has to be stated
    }

    abstract class Painter {
        abstract fun draw()     // abstract stands for open also
        // Read https://kotlinlang.org/docs/inheritance.html#overriding-properties
        open val vertexCount: Int = 0
    }

    class PainterImpl: Painter() {
        final override fun draw() { /*...*/ }   // final used here to prohibit re-overriding
        override val vertexCount = 4
    }
}