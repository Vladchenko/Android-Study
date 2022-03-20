package com.example.vladislav.androidstudy.kotlin.demo

/**
 * Classes operating in Kotlin
 *
 * @author Yanchenko Vladislav on 26.08.2020.
 */
class Classes {}

class SomeClass      // Declaration of a class, without its body
class SomeClass1 {}
class Person constructor(firstName: String) { /*...*/ } // A class in Kotlin can have a
// primary constructor and one or more secondary constructors. The primary constructor
// is part of the class header: it goes after the class name (and optional type parameters).
class Person2(firstName: String) {
    var nickName: String? = null
        set(value) {    // Defining a setter
            field = value
            println("the nickName is $value")
        }
        get() {    // Defining a getter
            println("the returned value is $field")
            return field
        }
} // If the primary constructor does not have
// any annotations or visibility modifiers, the constructor keyword can be omitted

// Creating singleton
object Resource {
    val name = "Name"
}

// Anonymous class
val base = object : Base("Rose") {
    // In this class one can override any properties or functions
}

class FinalRectangle: Rectangle() {
    final override fun draw() { /* ... */ } //Final method cannot be overridden further on.
}

class FilledRectangle: Rectangle() {
    override fun draw() { /* ... */ }   // Overridden methods are always open (can be overridden further on).
//    val borderColor: String get() = "black"

    inner class Filler {
        fun fill() { /* ... */ }
        fun drawAndFill() {
            super@FilledRectangle.draw() // Calls Rectangle's implementation of draw()
            fill()
            // Uses Rectangle's implementation of borderColor's get()
            println("Drawn a filled rectangle with color ${super@FilledRectangle.borderColor}")
            // FilledRectangle's implementation
//            println("Drawn a filled rectangle with color $borderColor")
        }
    }
}

// Class can be inherited from
open class Rectangle {
    val borderColor: String get() = "black"
    open fun draw() { /* ... */ }   // Method that can be overridden.
    open fun getSquare() { /* ... */ }   // final method (can NOT be overridden).
}

interface Polygon {
    fun draw() { /* ... */ } // interface members are 'open' by default
}

class Square() : Rectangle(),
    Polygon {
    // The compiler requires draw() to be overridden:
    override fun draw() {
        super<Rectangle>.draw() // call to Rectangle.draw()
        super<Polygon>.draw() // call to Polygon.draw()
    }
}



open class Base(val name: String) {     // "open" means one can inherit from such a class

    init { println("Initializing Base") }

    open val size: Int =
        name.length.also { println("Initializing size in Base: $it") }
}

class Derived(
    name: String,
    val lastName: String
) : Base(name) {    // Calling a parent constructor with its parameter
    //Some code
}

class Derived2(
    name: String,
    val lastName: String
) : Base(name.capitalize().also { println("Argument for Base: $it") }) {

    init { println("Initializing Derived") }

    override val size: Int =
        (super.size + lastName.length).also { println("Initializing size in Derived: $it") }
}

