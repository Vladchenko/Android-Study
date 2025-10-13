package com.example.vladislav.androidstudy.kotlin.study.deepseek

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Some tasks asked to be made up by Deepseek LLM to practice kotlin
 */
class DeepseekTasks {
    /**
     * Условие: Напиши функцию findMaxOrNull, которая принимает список целых чисел List<Int>?
     * и возвращает максимальный элемент в этом списке. Если список пустой или равен null,
     * функция должна вернуть null.
     *
     * Используй безопасный вызов (?.), оператор Elvis (?:) и функцию maxOrNull().
     *
     * @param list
     * @return
     */
    fun findMaxOrNull(list: List<Int>?): Int? {
        return list?.maxOrNull()
    }

    /**
     * Условие: Напиши функцию filterAndTransform. Она принимает список строк List<String>.
     * Отфильтруй только те строки, которые имеют длину больше 3 символов.
     * Преобразуй отфильтрованные строки в верхний регистр.
     * Верни первый символ каждой преобразованной строки в виде итогового списка List<Char>.
     * Используй цепочку вызовов filter, map и еще одного map.
     *
     * @param list
     * @return
     */
    fun filterAndTransform(list: List<String>): List<Char> {
        return list.filter { it.length > 3 }.map { it.uppercase().first() }
    }

    /**
     * Работа с let, also и scope-функциями
     * Условие: У нас есть nullable переменная nullableString: String?.
     * Нужно написать код, который:
     * Если строка не null и не пустая, выведет ее длину и саму строку в формате: "Длина строки '$строка' равна $длина".
     * Если строка null или пустая, выведет "Строка пуста или null".
     * Требование: Для обработки не-null значения используй scope-функцию let. Для вывода информации внутри let используй функцию also.
     * Пример:
     * Для nullableString = "Hello" вывод: "Длина строки 'Hello' равна 5"
     * Для nullableString = "" или null вывод: "Строка пуста или null"
     */
    fun checkString(nullableString: String?) {
        nullableString?.let {
            if (it.isNotEmpty()) {
                println("Длина строки '$it' равна ${it.length}")
            }
        }
        if (nullableString.isNullOrEmpty()) {
            println("Строка пуста или null")
        }
    }

    /**
     * Deepseek solution 1
     */
    fun checkString1(nullableString: String?) {
        nullableString?.takeIf { it.isNotEmpty() }?.let {
            println("Длина строки '$it' равна ${it.length}")
        } ?: println("Строка пуста или null")
    }

    /**
     * Deepseek solution 2
     */
    fun checkString2(nullableString: String?) {
        if (!nullableString.isNullOrEmpty()) {
            println("Длина строки '$nullableString' равна ${nullableString.length}")
        } else {
            println("Строка пуста или null")
        }
    }

    /**
     * Объяви data class User(val name: String, val age: Int).
     *
     * Напиши функцию getOldestUser, которая принимает список пользователей List<User>
     * и возвращает пару (Pair), содержащую имя и возраст самого старшего пользователя.
     * Если список пустой, функция должна возвращать null.
     *
     * Дополнительное задание: В функции main используй деструктурирование, чтобы распаковать
     * Pair из getOldestUser (если она не null) и вывести результат в формате:
     * "Самый старший пользователь: $name, возраст: $age".
     */
    data class User(val name: String, val age: Int)

    fun getOldestUser(list: List<User>): Pair<String, Int>? {
        list.maxByOrNull { it.age }?.let {
            return it.name to it.age
        } ?: run {
            return null
        }
    }

    fun getOldestUser_(list: List<User>): Pair<String, Int>? {
        list.maxByOrNull { it.age }?.let {
            println("Самый старший пользователь: ${it.name}, возраст: ${it.age}")
            return it.name to it.age
        } ?: run {
            return null
        }
    }

    /**
     * Ответ Deepseek:
     *
     * Первый вариант (getOldestUser):
     * Минусы: Конструкция ?.let { ... } ?: run { ... } избыточна. let уже возвращает null,
     * если объект до него был null. run блок не нужен.
     *
     * Второй вариант (getOldestUser_):
     * Минусы: Функция выполняет две задачи: возвращает результат и выводит его на печать.
     * Это нарушает принцип единственной ответственности. Функция, которая что-то вычисляет,
     * не должна иметь side-effects (как вывод в консоль). Ее задача — только вернуть данные.
     */
    fun getOldestUser_Corrected(list: List<User>): Pair<String, Int>? {
        return list.maxByOrNull { it.age }?.let { oldestUser ->
            oldestUser.name to oldestUser.age
        }
    }

