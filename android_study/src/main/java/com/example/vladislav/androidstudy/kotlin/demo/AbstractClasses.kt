package com.example.kotlinstudy.demo

/**
 * https://kotlinlang.org/docs/reference/classes.html#abstract-classes
 *
 * @author Yanchenko Vladislav on 26.08.2020.
 */
abstract class AbstractClasses {

    /**
     * "open" class means it can be inherited from, otherwise it is final.
     */
    open class Polygon {
        open fun draw() {}
    }

    abstract class Rectangle : Polygon() {
        abstract override fun draw()
    }

    abstract class Oval : Polygon() {
        override fun draw() {}
    }

}
