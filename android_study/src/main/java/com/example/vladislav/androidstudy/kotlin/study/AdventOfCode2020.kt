package com.example.vladislav.androidstudy.kotlin.study

import java.io.File

/**
 * Task from https://www.youtube.com/watch?v=o4emra1xm88&feature=youtu.be
 */
class AdventOfCode2020 {

    fun task1() {
        val numbers = """123
            |234
            |345
            |456
            |567
        """.trimMargin()
        val numbersList = numbers.split("\n").map {
            it.toIntOrNull()
        }
        val map = numbersList.associate { "key$it" to "value$it" }
        println(map)
        val complements = numbersList.associateBy { 2020 - it!! }   //lambda value is a key in a map
        val complements2 = numbersList.associateWith { 2020 - it!! }   //lambda value is a value  in a map
        println(complements2)


        println()
        println()
    }
}