    fun destructuring() {
        val user = DeepseekTasks().getOldestUser(listOf(User("Vlad", 120)))
        user?.let { (name, age) ->
            println("$name is $age years old")
        }
    }

    /**
     * Напиши функцию describeValue, которая принимает аргумент типа Any? и возвращает строку с его
     * описанием по следующим правилам:
     * Если это null -> вернуть "Null"
     * Если это строка, причем пустая -> вернуть "Empty string"
     * Если это строка, не пустая -> вернуть "String of length ${value.length}"
     * Если это число (тип Int) -> вернуть "Integer number: $value"
     * Если это число (тип Double) -> вернуть "Double number: $value"
     * Для всего остального -> вернуть "Unknown type"
     * Требование: Используй выражение when и пользуйся преимуществами smart cast
     * (умного приведения типов) в его условиях.
     *
     * @param value
     * @return
     */
    fun describeValue(value: Any?): String {
        return when (value) {
            null -> "Null"
            is String -> if (value.isBlank()) "Blank string" else "String of length ${value.length}"
            is Int -> "Integer number: $value"
            is Double -> "Double number: $value"
            else -> "Unknown type"
        }
    }

    /**
     *  Коллекции и ассоциативные списки
     * Условие: У тебя есть список слов List<String>.
     * Напиши функцию groupWordsByFirstLetter, которая:
     * Группирует слова по их первой букве (игнорируй регистр, т.е. 'A' и 'a' должны считаться
     * одной группой).
     * Возвращает Map<Char, List<String>>, где ключ — это буква в верхнем регистре, а значение
     * — список слов, начинающихся на эту букву (в их оригинальном регистре).
     * Подсказка: Используй groupBy и mapKeys/mapValues.
     * Пример:
     * Вход: listOf("apple", "Banana", "orange", "avocado", "Blueberry")
     * Выход: {A=[apple, avocado], B=[Banana, Blueberry], O=[orange]}
     *
     * Анализ твоего решения:
     * Ошибки в логике:
     *
     * Неправильная группировка: В цикле для каждого слова word ты пытаешься положить в мап по
     * ключу word.first() результат фильтрации всего исходного списка words по первой букве текущего
     * слова. Это значит, что для каждого слова, начинающегося на одну и ту же букву, ты будешь
     * перезаписывать значение в мапе одним и тем же списком (всех слов на эту букву), вычисляя
     * его заново каждый раз. Это избыточно и неверно, так как в итоге мап будет содержать
     * правильные данные, но только благодаря тому, что ты каждый раз перезаписываешь значение
     * тем же самым списком.
     *
     * Игнорирование регистра: Задание требует игнорировать регистр ('A' и 'a' должны быть в
     * одной группе), но твой код этого не делает. Ключом становится первая буква слова в
     * оригинальном регистре. Для слов "apple" (a) и "Avocado" (A) будут созданы две разные группы.
     *
     * Неэффективность: На каждой итерации цикла (для каждого слова!) ты заново фильтруешь весь
     * список words. Для списка из N слов это приведет к N*N операций (сложность O(N²)), что очень
     * медленно для больших списков.
     *
     * @param words
     * @return
     */
    fun groupWordsByFirstLetter(words: List<String>): Map<Char, List<String>> {
        val result = mutableMapOf<Char, List<String>>()
        for (word in words) {
            result[word.first()] = words.filter { it.startsWith(word.first()) }
        }
        return result
    }

    /**
     * words.groupBy { ... } — эта функция группирует элементы коллекции по ключу, который
     * вычисляется в лямбде.
     * it.first().uppercaseChar() — ключом группировки становится первая буква каждого слова,
     * приведенная к верхнему регистру. Это решает требование игнорировать регистр.
     * uppercaseChar() преобразует символ в верхний регистр.
     * Функция groupBy сама вернет Map<Char, List<String>>, где значение — это список слов
     * в оригинальном регистре, которые попали в эту группу.
     * Всё! Функция groupBy делает всю работу за нас.
     *
     * @param words
     * @return
     */
    fun groupWordsByFirstLetter_Corrected(words: List<String>): Map<Char, List<String>> {
        return words.groupBy { it.first().uppercaseChar() }
    }

