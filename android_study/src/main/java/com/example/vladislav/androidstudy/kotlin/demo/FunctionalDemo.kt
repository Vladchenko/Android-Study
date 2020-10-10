package com.example.vladislav.androidstudy.kotlin.demo

import com.example.vladislav.androidstudy.kotlin.models.FIO
import com.example.vladislav.androidstudy.kotlin.study.coursera.Gender
import com.example.vladislav.androidstudy.kotlin.study.coursera.Hero

/**
 * Some functional ops here.
 *
 * @author Yanchenko Vladislav
 * @since 09.03.2021
 */

fun mapVsFlatmap() {
    val listFIOs: List<FIO> = listOf(
        FIO("1", "2", "3"),
        FIO("q", "w", "e"),
        FIO("z6", "x7", "c8")
    )
    listFIOs.map { println(it) }
    // listFIOs.flatMap { it:String -> println(it) } // e: Expected parameter of type String
}

fun example1() {
    val heroes = listOf(
        Hero(
            "The Captain",
            60,
            Gender.MALE
        ),
        Hero(
            "Frenchy",
            42,
            Gender.MALE
        ),
        Hero("The Kid", 9, null),
        Hero(
            "Lady Lauren",
            29,
            Gender.FEMALE
        ),
        Hero(
            "First Mate",
            29,
            Gender.MALE
        ),
        Hero(
            "Sir Stephen",
            37,
            Gender.MALE
        )
    )

    println(heroes.firstOrNull { it.age == 30 }?.name)  //null
    println(heroes.map { it.age }.distinct().size)      //5
    println(heroes.filter { it.age < 30 }.size)         //3
    val (youngest, oldest) = heroes.partition { it.age < 30 }
    println(oldest.size)                                //3
    println(heroes.maxByOrNull { it.age }?.name)        //The Captain
    println(heroes.all { it.age < 50 })                 //false
    println(heroes.any { item -> item.gender == Gender.FEMALE })    //true

    val mapByAge: Map<Int, List<Hero>> = heroes.groupBy { it.age }
    val (age, group) = mapByAge.maxBy { (_, group) -> group.size }!!
    println(age)                                        //29

    val mapByName: Map<String, Hero> = heroes.associateBy { it.name }   // maps(name->Hero)
    println(mapByName["Frenchy"]?.age)                  //42

    val unknownHero =
        Hero("Unknown", 0, null)
    mapByName.getOrElse("unknown") { unknownHero }.age  //0

    // val (first, second) = heroes
    //     .flatMap { heroes.map { hero -> it to hero } }
    //     .maxBy { it.first.age - it.second.age }!!
    // println(first.name)                                 //The Captain

    // Slightly different
    val (first, second) = heroes
        .flatMap { first: Hero ->
            heroes.map { second -> first to second }
        }
        .maxBy { it.first.age - it.second.age }!!
    println(first.name)                                 //The Captain

    // Another simple way to get the oldest
    println(heroes.maxBy { it.age }?.name)              //The Captain
    // Another simple way to get the youngest
    println(heroes.minBy { it.age }?.name)              //The Kid
}

// Interchangeable predicates

fun List<Int>.allNonZero() =  all { it != 0 }
fun List<Int>.allNonZero1() =  none { it == 0 }
fun List<Int>.allNonZero2() =  !any { it == 0 }

fun List<Int>.containsZero() =  any { it == 0 }
fun List<Int>.containsZero1() =  !all { it != 0 }
fun List<Int>.containsZero2() =  !none { it == 0 }

fun main(args: Array<String>) {
    val list1 = listOf(1, 2, 3)
    list1.allNonZero() eq true
    list1.allNonZero1() eq true
    list1.allNonZero2() eq true

    list1.containsZero() eq false
    list1.containsZero1() eq false
    list1.containsZero2() eq false

    val list2 = listOf(0, 1, 2)
    list2.allNonZero() eq false
    list2.allNonZero1() eq false
    list2.allNonZero2() eq false

    list2.containsZero() eq true
    list2.containsZero1() eq true
    list2.containsZero2() eq true
}

infix fun <T> T.eq(other: T) {
    if (this == other) println("OK")
    else println("Error: $this != $other")
}
