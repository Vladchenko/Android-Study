package com.example.vladislav.androidstudy.kotlin.demo

/**
 * https://play.kotlinlang.org/byExample/05_Collections/12_flatMap
 */
class FlatMap {
    val fruitsBag = listOf("apple","orange","banana","grapes")  // 1
    val clothesBag = listOf("shirts","pants","jeans")           // 2
    val cart = listOf(fruitsBag, clothesBag)                    // 3
    val mapBag = cart.map { it }                                // 4
    val flatMapBag = cart.flatMap { it }                        // 5
    fun printMaps() {
        println(mapBag)     // [[apple, orange, banana, grapes], [shirts, pants, jeans]]
        println(flatMapBag)     // [apple, orange, banana, grapes, shirts, pants, jeans]
    }
}