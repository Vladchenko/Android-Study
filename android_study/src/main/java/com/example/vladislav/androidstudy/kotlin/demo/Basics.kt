package com.example.vladislav.androidstudy.kotlin.demo

import android.content.Context
import androidx.core.util.toRange
import com.example.vladislav.androidstudy.kotlin.models.SomeModel
import com.example.vladislav.androidstudy.kotlin.utils.createFilesDirIfAbsent
import solveExpression
import java.io.File
import java.math.BigDecimal
import java.nio.file.Files
import javax.inject.Inject

/**
 * Study Kotlin from https://kotlinlang.org/docs/reference/ & https://developer.android.com/kotlin/learn
 * Some Collections ops - http://developer.alexanderklimov.ru/android/kotlin/collection.php
 *
 * Basic class
 */
class Basics {

    // val unknownType          // Property must be initialized or be abstract
    // val knownType: Float     // Property must be initialized or be abstract
    /**
     * By default, top-level properties, just like any other properties, are exposed to Java code as accessor methods
     * (a getter for a val property and a getter/setter pair for a var property). If you want to expose a constant to
     * Java code as a public static final field, to make its usage more natural, you can mark it with the const modifier
     * (this is allowed for properties of primitive types, as well as String)
     */
    private val knownType: Float = 1.1f // Type may be omitted, since automatically defined from a value itself.

    // !!! Kotlin is a statically-typed language. This means that the type is resolved at compile time and never changes.

    internal val internalModifier = 0;  // Internal is a new modifier available in Kotlin that's not there in Java.
    // Setting a declaration as internal means that it'll be available in the same module only. By module in Kotlin,
    // we mean a group of files that are compiled together.

    fun noReturnType() {    // This function has no return type
    }

    fun noReturnTypeAlso(): Unit {  // Unit stands for no type, like void in Java. Could be and should be omitted.
    }

    fun theAnswer(): Int {
        return 42
    }

    fun theAnswer2(): Int = 42      // That is equivalent to a previous fun

    fun theAnswer3() = 42       // That is equivalent to a previous fun

    fun singleExpressionFunction(): String = "Hello world"    // Single expression function

    fun singleExpressionFunction2() = "Hello world"    // Single expression function (type can be omitted)

    fun args(name: String = "Vlad", lastName: String = "Yan") {}    // Default values for a parameters

    fun argsDemo() {
        args()  // Params will get default values
        args(name = "Ha")   // "lastName" param will get default value
        args(lastName = "Yanchenko")    // "name" param will get default value
        args(name = "Yan", lastName = "Vladchenko")
        // Params may also have a wrong order, since they are named
    }

    fun singleExpressionFunction3(word: String) = println("Hello $word")    // Single expression function

    fun stringLength(row: String) = row.length  // Same to String.stringLength() = this.length

    private fun elvisOperatorDemo(name: String?) = name ?: "no name" // Return "name", if it is not null,
    // else return "no name"

    private val isEmpty: Boolean get() = "Some string".isEmpty()  // Extension property

    private fun isEmpty2(string: String) = string.isEmpty()     // Alternative to isEmpty()

    private fun String.isEmpty3() = this.isEmpty()      // One more alternative to isEmpty()

    private fun isEven(i: Int): Boolean = i % 2 == 0    // Almost same as - fun Int.isEven(): Boolean = this % 2 == 0

    private fun isEven2(i: Int) = i % 2 == 0     // Type can be omitted

    private var setterWithAnnotation: Any? = null
        @Inject set // Annotate the setter with Inject (also with a default implementation)

    private var counter = 0 // Note: the initializer assigns the backing field directly
        set(value) {
            if (value >= 0) field = value   // Is this "field" is kind of container for counter's value ?
        }

