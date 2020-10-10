package com.example.vladislav.androidstudy.kotlin.study.coursera

/**
 * Some model
 *
 * @author Yanchenko Vladislav
 * @since 09.03.2021
 */

class Hero(val name:String, val age:Int, val gender: Gender?)

/**
 * Person's gender
 */
enum class Gender {
    FEMALE,
    MALE
}
