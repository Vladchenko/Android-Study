package com.example.vladislav.androidstudy.kotlin.study.kotlinkoans

import java.util.Random as JRandom
import kotlin.random.Random as KRandom

/**
 * Task 12 - Rename on import
 */
class Task12 {

    fun useDifferentRandomClasses(): String {
        return "Kotlin random: " +
                KRandom.nextInt(2) +
                " Java random:" +
                JRandom().nextInt(2) +
                "."
    }
}