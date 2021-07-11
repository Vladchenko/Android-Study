package com.example.kotlinstudy.demo

import java.util.*

/**
 * @author Yanchenko Vladislav on 30.08.2020.
 */
class Stepik {

    /**
     * https://stepik.org/lesson/46346/step/1?after_pass_reset=true&unit=24389
     */
    public fun toJSON(collection: Collection<Int>): String {
        val sb = StringBuilder()
        sb.append("[")
        collection.forEachIndexed { index, element ->
            if (index < collection.size - 1) {
                sb.append(element).append(", ")
            } else {
                sb.append(element)
            }
        }
        sb.append("]")
        return sb.toString()
    }

    /**
     * https://stepik.org/lesson/46347/step/1?after_pass_reset=true&unit=24386
     */
    fun joinOptions(options: Collection<String>) = options.joinToString(prefix = "[", postfix = "]")

    /**
     * https://stepik.org/lesson/46349/step/1?after_pass_reset=true&unit=24388
     */
    fun containsEven(collection: Collection<Int>) = collection.any {
            it % 2 == 0
    }

    /**
     * https://stepik.org/lesson/46351/step/1?after_pass_reset=true&unit=24387
     */
    data class Person(val name: String, val age: Int)
    fun getPeople(): List<Person> {
        return listOf(Person("Alice", 29), Person("Bob", 31))
    }

    /**
     *
     */
    fun sendMessageToClient(client: Client?, message: String?, mailer: Mailer) {
        val email = client?.personalInfo?.email
        if (email == null || message == null) {
            return
        }
        mailer.sendMessage(email, message)
    }
    class Client (val personalInfo: PersonalInfo?)
    class PersonalInfo (val email: String?)
    interface Mailer {
        fun sendMessage(email: String, message: String)
    }

    /**
     * https://stepik.org/lesson/46353/step/1?after_pass_reset=true&unit=24383
     */
    fun eval(expr: Expr): Int =
        when (expr) {
            is Num -> expr.value
            is Sum -> eval(expr.left) + eval(expr.right)
            else -> throw IllegalArgumentException("Unknown expression")
        }
    interface Expr
    class Num(val value: Int) : Expr
    class Sum(val left: Expr, val right: Expr) : Expr

    /**
     * https://stepik.org/lesson/46354/step/1?after_pass_reset=true&unit=24390
     */
    fun Int.r(): RationalNumber = RationalNumber(numerator = this,denominator = 1)
    fun Pair<Int, Int>.r(): RationalNumber = RationalNumber(this.first, this.second)
    data class RationalNumber(val numerator: Int, val denominator: Int)
    // other solution
//    fun Int.r(): RationalNumber = RationalNumber(this)
//    fun Pair<Int, Int>.r(): RationalNumber = RationalNumber(first, second)
//    data class RationalNumber(val numerator: Int, val denominator: Int = 1)

    /**
     * https://stepik.org/lesson/46355/step/1?after_pass_reset=true&unit=24393
     */
    fun getList(): List<Int> {
        val arrayList = arrayListOf(1, 5, 2)
        Collections.sort(arrayList, object: Comparator<Int> {
            override fun compare(o1: Int?, o2: Int?): Int {
                if (o1 != null && o2 != null)
                return o1 - o2
                return 0
            }
        })
        return arrayList
    }

}
