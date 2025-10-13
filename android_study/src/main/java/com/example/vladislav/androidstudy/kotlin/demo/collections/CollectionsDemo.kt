package com.example.vladislav.androidstudy.kotlin.demo.collections

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.persistentSetOf

class CollectionsDemo {

    /**
     * Code examples from https://developer.alexanderklimov.ru/android/kotlin/collection.php
     */
    fun demo() {
        val names = listOf("Барсик", "Мурзик", "Рыжик")
        val sizes = listOf("большой", "средний", "совсем котёнок")
        // zip() возвращает новый список (коллекцию пар Pair)
        val catMap = names.zip(sizes).toMap()   // Converting Pairs to Map. Also could be used (names zip sizes).toMap()
        // обращаемся к ключу
        println(catMap["Рыжик"]) // совсем котёнок

        val pairs = listOf(Pair("Россия", "рубль"), Pair("США", "доллар"), Pair("Украина", "гривна"))
        println(pairs.unzip().toString()) // выводит ([Россия, США, Украина], [рубль, доллар, гривна])

        // Хотя мы считаем, что стандартные коллекции, создаваемые через listOf(), setOf(), mapOf(), являются коллекция только для чтения, они в реальности не совсем неизменяемые. Мы можем изменить их следующим образом.
        val list5 = mutableListOf("A", "B", "C")
        val readOnlyList: List<String> = list5
        list5.add("D")  // Modifies the original list
        println(readOnlyList) // Output: [A, B, C, D]

        (readOnlyList as MutableList).add("E")
        println(readOnlyList) // Output: [A, B, C, D, E]

        // Создавать неизменяемые коллекции можно следующим образом.
        val immutableList = persistentListOf("A", "B", "C")
        val immutableSet = persistentSetOf(1, 2, 3)
        val immutableMap = persistentMapOf("key1" to 100, "key2" to 200)
        // Если мы попытаемся модифицировать содержимое коллекций, то будет создана новая коллекция, а старая останется без изменений.

        val newList = immutableList.add("D") // Creates a new list
        println(newList)  // Output: [A, B, C, D]
        println(immutableList)  // Output: [A, B, C]

        val newMap = immutableMap.put("key3", 300)
        println(newMap)   // Output: {key1=100, key2=200, key3=300}
        println(immutableMap)  // Output: {key1=100, key2=200}
        // Неизменяемые коллекции хорошо работают в Compose, позволяя избежать рекомпозиции.
    }
}