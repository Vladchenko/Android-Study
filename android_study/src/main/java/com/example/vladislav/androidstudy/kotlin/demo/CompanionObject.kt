package com.example.vladislav.androidstudy.kotlin.demo

/**
 * This is similar to a static area in Java.
 *
 * Companion object can also have a name.
 * companion object SomeName {
 * ...
 * }
 *
 * https://habr.com/ru/articles/721084/
 * ... Даже если члены объектов-компаньонов выглядят как статические члены в других языках, во время
 * выполнения они все равно остаются членами экземпляров реальных объектов и могут, например,
 * реализовывать интерфейсы.
 *
 * @author Yanchenko Vladislav on 28.08.2020.
 */
class CompanionObject private constructor() {
    companion object {
        private const val SOME_CONSTANT: String = "SomeConstant"
        fun createName() = SOME_CONSTANT
    }
}
