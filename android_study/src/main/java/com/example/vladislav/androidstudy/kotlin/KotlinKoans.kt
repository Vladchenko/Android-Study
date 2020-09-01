package com.example.kotlinstudy

import java.util.*

/**
 * Kotlin study from https://play.kotlinlang.org/koans/overview
 *
 * @author Yanchenko Vladislav on 16.08.2020.
 */
class KotlinKoans {

    // 1 task - Hello, world!
    fun start(): String = "OK"

    // Following method does the same as a previous one
    fun startAlt(): String {
        return "OK";
    }

    // 2 task - Named arguments
    fun joinOptions(options: Collection<String>) = options.joinToString(prefix = "[", postfix = "]", separator = ", ")
    // This method returns a string of a joined items of a Collection<String>.
    // joinOptions(arrayListOf("1", "2", "3")) makes it to be [1, 2, 3]

    // 3 task - Default arguments
    fun foo(name: String, number: Int, toUpperCase: Boolean) =
        (if (toUpperCase) name.toUpperCase() else name) + number

    fun useFoo() = listOf(
        foo("a", 42, false),
        foo("b", 1, false),
        foo("c", 42, toUpperCase = true),
        foo(name = "d", number = 2, toUpperCase = true)
    )

    // 4 task - Lambdas
    fun containsEven(collection: Collection<Int>): Boolean = collection.any {
        it % 2 == 0
    }

    // 5 task - Strings
    val month = "(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)"
    fun getPattern(): String = """\d{2} $month \d{4}"""

    // 6 task - Data classes
    data class Person(
        val name:String,
        val age:Int
    )

    fun getPeople(): List<Person> {
        return listOf(Person("Alice", 29), Person("Bob", 31))
    }

    // 7 task - Nullable types
    fun sendMessageToClient(client: Client?, message: String?, mailer: Mailer){
        if (client?.personalInfo?.email != null // ? - stands for - if not null
            && message != null) {
            mailer.sendMessage(client.personalInfo.email, message)
        }
    }

    class Client (val personalInfo: PersonalInfo?)
    class PersonalInfo (val email: String?)
    interface Mailer {
        fun sendMessage(email: String, message: String)
    }

    // 8 task - Smart casts
    fun eval(expr: Expr): Int =
        when (expr) {
            is Num -> {expr.value}
            is Sum -> {eval(expr.left) + eval(expr.right)}
            else -> throw IllegalArgumentException("Unknown expression")
        }

    interface Expr
    class Num(val value: Int) : Expr
    class Sum(val left: Expr, val right: Expr) : Expr

    // 9 task - Extension functions
    fun Int.r(): RationalNumber = RationalNumber(this, 1)
    fun Pair<Int, Int>.r(): RationalNumber = RationalNumber(first, second)

    data class RationalNumber(val numerator: Int, val denominator: Int)

    // 10 task - Object expressions
    fun getList(): List<Int> {
        val arrayList = arrayListOf(1, 5, 2)
        Collections.sort(arrayList, object : Comparator<Int> {
            override fun compare(x : Int, y: Int) = y - x
        })
        return arrayList
    }

    // 11 task - SAM conversions
    fun getListLambda(): List<Int> {
        val arrayList = arrayListOf(1, 5, 2)
        Collections.sort(arrayList, { x, y -> y-x })
        return arrayList
    }

    // 12 task - Extensions on collections
    fun getListSorted(): List<Int> {
        return arrayListOf(1, 5, 2).sortedDescending()
    }

    // Conventions

    // 13 task - Comparison
    // My realization
    data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
        override fun compareTo(other: MyDate): Int {
            if (other.year > this.year) { return 1}
            if (other.year < this.year) { return -1}
            if (other.month < this.month) { return 1}
            if (other.month > this.month) { return -1}
            if (other.dayOfMonth < this.dayOfMonth) { return 1}
            if (other.dayOfMonth > this.dayOfMonth) { return -1}
            return 0
        }
    }
    // Realization from web site
    data class MyDate2(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate2> {
        override fun compareTo(other: MyDate2) = when {
            year != other.year -> year - other.year
            month != other.month -> month - other.month
            else -> dayOfMonth - other.dayOfMonth
        }
    }

    // 14 task - In Range
    class DateRange(val start: MyDate, val endInclusive: MyDate) {
        operator fun contains(d: MyDate): Boolean {
            if (d in start..endInclusive) {
                return true
            }
            return false
        }
    }
    fun checkInRange(date: MyDate, first: MyDate, last: KotlinKoans.MyDate): Boolean {
        return date in DateRange(first, last)
    }

    // 15 task - Range to
    operator fun KotlinKoans.MyDate.rangeTo(other: KotlinKoans.MyDate) = KotlinKoans.DateRange(this, other)

    class DateRange2(override val start: KotlinKoans.MyDate, override val endInclusive: KotlinKoans.MyDate): ClosedRange<KotlinKoans.MyDate>

    fun checkInRange2(date: KotlinKoans.MyDate, first: KotlinKoans.MyDate, last: KotlinKoans.MyDate): Boolean {
        return date in first..last
    }
}
