package com.example.kotlinstudy

import java.util.*

/**
 * Kotlin study from https://play.kotlinlang.org/koans/overview
 *
 * @author Yanchenko Vladislav on 16.08.2020.
 */
class KotlinKoans {

    // Task 1 - Hello, world!
    fun start(): String = "OK"

    // Following method does the same as a previous one
    fun startAlt(): String {
        return "OK";
    }

    // Task 2 - Named arguments
    fun joinOptions(options: Collection<String>) =
        options.joinToString(prefix = "[", postfix = "]", separator = ", ")
    // This method returns a string of a joined items of a Collection<String>.
    // joinOptions(arrayListOf("1", "2", "3")) makes it to be [1, 2, 3]

    // Task 3 - Default arguments
    fun foo(name: String, number: Int, toUpperCase: Boolean) =
        (if (toUpperCase) name.toUpperCase() else name) + number

    fun useFoo() = listOf(
        foo("a", 42, false),
        foo("b", 1, false),
        foo("c", 42, toUpperCase = true),
        foo(name = "d", number = 2, toUpperCase = true)
    )

    // Task 4 - Triple quoted string
    val question = "life, the universe, and everything"
    val answer = 42

    val tripleQuotedString = """
    #question = "$question"
    #answer = $answer""".trimMargin("#")

    // Task 5 - String templates
    val month = "(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)"
    fun getPattern(): String = """\d{2} $month \d{4}"""

    // Task 6 - Nullable types
    fun sendMessageToClient(
        client: Client?, message: String?, mailer: Mailer
    ) {
        if (client != null && message != null && client.personalInfo != null
            && client.personalInfo.email != null
        ) {
            mailer.sendMessage(client.personalInfo.email, message)
        }
    }

    class Client(val personalInfo: PersonalInfo?)
    class PersonalInfo(val email: String?)
    interface Mailer {
        fun sendMessage(email: String, message: String)
    }

    // Task 7 - Nothing type
    fun failWithWrongAge(age: Int?): Unit {
        throw IllegalArgumentException("Wrong age: $age")
    }

    fun checkAge(age: Int?) {
        if (age == null || age !in 0..150) failWithWrongAge(age)
        println("Congrats! Next year you'll be ${age ?: +1}.")
    }

    fun main7() {
        checkAge(10)
    }

    // Task 8 - Lambdas
    fun containsEven(collection: Collection<Int>): Boolean = collection.any {
        it % 2 == 0
    }

    // Task 9 - Data classes
    data class Person(
        val name: String,
        val age: Int
    )

    fun getPeople(): List<Person> {
        return listOf(Person("Alice", 29), Person("Bob", 31))
    }

    fun comparePeople(): Boolean {
        val p1 = Person("Alice", 29)
        val p2 = Person("Alice", 29)
        return p1 == p2  // should be true
    }

    // Task 10 - Smart casts
    fun eval(expr: Expr): Int =
        when (expr) {
            is Num -> {
                expr.value
            }
            is Sum -> {
                eval(expr.left) + eval(expr.right)
            }
            else -> throw IllegalArgumentException("Unknown expression")
        }

    interface Expr
    class Num(val value: Int) : Expr
    class Sum(val left: Expr, val right: Expr) : Expr

    // Task 11 - Sealed classes
//    fun eval(expr: Expr): Int =
//        when (expr) {
//            is Num -> expr.value
//            is Sum -> eval(expr.left) + eval(expr.right)
//        }
//
//    sealed interface Expr
//    class Num(val value: Int) : Expr
//    class Sum(val left: Expr, val right: Expr) : Expr

    // Task 12 - Rename on import
//    import kotlin.random.Random as KRandom
//    import java.util.Random as JRandom
//
//    fun useDifferentRandomClasses(): String {
//        return "Kotlin random: " +
//                KRandom.nextInt(2) +
//                " Java random:" +
//                JRandom().nextInt(2) +
//                "."
//    }

    // Task 13 - Extension functions
    fun Int.r(): RationalNumber = RationalNumber(this, 1)
    fun Pair<Int, Int>.r(): RationalNumber = RationalNumber(first, second)

