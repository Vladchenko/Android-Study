package com.example.kotlinstudy.demo

import androidx.constraintlayout.solver.widgets.Rectangle
import androidx.core.util.toRange
import com.example.kotlinstudy.Utils
import com.example.kotlinstudy.models.SomeModel
import java.io.File
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Study Kotlin from https://kotlinlang.org/docs/reference/ & https://developer.android.com/kotlin/learn
 *
 * Basic class
 */
class Basics {

    // var unknownType     // Property must be initialized or be abstract
    private val COUNT = 42       // This is a constant, type is automatically inferred as int
    internal val CONST = "intenal"       // internal - visibility modifier saying that this property
    // is visible only in its module

    // !!! Kotlin is a statically-typed language. This means that the type is resolved at
    // compile time and never changes.
    private var mVariable = 255 // This is a variable, type is automatically inferred as int
    private var mInt: Int = 0xFF
    private var mBigInt: Int = Int.MAX_VALUE     // Integer variable with max value
    private var mDoubleVar: Double = Double.MIN_VALUE     // Double variable with min value
    private var mLongValue = 1L             // Long value
    private var mLongValue2 = 3000000000    // Long value

    //    private val mString1: String  // Property must be initialized or be abstract
//    private val string1: String = null   // Null can not be a value of a non-null type string
    private val languageName: String = "Kotlin"
    private val STRING2: String? = null
    private val STRING3: String? = String.toString()
    private var mLongString = """This is a 
        long string that may contain several rows
    """
    private val CHARNUMBER = "Васька".count { letter -> letter == 'а' } // Counts letters "a" in a

    // string "Васька", using anonymous function
    // Anonymous function. Returns a length of a passed string
    private val STRINGLENGTHFUNC: (String) -> Int = { input ->
        input.length
    }

    private var mChar: Char = '░'

    private val MAP = mapOf("a" to 1, "b" to 2, "c" to 3)   // readonly map

    private val validNumbers = arrayOf(1, 2, 3)

    val borderColor: String get() = "black" // What's this get() ?
    val isEmpty: Boolean get() = STRING3!!.isEmpty()
    var stringRepresentation: String
        get() = this.toString()
        set(value) {    // Custom setter. "value" can be named differently
            STRINGLENGTHFUNC(value) // parses the string and assigns values to other properties
        }
    var setterVisibility: String = "abc"
        private set // the setter is private and has the default implementation

    var setterWithAnnotation: Any? = null
//        @Inject set // annotate the setter with Inject (also with a default implementation)

    var counter = 0 // Note: the initializer assigns the backing field directly
        set(value) {
            if (value >= 0) field = value
        }

    fun noReturnType() {    // This function has no return type
    }

    fun noReturnTypeAlso(): Unit {  // Unit stands for no type, like void in Java
    }

    fun theAnswer(): Int {
        return 42
    } // That is equivalent to a next fun

    fun theAnswer2() = 42    // Single expression function

    fun args(name: String = "Vlad", lastName: String = "Yan") {}

    fun argsDemo() {
        args()  // Params will get default values
        args(name = "Ha")   // lastName param will get default value
        args(lastName = "Yanchenko")    // name param will get default value
        args(name = "Yan", lastName = "Vladchenko")
        args(
            lastName = "Yanchenko",
            name = "Lily"
        ) // Params may have a wrong order, since they are named
    }

    fun singleExpressionFunction(): String = "Hello world"    // Single expression function

    fun singleExpressionFunction2() = "Hello world"    // Single expression function

    fun singleExpressionFunction3(word: String) =
        println("Hello $word")    // Single expression function

    fun stringLengthFunc2(row: String) = row.length

    fun elvisOperatorDemo(name:String?) = name ?: "no name" // If name is not null, return it,
    // otherwise, make it to be "no name"

