package com.example.vladislav.androidstudy.kotlin.demo

/**
 * Abstract classes demo
 *
 * @author Yanchenko Vladislav
 * @since 15.03.2021
 */
abstract class Animated {           // This class is abstract: you can’t create an instance of it.
    abstract fun animate()          // This function is abstract: it doesn’t have an implementation and must be overridden in subclasses.
    open fun stopAnimating() { }    // Non-abstract functions in abstract classes aren’t open by default but can be marked as open.
    fun animateTwice() {}           // Non-abstract functions in abstract classes aren’t open by default but can be marked as open.
}