    data class RationalNumber(val numerator: Int, val denominator: Int)

    // Task 14 - Conventions - Comparison
    // My realization
    // Tested with different dates, seems working
    data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
        override fun compareTo(other: MyDate):Int {
            if (year > other.year)
            {
                return 1
            } else {
                if (year == other.year) {
                    if (month > other.month) {
                        return 1
                    } else {
                        if (month == other.month) {
                            if (dayOfMonth > other.dayOfMonth) {
                                return 1
                            } else {
                                if (dayOfMonth == other.dayOfMonth) {
                                    return 0
                                }
                            }
                        }
                    }
                }
            }
            return -1
        }
    }

    // My realization
    data class MyDate2(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
        override fun compareTo(other: MyDate): Int {
            if (other.year > this.year) {
                return 1
            }
            if (other.year < this.year) {
                return -1
            }
            if (other.month < this.month) {
                return 1
            }
            if (other.month > this.month) {
                return -1
            }
            if (other.dayOfMonth < this.dayOfMonth) {
                return 1
            }
            if (other.dayOfMonth > this.dayOfMonth) {
                return -1
            }
            return 0
        }
    }

    // Realization from web site
    data class MyDate3(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate2> {
        override fun compareTo(other: MyDate2) = when {
            year != other.year -> year - other.year
            month != other.month -> month - other.month
            else -> dayOfMonth - other.dayOfMonth
        }
    }

    //Task 15 - Conventions - Ranges
    fun checkInRange(date: MyDate, first: MyDate, last: MyDate): Boolean {
        return date in first..last
    }

    //Task 15 - Conventions - Ranges
    // Old realization
    class DateRange(val start: MyDate, val endInclusive: MyDate) {
        operator fun contains(d: MyDate): Boolean {
            if (d in start..endInclusive) {
                return true
            }
            return false
        }
    }

    fun checkInRange2(date: MyDate, first: MyDate, last: KotlinKoans.MyDate): Boolean {
        return date in DateRange(first, last)
    }

    //Task 16 - Conventions - For loop
//    class DateRange3(val start: MyDate, val end: MyDate):Iterable<MyDate> {
//        override fun iterator(): Iterator<MyDate> {
//            return MyDateIterator(start, end)
//        }
//    }
//
//    class MyDateIterator(val start: MyDate, val end: MyDate):Iterator<MyDate> {
//        var date: MyDate = start
//        override fun hasNext(): Boolean {
//            return date <= end
//        }
//        override fun next(): MyDate {
//            val resultDate = date
//            date = date.followingDate()     // code of this method is present in
//            // https://play.kotlinlang.org/koans/Conventions/For%20loop/DateUtil.kt. Sadly, it cannot be copied.
//            return resultDate
//        }
//    }
//
//    fun iterateOverDateRange(firstDate: MyDate, secondDate: MyDate, handler: (MyDate) -> Unit) {
//        for (date in firstDate..secondDate) {
//            handler(date)
//        }
//    }





    // TODO Continue adding up a completed tasks






    // 10 task - Object expressions
    fun getList(): List<Int> {
        val arrayList = arrayListOf(1, 5, 2)
        Collections.sort(arrayList, object : Comparator<Int> {
            override fun compare(x: Int, y: Int) = y - x
        })
        return arrayList
    }

    // 11 task - SAM conversions
    fun getListLambda(): List<Int> {
        val arrayList = arrayListOf(1, 5, 2)
        Collections.sort(arrayList, { x, y -> y - x })
        return arrayList
    }

    // 12 task - Extensions on collections
    fun getListSorted(): List<Int> {
        return arrayListOf(1, 5, 2).sortedDescending()
    }

    // Conventions



    // 15 task - Range to
    operator fun KotlinKoans.MyDate.rangeTo(other: KotlinKoans.MyDate) =
        KotlinKoans.DateRange(this, other)

    class DateRange2(
        override val start: KotlinKoans.MyDate,
        override val endInclusive: KotlinKoans.MyDate
    ) : ClosedRange<KotlinKoans.MyDate>


    fun main() {
        println(tripleQuotedString)
    }
}
