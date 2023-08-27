package com.example.vladislav.androidstudy.kotlin.demo

/**
 * This is similar to a static area in Java.
 *
 * Companion object can also have a name.
 * companion object SomeName {
 *
 * }
 *
 * @author Yanchenko Vladislav on 28.08.2020.
 */
class CompanionObject private constructor() {
    companion object {
        private const val SOME_CONSTANT: String = "SomeConstant"
        fun createName() = SOME_CONSTANT
    }
}