    /**
     * Напиши функцию для вычисления факториала этих чисел (произведение всех чисел) используя reduce
     *
     * ВАЖНО! При пустом падаваемом списке, reduce падает с UnsupportedOperationException: Empty collection can't be reduced
     *        В этом случае используй reduceOrNull.
     * @param listInts
     * @return
     */
    fun factorialFromList(listInts: List<Int>): Int? {
        return listInts.reduceOrNull { initial, next ->
            initial * next
        }
    }

    /**
     * Напиши функцию для вычисления суммы всех чисел, умноженной на 10 используя fold
     * (начальное значение 0)
     *
     * @param listInts
     * @return
     */
    fun multiplyEachAnd10FromList(listInts: List<Int>): Int {
        return listInts.fold(0) { initial, next ->
            initial + next
        } * 10
    }

    /**
     * Пример с ручной реализацией (менее предпочтительный вариант):
     *
     * @param words
     * @return
     */
    fun groupWordsByFirstLetter_Corrected2(words: List<String>): Map<Char, List<String>> {
        val result = mutableMapOf<Char, MutableList<String>>()
        for (word in words) {
            // Вычисляем ключ один раз для слова
            val key = word.first().uppercaseChar()
            // Достаем список для этого ключа. Если его нет — создаем новый пустой.
            val list = result.getOrPut(key) { mutableListOf() }
            // Добавляем слово в список
            list.add(word)
        }
        return result
    }

    /**
     * Напиши функцию simulateNetworkRequest, которая использует корутины.
     * Функция не принимает аргументов и возвращает String — результат "запроса".
     * Внутри функции используй delay для симуляции задержки сети в 1 секунду.
     * После задержки функция должна вернуть строку "Данные с сервера".
     * Функция должна быть suspend-функцией.
     * Напиши код в функции main, который использует runBlocking для запуска корутины, вызывает simulateNetworkRequest и выводит результат на экран.
     * @return
     */
    suspend fun simulateNetworkRequest(): String {
        delay(1000)
        return "Данные с сервера"
    }

    /**
     * Flow (потоки данных)
     * Условие:
     * Создай функцию countdownFlow(start: Int): Flow<Int>, которая:
     * Испускает (emits) числа от start вниз до 0 с задержкой в 1 секунду между каждым числом.
     * Используй flow { ... } builder и delay(1000) внутри.
     * В коде собери Flow (можно использовать runBlocking как раньше, раз мы вне Android-контекста):
     * Запусти countdownFlow(5)
     * Выводи каждое число в лог/консоль: println("Countdown: $it")
     * В конце выведи "Запуск!"
     *
     * @param start
     * @return
     */
    fun countdownFlow(start: Int): Flow<Int> =
        flow {
            for (i in start downTo 0) {
                delay(1000)
                emit(i)
            }
        }

    fun countdownFlow2(start: Int): Flow<Int> =
        flow {
            for (i in start downTo 0) {
                delay(1000)
                emit(i)
            }
        }

    /**
     * Условие: У тебя есть список списков:
     * kotlin
     * val listOfLists = listOf(
     *     listOf(1, 2, 3),
     *     listOf(4, 5),
     *     listOf(6, 7, 8, 9)
     * )
     * Нужно:
     * Оставить только списки с количеством элементов > 2 (filter)
     * Преобразовать каждый оставшийся список в список строк с префиксом "Number: " (map)
     * "Разгладить" результат в один общий список строк (flatMap)
     * Цель: Получить ["Number: 1", "Number: 2", "Number: 3", "Number: 6", "Number: 7", "Number: 8", "Number: 9"]
     * @return
     */
    fun someTasksWithList(): List<String> {
        val listOfLists = listOf(
            listOf(1, 2, 3),
            listOf(4, 5),
            listOf(6, 7, 8, 9)
        )
        return listOfLists
            .filter { it.size > 2 }
            .map { list -> list.map { listItem -> "Number: $listItem" } }
            .flatMap { it }
        // Deepseek optimized case
//        return listOfLists
//            .filter { it.size > 2 }
//            .flatMap { innerList ->
//                innerList.map { item -> "Number: $item" }
//            }
    }

