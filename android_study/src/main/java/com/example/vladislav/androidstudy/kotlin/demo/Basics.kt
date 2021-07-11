package com.example.vladislav.androidstudy.kotlin.demo

import android.content.Context
import androidx.constraintlayout.solver.widgets.Rectangle
import androidx.core.util.toClosedRange
import androidx.core.util.toRange
import com.example.kotlinstudy.KotlinKoans
import com.example.vladislav.androidstudy.kotlin.models.SomeModel
import com.example.vladislav.androidstudy.kotlin.utils.Utils
import com.example.vladislav.androidstudy.kotlin.utils.Utils.Companion.isPalindrome
import com.example.vladislav.androidstudy.kotlin.utils.Utils.Companion.numbersQuantity
import com.example.vladislav.androidstudy.kotlin.utils.Utils.Companion.wordsNumber
import com.example.vladislav.androidstudy.kotlin.utils.Utils.Companion.charNumberInString
import com.example.vladislav.androidstudy.kotlin.utils.Utils.Companion.longestWord
import com.example.vladislav.androidstudy.kotlin.utils.Utils.Companion.removeRepetitives
import com.example.vladislav.androidstudy.kotlin.utils.Utils.Companion.stringPosition
import com.example.vladislav.androidstudy.kotlin.utils.createFilesDirIfAbsent
import java.io.File
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Paths
import javax.inject.Inject

/**
 * Study Kotlin from https://kotlinlang.org/docs/reference/ & https://developer.android.com/kotlin/learn
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
    private val knownType: Float = 1.1f // Type may be omitted

    // !!! Kotlin is a statically-typed language. This means that the type is resolved at compile time and never changes.

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

    fun singleExpressionFunction2() =
        "Hello world"    // Single expression function (type can be omitted)

    fun args(
        name: String = "Vlad",
        lastName: String = "Yan"
    ) {
    }    // Default values for a parameters

    fun argsDemo() {
        args()  // Params will get default values
        args(name = "Ha")   // lastName param will get default value
        args(lastName = "Yanchenko")    // name param will get default value
        args(name = "Yan", lastName = "Vladchenko")
        // Params may also have a wrong order, since they are named
    }

    fun singleExpressionFunction3(word: String) =
        println("Hello $word")    // Single expression function

    fun stringLengthFunc2(row: String) = row.length

    private fun elvisOperatorDemo(name: String?) =
        name ?: "no name" // If name is not null, return it,
    // otherwise, make it to be "no name";  ?: - Elvis operator

    private val isEmpty: Boolean get() = "Some string".isEmpty()  // Extension property

    // private val isEmpty2(string: String): Boolean get() = "Some string".isEmpty()  // Why it is an error

    private fun isEven(i: Int): Boolean = i % 2 == 0

    private var setterWithAnnotation: Any? = null
        @Inject set // annotate the setter with Inject (also with a default implementation)

    private var counter = 0 // Note: the initializer assigns the backing field directly
        set(value) {
            if (value >= 0) field = value
        }

    internal val internalModifier = 0;
    // Internal is a new modifier available in Kotlin that's not there in Java. Setting a declaration as internal
    // means that it'll be available in the same module only. By module in Kotlin, we mean a group of files that
    // are compiled together.

    fun arraysDemo() {
        val array: Array<Int> = arrayOf(1, 2, 3)   // Type maybe omitted
        val array2 = arrayOf(1, 2.3, "3.4", 567, 789.01)
        val array3 =
            byteArrayOf(1, 2, 3, 4, 2, 3, 1, 2, 3, 4, 1).distinct()  // Only unique values - 1,2,3,4

        println(array2)
        println(array2[0])   // 1st item of array, also can be printed by println(array.get(0))
        println("array.size = ${array2.size}")   // Printing a size of an array
        println("array contains 345 = ${array2.contains(345)}")
        println("index of 567 = ${array2.indexOf(567)}")
        array3.forEach { // Check a LoopsDemo.kt to see a several ways to traverse through collections
            // Printing an array
            println(it)
        }
        array3.distinctBy { it }   // TODO Understand distinctBy and implement other methods
    }

    fun listDemo() {
        val someList = arrayOf(1, 2, 3, 4).asList()
        val list = listOf("q", "a", "z", "zz")    // Immutable list (one cannot add items to it)
        val mutableList = mutableListOf("w", "s", "x")    // Mutable list (one can add items to it)
        println(list[0])
        println(list.get(0))
        list.forEach { item -> println(item) }
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return // non-local return directly to the caller of foo()
            print(it)
        }
        list.forEach(System.out::print)
        println(list.map { it })
        println(list) //[1,2,3,4,5] invokes toString()
        list.first() // first element in list
        list.last() // last element in list
        list.last { it.contains("z") }   // FIXME Doesn't work
    }

    fun setDemo() {
        println("Kotlin" in setOf("Java", "Scala")) //false
        println(setOf(4, 7, 2, 9, 12, 10, 11).maxOrNull()) //12
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
            fun penDown() {}
            fun penUp() {}
            fun turn(degrees: Double) {}
            fun forward(pixels: Double) {}
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
            .map { it.toUpperCase() }
            .forEach { println(it) }
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

        val value = ""

        value?.let {
            // execute this block if not null
            println(value)
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
//        println("words number - " + "".removeRepetitives())
//        println("words number -  |" + " ".removeRepetitives())
        println("1".removeRepetitives())
        println("1й ".removeRepetitives())
        println(" eer 1 2й qq wertttt 4445666655".removeRepetitives())
        println("555565 66676777".removeRepetitives())
        println("6666 6".removeRepetitives())
        println("666656 77".removeRepetitives())
        println("6666266 77 ".removeRepetitives())
        println("666616661 77 455".removeRepetitives())
//        println("words number - 1 2 2 22|" + "1 2 wrethgfe 2 22|".removeRepetitives())

    }

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
