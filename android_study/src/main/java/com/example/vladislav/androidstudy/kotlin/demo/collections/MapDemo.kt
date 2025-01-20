package com.example.vladislav.androidstudy.kotlin.demo.collections

/**
 * Map<K, V> demonstration.
 * Although it does not implement Collections, it is placed to a collections package for convenience.
 *
 * @author Yanchenko Vladislav
 * @since 25.01.2021
 */
class MapDemo {

    companion object {

        private val list = listOf(1, 2, 3)
        private val list2 = listOf(10, 20, 30)
        private val lettersList = listOf("a", "b", "c")
        private val map1 = mapOf("a" to 1, "b" to 2, "c" to 3)   // readonly map
        private val map2 = mutableMapOf("a" to 1, "b" to 2, "c" to 3)   // variable contents map

        private fun addItem(map: MutableMap<String, Int>, key: String, value: Int) {
            map[key] = value
        }

        private fun printEntries(map: Map<String, Int>) {
            map.entries.forEach { print("$it ") }
        }

        fun mapDemo() {
            println(map1["key"])
            map1.entries.forEach(::println)
            map1.forEach(::println)
            map1.forEach { (key, value) -> print("$key -> $value, ") }
            println()
            for ((key, value) in map1) {
                print("$key -> $value,  ")
            }
            println()
            val mutableMap =
                mutableMapOf(1 to "1", 2 to "2", 3 to "3") // Map that one can add values to
            mutableMap.put(4, "4")
            mutableMap[4] = "4"     // This assignment is preferred over previous one
            addItem(map2, "4", 4)
            printEntries(map2)
            println()
            // Making a map from 2 lists
            println("list.zip(lettersList).toMap() = ${list.zip(lettersList).toMap() }")
        }

        fun flatMapDemo() {
            // flatten() could be used instead of flatMap()
            val result = listOf(listOf(1, 2, 3), listOf(4, 5, 6)).flatMap { it }
            println(result) // [1, 2, 3, 4, 5, 6]
        }

        data class Person(val name: String, val city: String, val phone: String)

        private val people = listOf(
            Person("John", "Boston", "+1-888-123456"),
            Person("Sarah", "Munich", "+49-777-789123"),
            Person("Svyatoslav", "Saint-Petersburg", "+7-999-456789"),
            Person("Vasilisa", "Saint-Petersburg", "+7-999-123456")
        )

        private val phoneBook = people.associateBy { it.phone } // {+1-888-123456=Person(name=John, city=Boston, phone=+1-888-123456), ...                          // 3
        private val cityBook = people.associateBy(Person::phone, Person::city) // {+1-888-123456=Boston, ...
        private val peopleCities = people.groupBy(Person::city, Person::name) // {Boston=[John], ...
        private val lastPersonCity = people.associateBy(Person::city, Person::name) // {Boston=John, ...

        fun peopleDemo() {
            println(phoneBook)
            println(cityBook)
            println(peopleCities)
            println(lastPersonCity)
        }
    }
}