    /**
     * Есть список пользователей:
     * data class User(val id: Int, val name: String, val department: String)
     * val users = listOf(
     *     User(1, "Alice", "HR"),
     *     User(2, "Bob", "IT"),
     *     User(3, "Charlie", "IT"),
     *     User(4, "Diana", "HR")
     * )
     * Нужно получить:
     * Группировку пользователей по отделам (отдел -> список пользователей)
     * Мапу для быстрого поиска пользователя по id (id -> пользователь)
     * Мапу id -> имя пользователя
     */
    fun someTasksWithList2() {
        data class User(val id: Int, val name: String, val department: String)

        val users = listOf(
            User(1, "Alice", "HR"),
            User(2, "Bob", "IT"),
            User(3, "Charlie", "IT"),
            User(4, "Diana", "HR")
        )
        val groupedUsers = users.groupBy { it.department }
        val mapped = users.associateBy { user -> user.id }
        val mapped2 = users.associate { user -> user.id to user.name }
    }

    /**
     * Работа с несколькими коллекциями
     * Условие:
     * Есть два списка — listOf("A", "B", "C") и listOf(1, 2, 3). Нужно объединить их в пары (первый элемент из первого списка с первым из второго и т.д.)
     * Есть список чисел listOf(1, 2, 3, 4, 5, 6). Нужно разделить его на две группы по признаку четности.
     * Для списка listOf(1, 2, 3, 4, 5) нужно получить "скользящие" группы по 3 элемента:
     * [1, 2, 3]
     * [2, 3, 4]
     * [3, 4, 5]
     */
    fun someTasksWithList3() {
        val list = listOf("A", "B", "C")
        val list2 = listOf(1, 2, 3)
        println(list zip list2) // Or list.zip(list2)   [(A, 1), (B, 2), (C, 3)]
        println(listOf(1, 2, 3, 4, 5, 6).partition { it % 2 == 0 }) // ([2, 4, 6], [1, 3, 5])
        println(
            listOf(1, 2, 3, 4, 5).windowed(
                3,
                1,
                false
            )
        )   // [[1, 2, 3], [2, 3, 4], [3, 4, 5], [4, 5], [5]]
    }

    /**
     * Сложная комбинация
     * Условие: Есть список строк: listOf("apple", "banana", "cherry", "date", "elderberry")
     * Нужно:
     * Оставить только строки длиной > 5 символов
     * Разбить каждую строку на список символов
     * Оставить только первые 3 символа каждой строки
     * Создать единый список всех символов
     * Подсчитать количество каждого символа в итоговом списке
     */
    fun someTasksWithList4() {
        val list = listOf("apple", "banana", "cherry", "date", "elderberry")
        println(list
            .filter { it.length > 5 }
            .map { string -> string.take(3).map { it } }
            .flatMap { string -> string.map { it } }
            .groupingBy { it }
            .eachCount())
    }

    /**
     * Сложная обработка вложенных структур
     * Условие: Есть данные о продажах по месяцам:
     * data class Sale(val product: String, val amount: Int, val month: String)
     * val sales = listOf(
     *     Sale("Laptop", 1000, "Jan"), Sale("Phone", 500, "Jan"), Sale("Laptop", 1200, "Feb"),
     *     Sale("Tablet", 300, "Feb"), Sale("Phone", 450, "Mar"), Sale("Laptop", 1100, "Mar")
     * )
     * Нужно получить: Сумму продаж по каждому продукту, но только для месяцев, где общие продажи превысили 1000
     * То есть:
     * Сначала сгруппировать по месяцам и отфильтровать "успешные" месяцы
     * Потом сгруппировать по продуктам и просуммировать
     * Попробуй решить в одной цепочке! Это потребует нетривиального подхода.
     */
    fun someTasksWithList5() {
        data class Sale(val product: String, val amount: Int, val month: String)

        val sales = listOf(
            Sale("Laptop", 1000, "Jan"),
            Sale("Phone", 500, "Jan"),
            Sale("Laptop", 1200, "Feb"),
            Sale("Tablet", 300, "Feb"),
            Sale("Phone", 450, "Mar"),
            Sale("Laptop", 1100, "Mar")
        )
        println(sales
            .groupBy { it.month }
            .filter { it.value.sumOf { value -> value.amount } > 1000 }
            .flatMap { it.value }
            .groupBy { it.product }
            .mapValues { it.value.sumOf { value -> value.amount } })
    }
}

/**
 * Условие: Создай extension-функцию List<Int>.product() для вычисления произведения всех элементов в списке. Функция должна возвращать Int. Если список пустой, функция должна возвращать 1 (нейтральный элемент для умножения).
 * Подсказка: Используй функцию fold.
 * Пример:
 * listOf(2, 3, 4).product() должно вернуть 24
 * listOf<Int>().product() должно вернуть 1
 */
fun List<Int>.product() = this.fold(1) { total, next -> total * next }