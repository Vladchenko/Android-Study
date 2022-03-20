package com.example.vladislav.androidstudy.kotlin.demo

/**
 * This is similar to a static area in Java
 *
 * @author Yanchenko Vladislav on 28.08.2020.
 */
class CompanionObject private constructor() {
    companion object {
        const val NAME: String = "Some"
        fun createName() =
            NAME
    }
}
