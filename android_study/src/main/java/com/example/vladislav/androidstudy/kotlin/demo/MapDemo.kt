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
            for ((key, value) in map1) {
                print("$key -> $value,  ")
            }
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

        data class Person(val name: String, val city: String, val phone: String) // 1

        val people = listOf(                                                     // 2
            Person("John", "Boston", "+1-888-123456"),
            Person("Sarah", "Munich", "+49-777-789123"),
            Person("Svyatoslav", "Saint-Petersburg", "+7-999-456789"),
            Person("Vasilisa", "Saint-Petersburg", "+7-999-123456"))

        val phoneBook = people.associateBy { it.phone }                          // 3
        val cityBook = people.associateBy(Person::phone, Person::city)           // 4
        val peopleCities = people.groupBy(Person::city, Person::name)            // 5
        val lastPersonCity = people.associateBy(Person::city, Person::name)

        fun peopleDemo() {
            println(phoneBook)
            println(cityBook)
            println(peopleCities)
            println(lastPersonCity)
        }
    }
}