    fun arraysDemo() {
        val array: Array<Int> = arrayOf(1, 2, 3)   // Type maybe omitted
        val array2 = arrayOf(1, 2.3, "3.4", 567, 789.01)
        val array3 = byteArrayOf(1, 2, 3, 4, 2, 3, 1, 2, 3, 4, 1).distinct()  // Only unique values - 1,2,3,4

        println(array2)
        println(array2[0])   // 1st item of array, also can be printed by println(array.get(0))
        println("array.size = ${array2.size}")   // Printing a size of an array
        println("array contains 345 = ${array2.contains(345)}")
        println("index of 567 = ${array2.indexOf(567)}")
        array3.forEach { // Check a LoopsDemo.kt to see a several ways to traverse through collections
            println(it)     // Printing an array
        }
        for (item in array3) print(item)
        println()
        println("distinctBy: " + array2.distinctBy {
            it.toString().toIntOrNull()    // Prints [1, 2.3, 567], 2.3 is present because it yields null and null is unique value among others
        })   // TODO Understand distinctBy and implement other methods
        println(array2.getOrNull(40) ?: "Неизвестный индекс")
        println(
            array3.toList() // Turns array into list
                .distinct() // Takes only unique items
                .map { it * it }    // Multiplies item onto itself
                .filter { it > 3 }  // Takes items greater than 3
        )   // [4, 9, 16]
        array3.toByteArray()
        array3.toHashSet()
        array3.toMutableList()
        array3.toMutableSet()
        array3.toSet()
        array3.toSortedSet()
    }

    fun listDemo() {
        val someList = arrayOf(1, 2, 3, 4).asList()
        val list = listOf("q", "a", "z", "zz")    // Immutable list (one cannot add items to it or remove from)
        val mutableList = mutableListOf("w", "s", "x")    // Mutable list (one can add items to it)
        println(list[0])    // Same to println(list.get(0))
        list.getOrElse(5) { list[0] }   // Answer: q    // Retrieves first element instead of 5th, which is absent.
        list.getOrNull(5)   // Retrieves null, instead of IOOB exception
        list.forEach { item -> println(item) }  // Printing list
        list.forEach(System.out::print)     // Printing list
        for (item in list) {    // Printing list
            print(item)
        }
        println(list.map { it })    // Printing list
        println(list) //[1,2,3,4,5] invokes toString()  // Printing list
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return@forEach
            print(it)
        }
        list.first() // First element in list
        list.last() // Last element in list
        println(list.last { it.contains("z") })   // Answer: zz
        println(listOf(1, 2, 3, 4, 5).fold(0) { total, next -> total + next })  // Answer: 15
        println(listOf(1, 2, 3).reduce { total, next -> total + next }) // Answer: 6. Same as .fold(), but without
        // an initial state
        println(listOf(1, 2, 3).sumOf { it + 5 }) // Answer: 21. Increasing all the items in 5, then sum them up
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
        println(listOf(1, 2, 3, 4, 5).elementAt(3)) // 4    // Также доступны elementAtOrElse, elementAtOrNull.
        listOf(1, 2, 3, 4, 5).find { it > 3 }   // 4 (find находит только 1-е вхождение. Для всех надо использовать filter)
        listOf(1, 2, 3, 4, 5).findLast { it > 3 }   // 5
        // Функция single() вернёт один уникальный элемент из списка. Если элементов, соответствующих условию, будет
        // несколько, то вернёт null.
        println(listOf(1, 6, 3, 4, 5).singleOrNull { it % 3 == 0 }) // null

        val list3 = mutableListOf(1,2,3,4,5)
        list3.reverse() // Reverses a mutable list itself (no need to write list3 = list3.reverse())
        println("list3.reverse() = $list3")  // [5,4,3,2,1]
        println(someList.reversed())    // Prints items in backwards order [4,3,2,1]

        val list4 = mutableListOf(1,20,3,40,5)
        list4.sort() // Sorts a mutable list itself (no need to write list4 = list4.reverse())
        println("list3.sort() = $list4")  // [1,3,5,20,40]
        println(someList.sorted())    // Sorts items [1,2,3,4]
        println(listOf(4,8,2,4,9).sortedDescending())    // Sorts items [9,8,4,4,2]
        println("listOf(4,8,2,4,9).sortedBy { it > 4 } = ${listOf(4,8,2,4,9).sortedBy { it > 4 }}") // [4, 2, 4, 8, 9]
        println("listOf(4,8,2,4,9).sortedByDescending { it < 9 } = ${listOf(4,8,2,4,9).sortedByDescending { it < 9 }}") // [4, 8, 2, 4, 9]
        println(listOf(2,9,5,null,1,5,0,3,null).sortedWith(
            // nullsLast(compareBy { it })  // Will have nulls coming last in a list
            nullsFirst(compareBy { it })
        ))  // [null, null, 0, 1, 2, 3, 5, 5, 9]