    fun numbersDemo() {
        println(mBigInt)
        println("int = " + mInt)
        println("int.toString() = " + mInt.toString())
        println("bigInt = $mBigInt")
        if (mVariable is Int) {
            println("variable is of Int type")
        } else {
            println("variable is NOT of Int type")
        }
        println("doubleVar is Double = ${mDoubleVar is Double}")
//         println("bigInt is Char = ${mBigInt is Char}")
        println("3.14 to Int = " + (3.14.toInt()))
        println("65 to Char = " + (65.toChar()))
        println("char to Double = " + (mChar.toDouble()))
//        mInt = mLongValue   // Error - Type mismatch. Because Kotlin has no implicit conversion.
        mInt = mLongValue.toInt();  // This way will do
    }

    fun stringsDemo() {
        println("Hello world")
        println(STRING3)
        println(mLongString)
        println("1 + 2 = ${1 + 2}")
        println("\"asrgthyuujmhngb\".length = ${"asrgthyuujmhngb".length}")
        println("\"asrgthyuujmhngb\".count() = ${"asrgthyuujmhngb".count()}")
        println("Equals str1 with str2 = ${"str1".equals("str2")}")
        println("Compare row and row = ${"row".compareTo("row")}")
        println("Compare row and row2 = ${"row".compareTo("row2")}")
        println("Compare row2 and row = ${"row2".compareTo("row")}")
        println("\"some string\".get(2) = ${"some string".get(2)}")
        println("\"some string\"[2] = ${"some string"[2]}")
        println("\"some string\".subSequence(2, 8) = ${"some string".subSequence(2, 8)}")
//        val p: String by lazy {
//            // compute the string
//        }
        val p = "Some String".also(::println)   // Assigns and prints the string
    }

    fun arraysDemo() {
        var arrayInt = arrayOf(1, 2, 3, 4, 5)
        val array = arrayOf(1, 2.3, "3.4", 567, 789.01)
        val array2 = Array(5, { x -> x * x })
        val array3: Array<Int> = arrayOf(1, 2, 3)

        println(array)
        println(array[0])
        println(array.get(1))
        println("array.size = ${array.size}")
        println("array contains 345 = ${array.contains(345)}")
        println("index of 567 = ${array.indexOf(567)}")
        for (i in 0..array2.size - 1) {
            println(array2[i])
        }
        for (i in array2.indices) {
            println("item at $i is ${array2[i]}")
        }
        for ((index, value) in array.withIndex()) {
            println("the element at $index is $value")
        }
        println(array2.joinToString(" "))   // Another way of printing array
        array.forEach {                         // Another way of printing array
                item ->
            println(item)
        }
        array.forEachIndexed { i, item -> println("Item $item is at index $i") }  // Another way of printing array
        array.forEach {
            // Another way of printing array
            println(it)
        }
        array.forEach(System.out::println)       // Another way of printing array
        println(array.map { it })
    }

    fun loopsDemo() {
        // Take a look to an arraysDemo() as well
        var x = 10
//        for ((index, value) in array.withIndex()) {
//            println("the element at $index is $value")
//        }
        for (x in 1..10 step 2) {
            print(x)
        }
        println()
        for (x in 9 downTo 0 step 3) {  // This one doesn't work
            print(x)
        }
//        for (item in collection) print(item)
//        for (item: Int in ints) {
//            // ...
//        }

        while (x > 0) {
            x--
        }

//        do {
//            val y = retrieveData()
//        } while (y != null) // y is visible here!
    }

