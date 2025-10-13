package com.example.vladislav.androidstudy.kotlin.demo.collections

import com.example.vladislav.androidstudy.kotlin.demo.joinToString

/**
 * List operations demonstration.
 */
class ListDemo {

    /**
     * Some list ops demo
     */
    fun listDemo() {
        val someList = arrayOf(1, 2, 3, 4).asList()
        val list = listOf(
            "q",
            "a",
            "z",
            "zz"
        )    // Immutable list (one cannot add items to it or remove from)
        println(list[0])    // Same to println(list.get(0))
        list.getOrElse(5) { list[0] }   // Answer: q    // Retrieves first element instead of 5th, which is absent.
        list.getOrNull(5)   // Retrieves null, instead of IOOB exception
        list.forEach { item -> println(item) }  // Printing list
        list.forEach(System.out::print)     // Printing list
        list.forEach(::print)     // Printing list
        for (item in list) {    // Printing list
            print(item)
        }
        println(list.map { it })    // Printing list
        println(list) //[1,2,3,4,5] invokes toString()  // Printing list
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return@forEach
            print(it)
        }
        val dest = list.mapIndexed { index, i -> "$index: $i" }
        println(dest.toString()) // выводит [0: 8, 1: 4, 2: 2]  // Also check mapNotNull, mapIndexedNotNull
        list.first() // First element in list
        list.last() // Last element in list
        println(list.last { it.contains("z") })   // Answer: zz
        println(listOf(1, 2, 3, 4, 5).fold(0) { total, next -> total + next })  // Answer: 15
        println(
            listOf(
                1,
                2,
                3
            ).reduce { total, next -> total + next }) // Answer: 6. Same as .fold(), but without
        // an initial state
        println(
            listOf(
                1,
                2,
                3
            ).sumOf { it + 5 }) // Answer: 21. Increasing all the items in 5, then sum them up
        val list2 = listOf(1, 2, 3, 4, 5)
        // Is there an even element ?
        println(list2.any { it % 2 == 0 }) // true
        // Is there an element bigger than 10 ?
        println(list2.any { it > 10 }) // false
        // "!any" expression can be replaced with "all".    "!all" expression can be replaced with "any".
        // Are all elements less than 7 ?
        println(list2.all { it < 7 }) // true
        // None of the elements is bigger than 6 ?
        println(list2.none { it > 6 }) // true
        //  from book - Kotlin. Паттерны проектирования и лучшие практики, 3-е изд.
        println("Are they the same? ${emptyList<String>() === listOf<String>()}") // Are they the same? true
        println("Are they the same? ${emptyList<String>() === listOf<Int>()}") // Are they the same? true
    }

    /**
     * https://developer.alexanderklimov.ru/android/kotlin/list.php
     */
    fun listDemo2() {

        // Добавить более ранние примеры

        data class Cat(
            val name: String,
            val age: Int,
            val weight: Double
        )

        val cats8 = mutableListOf<Cat>()
        cats8.add(Cat("Мурзик", 9, 5400.0))
        cats8.add(Cat("Рыжик", 5, 6500.0))
        cats8.add(Cat("Василий", 4, 5100.0))

        val junior = cats8.minOf { it.age }
        //Есть родственные функции minBy(), maxBy(), minWith(), maxWith().

        // sumBy() подсчитывает сумму всех элементов, после того, как они подверглись изменению.
        // увеличиваем все элементы на 5, а потом складываем
        println(listOf(1, 2, 3).sumBy { it + 5 }) // 21 //sumBy deprecated use sumOf instead
        // Считаем сумму длин строк
        println(listOf("cat", "kitten").sumBy { it.length })    //sumBy deprecated use sumOf instead
        // При работе с числами Double используйте функцию sumByDouble().

        val cats13 = listOf(
            Cat("Murzik", age = 10, weight = 1.4),
            Cat("Barsik", age = 5, weight = 3.1),
            Cat("Ryzik", age = 1, weight = 2.2))

        // суммируем разные типы
        val total = cats13.sumOf { it.age * it.weight} // Перемножаем возраст на вес и суммируем эти значения для всех котов
        println(total)
        val count = cats13.sumOf { it.age } // Int
        println(count)
        // Также появились minOfWith() и maxOfWith(), работающие с компаратором.

        println(listOf(1, 2, 3, 4, 5).drop(2)) // [3, 4, 5]
        println(listOf(1, 2, 3, 4, 5).dropWhile({ it < 3 }))  // [3, 4, 5]
        println(listOf(1, 2, 3, 4, 5).dropLastWhile { it > 4 })  // [1, 2, 3, 4]
        // оставим первые два элемента
        println(listOf(12, 32, 34, 45, 45).take(2)) // [12, 32]
        // оставим последние два элемента
        println(listOf(12, 32, 34, 45, 45).takeLast(2)) // [45, 45]
        println(listOf(1, 2, 3, 4, 5).takeWhile { it < 3 }) // [1, 2]
        // takeWhile() и filter(). Первая функция будет отбирать элементы, пока выполняется условие и прервётся,
        // а вторая пройдётся по всему списку до конца.
        // takeIf() будет выбирать элементы, если выполняется условие (предикат).
        val cats = listOf("Рыжик", "Мурзик", "Барсик", "Васька")
        cats.takeIf {
            it.contains("Пушистик")
        }.apply {
            this?.forEach {
                println(it)
            }
        }   // Ничего не выводится
        // Выбрать элементы, если список не содержит Пушистика
        val cats2 = listOf("Рыжик", "Мурзик", "Барсик", "Васька")
        cats2.takeUnless {
            it.contains("Пушистик")
        }.apply {
            this?.let {
                println(it)
            }
        }   // [Рыжик, Мурзик, Барсик, Васька]
        val cats3 = listOf("Барсик", "Мурзик", "Пикассо", "Васька", "Рыжик")
        // Содержит "ик", сортируем по длине слова
        val filtered = cats3.filter { it.contains("ик") }.sortedBy { it.length }
        println(filtered)   // [Рыжик, Барсик, Мурзик, Пикассо]
        val cats4 = listOf("Барсик", "Мурзик", "Пикассо", "Васька", "Рыжик", "Пушок")
        // Начинается на "П" и оканчивается на "к"
        val filtered2 = cats4.filter { it.startsWith('П') }.filter { it.endsWith('к') }
        println(filtered2) // Пушок
        // оставляем нечётные числа
        println(listOf(1, 2, 3, 4, 5).filterNot { it % 2 == 0 }) // [1, 3, 5] It is same as
        println(listOf(1, 2, 3, 4, 5).filter { it % 2 != 0 }) // [1, 3, 5]
        // filterNotNullTo() уберёт все элементы null и добавит оставшиеся элементы в новый список.
        val cats5 = listOf("Мурзик", null, "Барсик", "Рыжик", null, "Васька", "Пушистик", null)
        val allCats = mutableListOf("Мурка", "Милка")
        cats5.filterNotNullTo(allCats)
        println(allCats.joinToString()) // Мурка, Милка, Мурзик, Барсик, Рыжик, Васька, Пушистик
        // содержится ли в списке Барсик и Мурзик
        val cats6 = listOf("Мурзик", "Барсик", "Рыжик")
        println("Барсик и Мурзик в списке: ${cats6.containsAll(listOf("Барсик", "Мурзик"))}")
        println(
            listOf(
                1,
                2,
                3,
                4,
                5
            ).elementAt(3)
        ) // 4    // Также доступны elementAtOrElse, elementAtOrNull.
        listOf(
            1,
            2,
            3,
            4,
            5
        ).find { it > 3 }   // 4 (find находит только 1-е вхождение. Для всех надо использовать filter)
        listOf(1, 2, 3, 4, 5).findLast { it > 3 }   // 5
        // Функция single() вернёт один уникальный элемент из списка. Если элементов, соответствующих условию, будет
        // несколько, то вернёт null.
        println(listOf(1, 6, 3, 4, 5).singleOrNull { it % 3 == 0 }) // null

        val list3 = mutableListOf(1, 2, 3, 4, 5)
        list3.reverse() // Reverses a mutable list itself (no need to write list3 = list3.reverse())
        println("list3.reverse() = $list3")  // [5,4,3,2,1]
        println(list3.reversed())    // Prints items in backwards order [4,3,2,1]

        val list4 = mutableListOf(1, 20, 3, 40, 5)
        list4.sort() // Sorts a mutable list itself (no need to write list4 = list4.sort())
        println("list3.sort() = $list4")  // [1,3,5,20,40]
        println(list4.sorted())    // Sorts items [1,2,3,4]
        println(listOf(4, 8, 2, 4, 9).sortedDescending())    // Sorts items [9,8,4,4,2]
        println(
            "listOf(4,8,2,4,9).sortedBy { it > 4 } = ${
                listOf(
                    4,
                    8,
                    2,
                    4,
                    9
                ).sortedBy { it > 4 }
            }"
        ) // [4, 2, 4, 8, 9]
        println(
            "listOf(4,8,2,4,9).sortedByDescending { it < 9 } = ${
                listOf(
                    4,
                    8,
                    2,
                    4,
                    9
                ).sortedByDescending { it < 9 }
            }"
        ) // [4, 8, 2, 4, 9]
        println(
            listOf(2, 9, 5, null, 1, 5, 0, 3, null).sortedWith(
                // nullsLast(compareBy { it })  // [0, 1, 2, 3, 5, 5, 9, null, null]
                nullsFirst(compareBy { it })    // [null, null, 0, 1, 2, 3, 5, 5, 9]
            )
        )

        val cats7 = mutableListOf<Cat>()
        cats7.add(Cat("Мурзик", 4, 5400.0))
        cats7.add(Cat("Рыжик", 5, 6500.0))
        cats7.add(Cat("Василий", 4, 5100.0))
        cats7.add(Cat("Мурзик", 6, 5400.0))
        println(cats7.sortedWith(
            compareBy(
                { it.name }, { it.age }
            )
        ))  // [Cat(name=Василий, age=4, weight=5100), Cat(name=Мурзик, age=4, weight=5400), Cat(name=Мурзик, age=6, weight=5400), Cat(name=Рыжик, age=5, weight=6500)]

        //TODO Stopped @ Сортируем по длине имён в порядке возрастания, используя Comparator.
        // http://developer.alexanderklimov.ru/android/kotlin/collection.php (Только по ВПН)

        val mutableList = mutableListOf(
            "1",
            "2",
            "3",
            "1",
            "2",
            "3",
            "1",
            "2",
            "3"
        )    // Mutable list (one can add items to it)
        mutableList.removeAll { it == "1" }
        println(mutableList)    // [2, 3, 2, 3, 2, 3]
        mutableList.remove("2")     // Removes 1st matching element
        println(mutableList)    // [3, 2, 3, 2, 3]
    }
}
