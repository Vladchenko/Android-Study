package com.example.vladislav.androidstudy.kotlin.demo.functional

/**
 * https://play.kotlinlang.org/byExample/05_Collections/12_flatMap
 */
class FlatMap {
    private val fruitsBag = listOf("apple","orange","banana","grapes")  // 1
    private val clothesBag = listOf("shirts","pants","jeans")           // 2
    private val cart = listOf(fruitsBag, clothesBag)                    // 3
    private val mapBag = cart.map { it }                                // 4
    private val flatMapBag = cart.flatMap { it }                        // 5
    fun printMaps() {
        println(mapBag)     // [[apple, orange, banana, grapes], [shirts, pants, jeans]]
        println(flatMapBag)     // [apple, orange, banana, grapes, shirts, pants, jeans]
    }
}