package com.example.vladislav.androidstudy.kotlin.demo

/**
 * Демо работы с Map
 *
 * @author Yanchenko Vladislav
 * @since 25.01.2021
 */
class MapDemo {

    companion object {

        private val list = listOf(1,2,3)
        private val list2 = listOf(10,20,30)
        private val map1 = mapOf("a" to 1, "b" to 2, "c" to 3)   // readonly map
        private val map2 = mutableMapOf("a" to 1, "b" to 2, "c" to 3)   // variable map

        fun addItem(map: MutableMap<String, Int>, key: String, value: Int) {
            map[key] = value
        }

        fun printEntries(map: MutableMap<String, Int>) {
            map.entries.forEach { println(it) }
        }

        fun mapDemo() {
            println(map1["key"])
            map1.forEach { key, value -> println("$key -> $value") }
            val mutableMap =
                mutableMapOf(1 to "1", 2 to "2", 3 to "3") // Map that one can add values to
            mutableMap.put(4, "4")
            addItem(map2, "4", 4)
            printEntries(map2)
            println()
            for ((key, value) in map1) {
                println("$key = $value")
            }
        }

        fun flatMapDemo() {
            val result = listOf(listOf(1, 2, 3), listOf(4, 5, 6)).flatMap { it }    // flatten() could be used
            // instead of flatMap()
            println(result) // [1, 2, 3, 4, 5, 6]
        }
    }
}
