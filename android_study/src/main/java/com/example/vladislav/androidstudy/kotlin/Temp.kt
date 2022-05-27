package com.example.vladislav.androidstudy.kotlin

/**
 * To make some preliminary stuff and later pass to a respective file
 *
 * @author Yanchenko Vladislav
 * @since 06.02.2021
 */
class Temp {
    fun String.isLetters() = this.all { it in 'a'..'z' || it in 'A'..'Z'  }
    fun String.isLetters2() = this.all { it.isLetter()  }
    fun String.isLetters3() = this.filter { !it.isLetter() }.count() == 0
    fun String.isLetters4() = this.none { !it.isLetter() }
    fun String.isLetters5() = this.filter { it.isLetter()  }.count() == this.length
    fun String.isLetters6() = this.matches("^[a-zA-Z]*$".toRegex())
}
