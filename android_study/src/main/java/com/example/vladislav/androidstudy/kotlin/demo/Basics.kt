package com.example.vladislav.androidstudy.kotlin.demo

import android.content.Context
import androidx.core.util.toRange
import com.example.vladislav.androidstudy.kotlin.models.SomeModel
import com.example.vladislav.androidstudy.kotlin.utils.createFilesDirIfAbsent
import com.example.vladislav.androidstudy.kotlin.utils.solveExpression
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

    // Builder-style usage of methods that return Unit
    fun arrayOfMinusOnes(size: Int): IntArray {
        return IntArray(size).apply { fill(-1) }
    }

    fun instantiationDemo() {
//        val abstractModel: Any = AbstractModel()  // Error - Cannot create an instance of an abstract class
        var model: Any = SomeModel()                // Creating an instance of some class
        // Any - The root of the Kotlin class hierarchy. Every Kotlin class has Any as a superclass.
        model = SomeModel()                         // Assigning a new instance
        println("instantiationDemo has been invoked")
    }

    fun temp() {
        // com.example.vladislav.androidstudy.kotlin.utils.isNotDigit('3')
        // println(Utils.com.example.vladislav.androidstudy.kotlin.utils.charToInt('4'))
        // println(Utils.com.example.vladislav.androidstudy.kotlin.utils.charToInt('0'))
        // println(Utils.com.example.vladislav.androidstudy.kotlin.utils.charToInt('b'))   // IllegalArgumentException
        println("os.name = " + System.getProperty("os.name"))
        // Full list of properties - https://docs.oracle.com/javase/8/docs/api/java/lang/System.html
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
//        println("}sdv(c)wefef[c]s".com.example.vladislav.androidstudy.kotlin.utils.checkIfBracesPaired())
//        println(")wefv[ccsd]dv".com.example.vladislav.androidstudy.kotlin.utils.checkIfBracesPaired())
//        println("]dcerg[".com.example.vladislav.androidstudy.kotlin.utils.checkIfBracesPaired())
//        println("a is a palindrome - " + "a".com.example.vladislav.androidstudy.kotlin.utils.isPalindrome())
//        println("aa is a palindrome - " + "aa".com.example.vladislav.androidstudy.kotlin.utils.isPalindrome())
//        println("ab is a palindrome - " + "ab".com.example.vladislav.androidstudy.kotlin.utils.isPalindrome())
//        println("aba is a palindrome - " + "aba".com.example.vladislav.androidstudy.kotlin.utils.isPalindrome())
//        println("abba is a palindrome - " + "abba".com.example.vladislav.androidstudy.kotlin.utils.isPalindrome())
//        println("abbc is a palindrome - " + "abbc".com.example.vladislav.androidstudy.kotlin.utils.isPalindrome())
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
    }
}
