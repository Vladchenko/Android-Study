package com.example.vladislav.androidstudy.kotlin.study.coursera

/**
 * Coursera Kotlin study course - https://www.coursera.org/learn/kotlin-for-java-developers/
 *
 * @author Yanchenko Vladislav
 * @since 09.03.2021
 */

/**
 * Implement the function that checks whether a string is a valid identifier. A valid identifier is a non-empty string
 * that starts with a letter or underscore and consists of only letters, digits and underscores.
 *
 * https://www.coursera.org/learn/kotlin-for-java-developers/ungradedWidget/Yqyi3/kotlin-playground-checking-identifier
 */
fun isValidIdentifier(s: String): Boolean {
    if (s.isBlank()) {
        return false
    }
    when {
        s[0] in '0'..'9' -> return false
        s[0] !in 'a'..'z' && s[0] !in 'A'..'Z' && s[0] != '_' -> return false
    }
    for (char in s.subSequence(1, s.length)) {
        if (char !in 'a'..'z' && char !in 'A'..'Z' && char !in '0'..'9' && char != '_') return false
    }
    return true
}

fun isValidIdentifier2(s: String): Boolean {
    fun isValidFirstCharacter(char: Char) =
        char == '_' || char.isLetter()

    fun isValidCharacter(char: Char) =
        char == '_' || char.isDigit() || char.isLetter()

    if (s.isBlank()) {
        return false
    }

    if (!isValidFirstCharacter(s[0])) {     // Checking first letter
        return false
    }

    s.drop(1).forEachIndexed { index, char ->   // forEachIndexed could be replaced with mapIndexed
        if (index > 0 && !isValidCharacter(char)) {
            return false
        }
    }
    // Instead of s.drop(1).forEachIndexed, one could have used - for (i in 1 until s.length - 1)

    return true
}

fun testIsValidIdentifier() {
    println("name is = " + isValidIdentifier2(
        "name"
    )
    )   // true
    println("_name is = " + isValidIdentifier2(
        "_name"
    )
    )  // true
    println("_12 is = " + isValidIdentifier2(
        "_12"
    )
    )    // true
    println(" is = " + isValidIdentifier2(""))       // false
    println("012 is = " + isValidIdentifier2(
        "012"
    )
    )    // false
    println("no$ is = " + isValidIdentifier2(
        "no$"
    )
    )    // false
}

fun String?.isEmptyOrNull():Boolean {
    if (this == null || this.isEmpty()) {
        return true
    }
    return false
}

fun testIsEmptyOrNull() {
    val s1: String? = null
    val s2: String? = ""
    s1.isEmptyOrNull() eq true
    s2.isEmptyOrNull() eq true

    val s3 = "   "
    s3.isEmptyOrNull() eq false
}


// Interchangeable predicates
//https://www.coursera.org/learn/kotlin-for-java-developers/ungradedWidget/ndlIy/kotlin-playground-interchangeable-predicates

fun List<Int>.allNonZero() =  all { it != 0 }
fun List<Int>.allNonZero1() =  none { it == 0 }
fun List<Int>.allNonZero2() =  !any { it == 0 }

fun List<Int>.containsZero() =  any { it == 0 }
fun List<Int>.containsZero1() =  !all { it != 0 }
fun List<Int>.containsZero2() =  !none { it == 0 }

fun runInterchangeablePredicates() {
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

private infix fun <T> T.eq(other: T) {
    if (this == other) println("OK")
    else println("Error: $this != $other")
}