    fun listDemo() {
        val list = listOf("q", "a", "z")    // Immutable list (one cannot add items to it)
        val mutableList = mutableListOf("w", "s", "x")    // Mutable list (one can add items to it)
        println(list[0])
        println(list.get(0))
        fun printList() {
            list.forEach { item -> println(item) }
        }
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return // non-local return directly to the caller of foo()
            print(it)
        }
        list.forEach(System.out::print)
        println(list.map { it })
    }

    fun mapDemo() {
        println(MAP["key"])
        MAP.forEach { key, value -> println("$key -> $value") }
        val mutableMap =
            mutableMapOf(1 to "1", 2 to "2", 3 to "3") // Map that one can add values to
        mutableMap.put(4, "4")
    }

    fun rangesDemo() {
        val range1 = 1..10
        val range2 = 'A'..'Z'
        val range3 = 1.1..10.1
        println(range1.joinToString(" "))
        println(range2.joinToString(" "))
        println("range2::class = " + range2::class)
        println("D in range2 + ${'D' in range2}")
        println("range3.contains(7.1) = " + range3.contains(7.1))
        println("range3 = " + range3.toRange()) // Won't iterate through this range, one needs
        // to make own iterator
    }

    fun otherDemo() {
        mLongString?.toUpperCase()  // Checking for a null before casting to uppercase
        println("variable::class = " + mVariable::class)
        println("variable::class.java = " + mVariable::class.java)
//        println("variable::javaClass = " + mVariable::javaClass)  // CompilationException: Back-end (JVM) Internal error: wrong bytecode generated
    }

    fun whenDemo() {
        val answerString: String = when {
            COUNT == 42 -> "I have the answer."
            COUNT > 35 -> "The answer is close."
            else -> "The answer eludes me."
        }
        println(answerString)
    }

    fun whenDemo2(color: String): Int {
        return when (color) {
            "Red" -> 0
            "Green" -> 1
            "Blue" -> 2
            else -> throw IllegalArgumentException("Invalid color param value")
        }
    }

    fun whenDemo3(color: String) = when (color) {
        "Red" -> 0
        "Green" -> 1
        "Blue" -> 2
        else -> throw IllegalArgumentException("Invalid color param value")
    }

    fun whenDemo4(x: Int) =
        when (x) {
            -1, 0 -> print("0 or 1")
            getInt(x) -> print("s encodes x")
            in 1..10 -> print("x is in the range")
            in validNumbers -> print("x is valid")
            !in 10..20 -> print("x is outside the range")
            else -> print("none of the above")
        }

    // Another demo of "when"
    fun hasPrefix(x: Any) =
        when (x) {
            is String -> x.startsWith("prefix")
            else -> false
        }

    fun getInt(x: Int): Int = x * x

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

    fun ifDemo(param: Int) {
        val result = if (param == 1) {
            "one"
        } else if (param == 2) {
            "two"
        } else {
            "three"
        }
    }

    fun ifDemo2(param: Int) = if (param == 1) {
        "one"
    } else if (param == 2) {
        "two"
    } else {
        "three"
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
        val fruits = listOf("banana", "avocado", "apple", "kiwifruit")
        fruits
            .filter { it.startsWith("a") }
            .sortedBy { it }
            .map { it.toUpperCase() }
            .forEach { println(it) }
    }

    fun idiomsDemo() {
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
        val stream = Files.newInputStream(Paths.get("/some/file.txt"))
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

    fun extensionFunctionDemo() {
        fun String.spaceToCamelCase() {
            this.length
        }
        println("Convert this to camelcase".spaceToCamelCase())
    }

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

    fun generateAnswerString5() = if (mVariable == 42) "yes" else "no"

    fun instantiationDemo() {
//        val abstractModel: Any = AbstractModel()  // Error - Cannot create an instance of an abstract class
        var model: Any = SomeModel()                // Creating an instance of some class
        // Any - The root of the Kotlin class hierarchy. Every Kotlin class has Any as a superclass.
        model = SomeModel()                         // Assigning a new instance
        println("instantiationDemo has been invoked")
    }

    fun temp() {
        STRINGLENGTHFUNC("qwertyu")
        println(CHARNUMBER) // prints 2
        println(Utils.charToInt('4'))
        println(Utils.charToInt('0'))
//        println(Utils.charToInt('b'))   // IllegalArgumentException
        println("os.name = " + System.getProperty("os.name"))
    }

    fun sayHello(map: Map<String, String>) =
        map.forEach { greeting, subject -> println("$greeting, $subject") }

    fun sayHello(greeting: String, list: List<String>) =
        list.forEach { item -> println("$greeting, $item") }

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

    companion object {
        const val CONST = ""  // The val keyword is also used for read-only properties.
        // But the main difference between the const and val is that the val properties
        // can be initialised at the runtime also.
        // IMHO - Not sure if this is true !

    }
}
