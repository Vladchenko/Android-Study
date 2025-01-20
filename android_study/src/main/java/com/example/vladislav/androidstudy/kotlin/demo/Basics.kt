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
 * Kotlin study basics
 * Study Kotlin from https://kotlinlang.org/docs/reference/ & https://developer.android.com/kotlin/learn
 * Some Collections ops - http://developer.alexanderklimov.ru/android/kotlin/collection.php
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

    internal val internalModifier = 0  // Internal is a new modifier available in Kotlin that's not there in Java.
    // Setting a declaration as internal means that it'll be available in the same module only. By module in Kotlin,
    // we mean a group of files that are compiled together.

    fun noReturnType() {}   // This function has no return type

    fun noReturnTypeAlso(): Unit {}  // Unit stands for no type, like void in Java. Could be and should be omitted.

    fun theAnswer(): Int {
        return 42
    }

    fun theAnswer2(): Int = 42      // That is equivalent to a previous fun, but shorter

    fun theAnswer3() = 42       // That is equivalent to a previous fun, but even shorter

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

    /** https://habr.com/ru/articles/721084/ - find "Какой тип находится на вершине иерархии типов в Kotlin?" */
    private fun anyDemo() {
        var anyValue: Any? = null
        val list: List<Int?> = listOf()
        anyValue = listOf(5, null)
    }

    private val isEmpty: Boolean get() = "Some string".isEmpty()  // Extension property with custom getter

    private fun isEmpty2(string: String) = string.isEmpty()     // Alternative to isEmpty()

    private fun String.isEmpty3() = this.isEmpty()      // One more alternative to isEmpty()

    private fun isEven2(i: Int) = i % 2 == 0     // Type can be omitted
    // private fun Int.isEven() = this % 2 == 0     // Type can be omitted (Extension function)

    private var setterWithAnnotation: Any? = null
        @Inject set // Annotate the setter with Inject (also with a default implementation)

    private var counter = 0 // Note: the initializer assigns the backing field directly
        set(value) {
            if (value >= 0) field = value   // Is this "field" is kind of container for counter's value ?
        }

    fun equalityDemo() {
        // https://play.kotlinlang.org/byExample/02_control_flow/04_Equality%20Checks
        val authors = setOf("Shakespeare", "Hemingway", "Twain")
        val writers = setOf("Twain", "Shakespeare", "Hemingway")
        println(authors == writers)   // true
        println(authors === writers)  // false
    }

    fun dataClassCopyDemo() {
        data class Test(val value1: Int, val value2: String)

        val instance1 = Test(1, "1")
        val instance2 = instance1.copy()
        println(instance1 == instance2)     // true
        println(instance1 === instance2)    // false
    }

    fun typesDemo() {
        val valueByte: Byte = 10
        println(valueByte.javaClass)    // byte (notice, a primitive)

        val valueLong = 10L
        println(valueLong.javaClass)    // long (notice, a primitive)

        val pi = 3.14 // Double
        // val one: Double = 1  // Error: type mismatch
        val oneFloat = 1.0f     // float    1.0F or 1e10
        println(oneFloat.javaClass)     // float (notice, a primitive)

        val valueSmallerInt = 1
        println(valueSmallerInt.javaClass)    // int (notice, a primitive)
        println(valueSmallerInt)    // 1

        val valueBiggerInt = 1000000000000000
        println(valueBiggerInt.javaClass)    // long (notice, a primitive)
        println(valueBiggerInt)    // 1000000000000000

//        val valueBiggerInt2 = 10000000000000000000000000000       // The value is out of range

        val value1: Short = 10
        val value2: Short = 20
        println((value1 + value2).javaClass)    // int (notice, a primitive)
        val value3 = value1 + value2
        println(value3.javaClass)    // int (notice, a primitive)

        val value4: UByte = 10u
        val value5: UByte = 20u
        println((value4 + value5).javaClass)    // class kotlin.UInt
        val value6 = value4 + value5
        println(value6.javaClass)    // class kotlin.UInt

        val value7: Int = Integer.MAX_VALUE
        val value8: Int = Integer.MAX_VALUE
        println((value7 + value8).javaClass)    // int (notice, a primitive)
        val value9 = value7 + value8
        println(value9.javaClass)    // int (notice, a primitive)
        println(value9)     // -2   Doesn't raise type to Long

        val value10: Int = Integer.MAX_VALUE
        val value11: Int = Integer.MAX_VALUE
        println((value10 + value11.toLong()).javaClass)    // long (notice, a primitive)
        val value12 = value10 + value11.toLong()
        println(value12.javaClass)    // long (notice, a primitive)
        println(value12)     // 4294967294

        val i = 1
        val d = 1.0
        val f = 1.0f
        fun printDouble(d: Double) {
            print(d)
        }
        printDouble(d)
//    printDouble(i) // Error: Type mismatch
//    printDouble(f) // Error: Type mismatch
    }

    fun listDemo() {
        val someList = arrayOf(1, 2, 3, 4).asList()
        val list = listOf("q", "a", "z", "zz")    // Immutable list (one cannot add items to it or remove from)
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

        val list3 = mutableListOf(1, 2, 3, 4, 5)
        list3.reverse() // Reverses a mutable list itself (no need to write list3 = list3.reverse())
        println("list3.reverse() = $list3")  // [5,4,3,2,1]
        println(someList.reversed())    // Prints items in backwards order [4,3,2,1]

        val list4 = mutableListOf(1, 20, 3, 40, 5)
        list4.sort() // Sorts a mutable list itself (no need to write list4 = list4.sort())
        println("list3.sort() = $list4")  // [1,3,5,20,40]
        println(someList.sorted())    // Sorts items [1,2,3,4]
        println(listOf(4, 8, 2, 4, 9).sortedDescending())    // Sorts items [9,8,4,4,2]
        println("listOf(4,8,2,4,9).sortedBy { it > 4 } = ${listOf(4, 8, 2, 4, 9).sortedBy { it > 4 }}") // [4, 2, 4, 8, 9]
        println("listOf(4,8,2,4,9).sortedByDescending { it < 9 } = ${listOf(4, 8, 2, 4, 9).sortedByDescending { it < 9 }}") // [4, 8, 2, 4, 9]
        println(listOf(2, 9, 5, null, 1, 5, 0, 3, null).sortedWith(
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
                        { it.name }, { it.age }
                )
        ))  // [Cat(name=Василий, age=4, weight=5100), Cat(name=Мурзик, age=4, weight=5400), Cat(name=Мурзик, age=6, weight=5400), Cat(name=Рыжик, age=5, weight=6500)]

        //TODO Stopped @ Сортируем по длине имён в порядке возрастания, используя Comparator.
        // http://developer.alexanderklimov.ru/android/kotlin/collection.php

        val mutableList = mutableListOf("1", "2", "3", "1", "2", "3", "1", "2", "3")    // Mutable list (one can add items to it)
        mutableList.removeAll { it == "1" }
        println(mutableList)    // [2, 3, 2, 3, 2, 3]
        mutableList.remove("2")     // Removes 1st matching element
        println(mutableList)    // [3, 2, 3, 2, 3]
    }

    fun setDemo() {
        println("Kotlin" in setOf("Java", "Scala")) //false
        println(setOf(4, 7, 2, 9, 12, 10, 11).maxOrNull()) //12
        // {[4, 7, 2, 9, 12, 10, 11]=[4, 7, 2, 9, 12, 10, 11]}
        println(mapOf(setOf(4, 7, 2, 9, 12, 10, 11) to setOf(4, 7, 2, 9, 12, 10, 11)))
        // [(4, 40), (7, 70), (2, 20), (9, 90), (12, 120), (10, 100), (11, 110)]
        println(setOf(4, 7, 2, 9, 12, 10, 11).zip(setOf(40, 70, 20, 90, 120, 100, 110)))
        // {4=40, 7=70, 2=20, 9=90, 12=120, 10=100, 11=110}
        println(setOf(4, 7, 2, 9, 12, 10, 11).zip(setOf(40, 70, 20, 90, 120, 100, 110)).toMap())
        val set: Set<Int> = emptySet()    // Empty integer set
        val set2: Set<Int> = setOf(1, 2, 3, 4, 5, 6)    // Integer set
        val map = set2.partition { it % 2 == 0 }    // Splitting lists which conclude pair
        println(map)    // ([2, 4, 6], [1, 3, 5])
        println(setOfNotNull(null, 5, "s", null, "fgh"))    // [5, s, fgh]
        println(3 in set2)  // true     // Tells if 3 present in set2
        val hashSet = hashSetOf(1, 2, 3)
        hashSet.add(4)
        val sortedSet = sortedSetOf(5, 20, 3, 1, 9) // Creates treeSet that has sorted set in it
        sortedSet.add(0)
        println(sortedSet)  // [0, 1, 3, 5, 9, 20]
        hashSet.addAll(sortedSet)   // Merging two sets, same as hashSet.union(sortedSet)
        println(hashSet)  //[0, 1, 2, 3, 4, 20, 5, 9]
    }

    fun rangesDemo() {
        val range1 = 1..10
//        val range11 = 10..<20     // Present in Kotlin beginning with version 1.9
        val range2 = 'A'..'Z'
        val range3 = 1.1..10.1  // https://stackoverflow.com/questions/44315977/ranges-in-kotlin-using-data-type-double
        val range4 = listOf("Scala", "Java", "Kotlin", "Python", "Ruby")
        println(range1.joinToString(" "))   // Adds space after each item
        println(range1.joinToString("/"))
        println(range1.joinToString(";"))
        println(range2.joinToString(" "))
        println("range2::class = " + range2::class) // class kotlin.ranges.CharRange (Kotlin reflection is not available)
        println("D in range2 = ${'D' in range2}")   // true
        println("range3.contains(7.1) = " + range3.contains(7.1))   // true
        println("range3 = " + range3.toRange()) // [1.1, 10.1]
        println("Kotlin" in range4) // true
        arrayOf(range3.toRange()).iterator().forEach { println(it) }    // [1.1, 10.1]
        CharRange('1', '9').toList().forEach(System.out::println)    // Prints 1 to 9 each with new row
        println(CharRange('1', '9').toList())    // [1, 2, 3, 4, 5, 6, 7, 8, 9]
        println(('z' downTo 's' step 2).toList())   // [z, x, v, t]
    }

    fun javaRelatedDemo() {
        println("variable::class = " + knownType::class)    // float (Kotlin reflection is not available)
        println("variable::class.java = " + knownType::class.java)  // float
//        println("variable::javaClass = " + mVariable::javaClass)  // CompilationException: Back-end (JVM) Internal error: wrong bytecode generated
    }

    fun updateWeather(degrees: Int) = when (degrees) {
        in Int.MIN_VALUE..10 -> Pair("cold", Color.BLUE)
        in 25..30 -> Pair("mild", Color.YELLOW)
        else -> Pair("hot", Color.RED)
    }

    fun updateWeather2(degrees: Int) = when {
        degrees < 10 -> Pair("cold", Color.BLUE)
        degrees < 25 -> "mild" to Color.YELLOW  // Kotlin way of making Pair
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
            fun penDown() {
                /** Empty for demo */
            }

            fun penUp() {
                /** Empty for demo */
            }

            fun turn(degrees: Double) {
                /** Empty for demo */
            }

            fun forward(pixels: Double) {
                /** Empty for demo */
            }
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

    /**
     * https://kotlinlang.org/docs/kotlin-tour-functions.html#lambdas-exercise-2
     * This seems to be a higher order function
     */
    private fun repeatN(n: Int, action: () -> Unit) {
        repeat(n) {
            action.invoke()
        }
    }

    fun repeatNInvocation() {
        repeatN(5, { println("Hello") })
    }

    fun idiomsDemo(context: Context) {
        val list = listOf(0, 1, 2, 3, 4, 5)
        val positives = list.filter { x -> x > 0 }
        // same as
        val positives2 = list.filter { it > 0 }

        val files = File("Test").listFiles()
        println(files?.size ?: "empty") // If not null and else shorthand

        val value: String? = null

        value?.let {
            // execute this block if not null
            println(value)
        }

        value?.let {
            // execute this block if not null
        } ?: run {
            // execute this block if null
        }

        // defaultValue is returned, if the value or the transform result is null.
        val mapped = value?.let { transformValue(it) } ?: list

        // This is useful for configuring properties that aren't present in the object constructor.
        val myRectangle = Rectangle().apply {
            x = 40
            y = 50
            width = 100
            height = 70
        }

        // Java 7's try with resources analog
        createFilesDirIfAbsent(context) //Creating files dir first, else any file won't be created in android.
        val file = File(context.filesDir.path + "/file.txt")
        if (file.createNewFile()) {    // Creating file now
            println("File ${context.filesDir.path}/file.txt created")
        } else {
            println("File ${context.filesDir.path}/file.txt already exists")
        }
        val stream = Files.newInputStream(file.toPath())
        stream.buffered().reader().use { reader ->
            println(reader.readText())
        }
        if (file.delete()) {    // Deleting file now
            println("File ${context.filesDir.path}/file.txt has been deleted")
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
        println("a = $a, b2 = $b2")     // a = 2, b2 = 1

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

    fun varArgsDemo(vararg args: Any) {
        args.forEach { item -> println(item) }  // Strangely, print doesn't work here, only println
    }

    fun varArgsDemo2() {
        val list = listOf("1", "2", "3")
        varArgsDemo(list)   // [1, 2, 3]
        val array = arrayOf("1", "2", "3")
        // That's how array is transformed to varargs. That is a spread operator.
        varArgsDemo(*array)     // 1 2 3    each on a separate row
        varArgsDemo(array)      // [Ljava.lang.String;@36effb6
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

    private fun Int.answer() = if (this == 42) "yes" else "no"
    fun answerUsageExample() {
        println(5.answer())     // no
        println(42.answer())    // yes
    }

    fun instantiationDemo() {
//        val abstractModel: Any = AbstractModel()  // Error - Cannot create an instance of an abstract class
        var model: Any = SomeModel()                // Creating an instance of some class
        // Any - The root of the Kotlin class hierarchy. Every Kotlin class has Any as a superclass.
        model = SomeModel()                         // Assigning a new instance
        println("instantiationDemo has been invoked")
    }

    fun temp() {
        // isNotDigit('3')
        // println(Utils.charToInt('4'))
        // println(Utils.charToInt('0'))
        // println(Utils.charToInt('b'))   // IllegalArgumentException
        println("os.name = " + System.getProperty("os.name"))
        // Full list of properties - https://docs.oracle.com/javase/8/docs/api/java/lang/System.html
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
        println("Result is = " + "10 +- 2".solveExpression())    // Wrong operation
        println("Result is = " + "10 + 2 / 4 - 3 * 6 + 1".solveExpression())    // -6.5
        println("Result is = " + "10 + 2".solveExpression())    // 12.0
        println("Result is = " + "10 / 2".solveExpression())    // 5.0
        println("Result is = " + "10 / 2 * 3".solveExpression())    // 15.0
        println("Result is = " + "10 / 2 * 3.55678965 + 2".solveExpression())   // 19.783948249999998
        println("Result is = " + "10 / 2 * 3 + 2 / 6".solveExpression())    // 15.333333333333334
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

    /**
     * https://developer.alexanderklimov.ru/android/kotlin/jvmoverloads.php
     * @JvmOverloads    Tells compiler to create several implementations of this fun. They will be
     *                  needed in java code.
     */
    @JvmOverloads
    fun setPersonInfo(name: String = "Ivan", lastname: String = "Ivanov", age: Int = 25) {
        // Some ops
    }

    // 'operator' modifier is inapplicable on this function: receiver must be a supertype of the return type
    // operator fun Int.inc() = this+this

    companion object {
        const val CONST = ""  // The val keyword is also used for read-only properties.

        // But the main difference between the const and val is that the val properties
        // can be initialised at the runtime also.
        // IMHO - Not sure if this is true !
        internal const val CONST2 = "internal"    // internal - visibility modifier saying that this property
        // is visible only in its module

        private const val COUNT = 42       // This is a constant, type is automatically inferred as int
    }
}