        data class Cat(val name: String, val age: Int, val weight: Int)
        val cats7 = mutableListOf<Cat>()
        cats7.add(Cat("Мурзик", 4, 5400))
        cats7.add(Cat("Рыжик", 5, 6500))
        cats7.add(Cat("Василий", 4, 5100))
        cats7.add(Cat("Мурзик", 6, 5400))
        println(cats7.sortedWith(
            compareBy(
                {it.name}, {it.age}
            )
        ))

        //TODO Stopped @ Сортируем по длине имён в порядке возрастания, используя Comparator.
        // http://developer.alexanderklimov.ru/android/kotlin/collection.php
    }

    fun setDemo() {
        println("Kotlin" in setOf("Java", "Scala")) //false
        println(setOf(4, 7, 2, 9, 12, 10, 11).maxOrNull()) //12
        println(mapOf(setOf(4, 7, 2, 9, 12, 10, 11) to setOf(4, 7, 2, 9, 12, 10, 11)))
        // {[4, 7, 2, 9, 12, 10, 11]=[4, 7, 2, 9, 12, 10, 11]}
        val set: Set<Int> = emptySet()    // Empty integer set
        val set2: Set<Int> = setOf(1, 2, 3, 4, 5, 6)    // Empty integer set
        val map = set2.partition { it % 2 == 0 }    // Splitting lists which conclude pair
        println(map)
        val mutableSet: MutableSet<Int> = mutableSetOf()          // Empty integer mutable set
        val mutableSet2: MutableSet<Int> = mutableSetOf(1, 2, 3, 4)   // Integer mutable set with default values
        mutableSet2.add(5)
        val hashSet = hashSetOf(1, 2, 3)
        hashSet.add(4)
        val sortedSet = sortedSetOf(5, 20, 3, 1, 9) // Creates treeSet that has sorted set in it
        sortedSet.add(0)
        println(sortedSet)  // 1,3,5,9,20
        hashSet.addAll(sortedSet)   // Merging two sets, same as hashSet.union(sortedSet)

        println(setOfNotNull(null, 5, "s", null, "fgh"))    // [5, s, fgh]
    }

    fun rangesDemo() {
        val range1 = 1..10
        val range2 = 'A'..'Z'
        val range3 = 1.1..10.1
        val range4 = listOf("Scala", "Java", "Kotlin", "Python", "Ruby")
        println(range1.joinToString(" "))   // Adds space after each item
        println(range1.joinToString("/"))
        println(range1.joinToString(";"))
        println(range2.joinToString(" "))
        println("range2::class = " + range2::class)
        println("D in range2 = ${'D' in range2}")   // class kotlin.ranges.CharRange (Kotlin reflection is not available)
        println("range3.contains(7.1) = " + range3.contains(7.1))   // true
        println("range3 = " + range3.toRange()) // [1.1, 10.1]
        println("Kotlin" in range4) // true
        arrayOf(range3.toRange()).iterator().forEach { println(it) }    // [1.1, 10.1]
    }

    fun javaRelatedDemo() {
        println("variable::class = " + knownType::class)
        println("variable::class.java = " + knownType::class.java)
//        println("variable::javaClass = " + mVariable::javaClass)  // CompilationException: Back-end (JVM) Internal error: wrong bytecode generated
    }

    fun updateWeather(degrees: Int) = when (degrees) {
        in Int.MIN_VALUE..10 -> Pair("cold", Color.BLUE)
        in 25..30 -> Pair("mild", Color.YELLOW)
        else -> Pair("hot", Color.RED)
    }

    fun updateWeather2(degrees: Int) = when {
        degrees < 10 -> Pair("cold", Color.BLUE)
        degrees < 25 -> "mild" to Color.YELLOW  // another way of making Pair
        else -> Pair("hot", Color.RED)
    }

    fun tryCatchDemo() {
        fun test() {
            val result = try {
//                count()
            } catch (e: ArithmeticException) {
                throw IllegalStateException(e)
            }

            // Working with result
        }
    }

    fun withDemo() {    // Calling multiple methods on an object instance (with)
        class Turtle {
            fun penDown() { /** Empty for demo */ }
            fun penUp() { /** Empty for demo */ }
            fun turn(degrees: Double) { /** Empty for demo */ }
            fun forward(pixels: Double) { /** Empty for demo */ }
        }

        val myTurtle = Turtle()
        with(myTurtle) {
            //draw a 100 pix square
            penDown()
            for (i in 1..4) {
                forward(100.0)
                turn(90.0)
            }
            penUp()
        }
    }

    fun lambdaDemo() {
        val predicate = ::elvisOperatorDemo
        println(predicate)  // "function elvisOperatorDemo"
        // println(::predicate)  // "Error - References to a variables are not supported yet"
        val array2 = Array(5, { x -> x * x })
        val array3 = Array(5) { x -> x * x }
        val fruits = listOf("banana", "avocado", "apple", "kiwifruit")
        val ordinalsList = listOf(1, 2, 3, 4)
        fruits
            .filter { it.startsWith("a") }
            .sortedBy { it }
            .map { it.uppercase() }
            .forEach { println(it) }    // APPLE    AVOCADO
        ordinalsList.any(::isEven)  // Passes through each items in ordinalsList and runs isEven on them.
        // Prints true
        ordinalsList.filter { isEven(it) }  // [2, 4]
        // same as
        ordinalsList.filter(::isEven)  // [2, 4]
    }

    fun idiomsDemo(context: Context) {
        val list = listOf(0, 1, 2, 3, 4, 5)
        val positives = list.filter { x -> x > 0 }
        // same as
        val positives2 = list.filter { it > 0 }

        val files = File("Test").listFiles()
        println(files?.size ?: "empty") // If not null and else shorthand

        var value: String? = null

        value?.let {
            // execute this block if not null
            println(value)
        }

        value?.let {
            // execute this block if not null
        } ?: run {
            // execute this block if null
        }

        val mapped = value?.let { transformValue(it) } ?: list      // defaultValue is returned
        // if the value or the transform result is null.

        // This is useful for configuring properties that aren't present in the object constructor.
        val myRectangle = Rectangle().apply {
            x = 40
            y = 50
            width = 100
            height = 70
        }

        // Java 7's try with resources analog
        createFilesDirIfAbsent(context) //Creating files dir first, else any file won't be created in android.
        val file = File(context.filesDir.path + "/some/file.txt")
        file.createNewFile()    // Creating file now
        val stream =
            Files.newInputStream(file.toPath())    // FIXME - IOException: No such file or directory
        stream.buffered().reader().use { reader ->
            println(reader.readText())
        }

        // Consuming a nullable Boolean
        val b: Boolean? = false
        if (b == true) {
            println(b)
        } else {
            // `b` is false or null
        }

        // Swapping two variables
        var a = 1
        var b2 = 2
        a = b2.also { b2 = a }

        fun calcTaxes(): BigDecimal = TODO("Waiting for feedback from accounting")

        // Bad
        fun foo(a: String) { /*...*/
        }

        fun foo() = foo("a")

        // Good
        fun foo2(a: String = "a") { /*...*/
        }

        val n = 10
        for (i in 0..n - 1) { /*...*/
        }  // bad
        for (i in 0 until n) { /*...*/
        }  // good
    }

    fun varArgsDemo(vararg args: String) {
        args.forEach { item -> println(item) }  // Strangely, print method doesn't work here
    }

    fun varArgsDemo2(vararg args: Any) {
        args.forEach { item -> println(item) }  // Strangely, print method doesn't work here
    }

    fun varArgsDemo3() {
        val list = listOf("1", "2", "3")
//        varArgsDemo(list)   // Required String, found List<String>
        varArgsDemo2(list)
        val array = arrayOf("1", "2", "3")
        varArgsDemo(*array)     // That's how array is transformed to varargs. That is a spread operator.
        varArgsDemo2(array)
    }

    private fun transformValue(it: Any) {}

    fun functionAsParameter() {
        listOf(1, 2, 3, 4, 5).forEach(fun(value: Int) {
            if (value == 3) return  // local return to the caller of the anonymous fun, i.e. the forEach loop
            print(value)
        })
        print(" done with anonymous function")
    }

    // Builder-style usage of methods that return Unit
    fun arrayOfMinusOnes(size: Int): IntArray {
        return IntArray(size).apply { fill(-1) }
    }

    fun generateAnswerString(): String {
        val answerString = if (COUNT == 42) {
            "I have the answer."
        } else {
            "The answer eludes me"
        }
        return answerString
    }

    fun generateAnswerString2(count: Int): String {
        val answerString = if (count == 42) {
            "I have the answer."
        } else {
            "The answer eludes me"
        }
        return answerString
    }

    fun generateAnswerString3(count: Int): String {
        return if (count == 42) {
            "I have the answer."
        } else {
            "The answer eludes me"
        }
    }

    fun generateAnswerString4(count: Int) = if (count == 42) "yes" else "no"

    fun instantiationDemo() {
//        val abstractModel: Any = AbstractModel()  // Error - Cannot create an instance of an abstract class
        var model: Any = SomeModel()                // Creating an instance of some class
        // Any - The root of the Kotlin class hierarchy. Every Kotlin class has Any as a superclass.
        model = SomeModel()                         // Assigning a new instance
        println("instantiationDemo has been invoked")
    }

    fun temp() {
//         isNotDigit('3')
//         println(Utils.charToInt('4'))
//         println(Utils.charToInt('0'))
// //        println(Utils.charToInt('b'))   // IllegalArgumentException
//         println("os.name = " + System.getProperty("os.name"))
    }

    // Higher-order functions
    fun stringMapper(str: String, mapper: (String) -> Int): Int {
        // Invoke function
        return mapper(str)
    }

    // Using stringMapper
    val length: Int = stringMapper("Android", { input ->
        input.length
    })

    // Using stringMapper
    val length2 = stringMapper("Android") { input ->
        input.length
    }

    fun someExample() {
        val x: Int? = 1
        val y: Int = 2
        val sum = x ?: 0 + y    // x ?: 0 has to be put into (), for result could be 3
        println(sum)    //1
    }

    fun safeCast() {
        val s = ""
        println(s as? Int)    // null
        println(s as Int?)    // exception
    }

    fun destructuringDeclaration() {
        // Also check
        // 1) https://play.kotlinlang.org/byExample/08_productivity_boosters/03_Destructuring%20Declarations
        // 2) https://www.baeldung.com/kotlin/destructuring-declarations
        val (number, name) = 1 to "one" // Destructuring declaration
        val collection = listOf(1, 2, 3)
        for ((index, element) in collection.withIndex())    // Destructuring declaration
        {
            println("$index: $element")
        }
        val map = mapOf(1 to "one", 2 to "two")
        for ((key, value) in map) {
            println("$key to $value")
        }
    }

    fun testIsPalindromeMethod() {
        println("Result is = " + "10 +- 2".solveExpression())    //6.5
        println("Result is = " + "10 + 2 / 4 - 3 * 6 + 1".solveExpression())    //6.5
        println("Result is = " + "10 + 2".solveExpression())
        println("Result is = " + "10 / 2".solveExpression())
        println("Result is = " + "10 / 2 * 3".solveExpression())
        println("Result is = " + "10 / 2 * 3.55678965 + 2".solveExpression())
        println("Result is = " + "10 / 2 * 3 + 2 / 6".solveExpression())
//        println("}sdv(c)wefef[c]s".checkIfBracesPaired())
//        println(")wefv[ccsd]dv".checkIfBracesPaired())
//        println("]dcerg[".checkIfBracesPaired())
//        println("a is a palindrome - " + "a".isPalindrome())
//        println("aa is a palindrome - " + "aa".isPalindrome())
//        println("ab is a palindrome - " + "ab".isPalindrome())
//        println("aba is a palindrome - " + "aba".isPalindrome())
//        println("abba is a palindrome - " + "abba".isPalindrome())
//        println("abbc is a palindrome - " + "abbc".isPalindrome())
    }

    // 'operator' modifier is inapplicable on this function: receiver must be a supertype of the return type
    // operator fun Int.inc() = this+this

    companion object {
        const val CONST = ""  // The val keyword is also used for read-only properties.

        // But the main difference between the const and val is that the val properties
        // can be initialised at the runtime also.
        // IMHO - Not sure if this is true !
        internal const val CONST2 =
            "internal"       // internal - visibility modifier saying that this property
        // is visible only in its module

        private const val COUNT =
            42       // This is a constant, type is automatically inferred as int
    }
}
