package com.example.vladislav.androidstudy.kotlin.study.kotlinkoans

import java.util.Calendar

/**
 * Task 16 - Conventions - For loop
 */
class Task16 {
    data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
        override fun compareTo(other: MyDate): Int {
            if (year != other.year) return year - other.year
            if (month != other.month) return month - other.month
            return dayOfMonth - other.dayOfMonth
        }

    /*
     * Returns the following date after the given one.
     * For example, for Dec 31, 2019 the date Jan 1, 2020 is returned.
     */
        fun followingDate(): MyDate {
            val c = Calendar.getInstance()
            c.set(year, month, dayOfMonth)
            val millisecondsInADay = 24 * 60 * 60 * 1000L
            val timeInMillis = c.timeInMillis + millisecondsInADay
            val result = Calendar.getInstance()
            result.timeInMillis = timeInMillis
            return MyDate(
                result.get(
                    Calendar.YEAR
                ), result.get(Calendar.MONTH), result.get(Calendar.DATE)
            )
        }
    }

    operator fun MyDate.rangeTo(other: MyDate) = DateRange(this, other)

    class DateRange(val start: MyDate, val end: MyDate): Iterable<MyDate> {
        override fun iterator(): Iterator<MyDate> {
            return object : Iterator<MyDate> {
                private var currentDate = start

                override fun hasNext(): Boolean {
                    return currentDate <= end
                }

                override fun next(): MyDate {
                    if (!hasNext()) throw NoSuchElementException("No more elements")
                    val result = currentDate
                    currentDate = currentDate.followingDate()
                    return result
                }
            }
        }
    }

    fun iterateOverDateRange(firstDate: MyDate, secondDate: MyDate, handler: (MyDate) -> Unit) {
        for (date in firstDate..secondDate) {
            handler(date)
        }
    }
}