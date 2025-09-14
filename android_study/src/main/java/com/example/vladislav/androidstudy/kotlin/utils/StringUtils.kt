package com.example.vladislav.androidstudy.kotlin.utils

import com.example.vladislav.androidstudy.kotlin.demo.joinToString
import java.util.Locale

/**
 * Some utils methods helper.
 *
 * Several tasks from http://www.itmathrepetitor.ru/prog/zadachi-na-stroki/ implemented.
 *
 * @author Yanchenko Vladislav on 05.08.2020.
 */

/** Checks if character is not a digit. */
fun isNotDigit(c: Char) = c !in '0'..'9'    // Or !c.isDigit()

/** Character to integer conversion. */
fun charToInt(c: Char): Int {
    if (!c.isDigit()) throw IllegalArgumentException("$c is not a digit")
    else return c.code - 48     // return c.digitToInt() - an alternative
}

fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'

/**
 * Checks whether the string consists only of alphabetic characters (case-insensitive).
 *
 * @return true if all characters are letters, false otherwise.
 */
fun String.isAllLetters() = this.all { it.isLetter() } // c.com.example.vladislav.androidstudy.kotlin.utils.isLetter(), method from CharJVM.kt

fun String.isNumber() = this.toDoubleOrNull() != null

fun String.isInteger() = this.toIntOrNull() != null

fun String.isRealNumber() = this.toIntOrNull() == null && this.toDoubleOrNull() != null

fun String.isPalindrome(): Boolean {
    if (this.length == 1) return true
    this.dropLast(this.length / 2).mapIndexed { index, char ->
        if (char != this[this.length - 1 - index]) {
            return false
        }
    }
    return true
}

fun String.isPalindrome2(): Boolean {
    val stringHalf =
            if (this.length % 2 == 0) {
                this.drop(this.length / 2)
            } else {
                this.drop(this.length / 2 + 1)
            }
    stringHalf.forEachIndexed { index, c ->
        if (c != this[this.length / 2 - index - 1]) return false
        if (this.length / 2 == index) return true
    }
    return true
}

/** Retrieve a list of words */
fun String.splitIntoWords() = this.trim().split(Regex("\\s+"), 0)
// For split(), one could use split(" "), even split("") for space delimiter
// trim() is redundant, since split(" ") or split("") removes leading and subsequent spaces.

/** Counts the number of words in the input string (numbers included) */
fun String.countWords() = this.trim().split(Regex("\\s+"), 0).size // Or .count instead of .size

/** Finds palindromes in given string */
fun String.findPalindromes() = this.splitIntoWords().filter { it.isPalindrome() }

/** Counts the number of palindromes in the input string */
fun String.countPalindromes() = this.splitIntoWords().count { it.isPalindrome() }

/** Finds the longest palindrome in the input string */
fun String.findLongestPalindrome() = this.findPalindromes().maxByOrNull { it.length }

/**
 * Extracts all integer and floating-point values from the input string.
 * The input is split by whitespace and trimmed before processing.
 *
 * @return List of [Double] values parsed from the input.
 */
fun String.findNumbers(): List<Double> = this.trim()
    .split(Regex("\\s+"), 0)
    .filter { it.toDoubleOrNull() != null }
    .map { it.toDouble() }

/**
 * Extracts all integer values from the input string.
 * The input is split by whitespace and trimmed before processing.
 *
 * @return List of [Int] values parsed from the input.
 */
fun String.findIntegers(): List<Int> = this.trim()
    .split(Regex("\\s+"), 0)
    .filter { it.toIntOrNull() != null }
    .map { it.toInt() }

/**
 * Checks if all the words in the input string are numbers.
 *
 * @return true if all words in the string are numeric values, false otherwise.
 */
fun String.isAllNumbers() = this.trim().split(Regex("\\s+"), 0).all {
    it.toDoubleOrNull() != null
}

/** Checks if all the words in the input string are integer numbers. */
fun String.isAllIntegers() = this.trim().split(Regex("\\s+"), 0).all {
    it.toIntOrNull() != null
}

/** Checks if all the words in the input string are integer and floating-point numbers. */
fun String.isAllRealNumbers() = this.trim().split(Regex("\\s+"), 0).all {
    it.toDoubleOrNull() != null && it.toIntOrNull() == null
}

/** Count numbers (integer or real) present in a string */
fun String.countNumbers() = this.trim().split(Regex("\\s+"), 0).count {
    it.toDoubleOrNull() != null
}

/**
 * Counts the number of non-numeric words in the input string.
 * A word is considered numeric if it can be parsed as [Double].
 *
 * @return The count of non-numeric words.
 */
fun String.countNonNumericWords() = this.trim().split(Regex("\\s+"), 0).count {
    it.toDoubleOrNull() == null
}

/**
 * Counts the number of occurrences of a specific character in the input string.
 *
 * @param char The character to count.
 * @return The number of times [char] appears in the string.
 */
fun String.countChar(char: Char) = this.count { it == char }

/** Counts the number of digits in the input string */
fun String.countDigits() = this.count { it.isDigit() }

/** Counts the number of non digit chars present in the input string */
fun String.countNonDigits() = this.count { !it.isDigit() }

/**
 * Returns the longest word in the input string.
 * If multiple words have the same maximum length, the first one is returned.
 *
 * @return The longest word, or null if the string is empty or contains no words.
 */
fun String.longestWord() = this.trim().split(Regex("\\s+"), 0).maxByOrNull { it.length }

/**
 * Returns the index of the first occurrence of [subString] in this string.
 * If the substring is not found, returns -1.
 *
 * @param subString The substring to search for.
 * @return The index of the first occurrence, or -1 if not found.
 */
fun String.indexOfFirst(subString: String) = this.indexOf(subString)

/**
 * Removes consecutive duplicate characters in a string.
 * Replaces sequences of the same character with a single occurrence.
 *
 * Examples:
 * - "00 1"      → "0 1"
 * - "11 22 344" → "1 2 34"
 * - "555565 66676777" → "565 6767"
 *
 * @return A new string with consecutive duplicates removed.
 */
fun String.removeConsecutiveDuplicates(): String {
    if (isEmpty()) return this

    val result = StringBuilder()
    var previousChar = this[0]
    result.append(previousChar)

    for (i in 1 until length) {
        val currentChar = this[i]
        if (currentChar != previousChar) {
            result.append(currentChar)
            previousChar = currentChar
        }
    }

    return result.toString()
}

/**
 * Returns the index of the longest word in the list of words.
 * If multiple words have the same maximum length, returns the index of the first one.
 *
 * @return Index of the longest word in the list, or -1 if no words are found.
 */
fun String.indexOfLongestWordInList(): Int {
    val words = this.trim().split(Regex("\\s+"), 0)
    if (words.isEmpty()) return -1

    val longestWord = words.maxByOrNull { it.length }!!
    return words.indexOf(longestWord)
}

/** Removes excessive spaces (ones that come in a sequence) from string */
fun String.removeExcessiveSpaces() = this.split(Regex("\\s+")).toString()       // joinToString()

/** Same functionality as com.example.vladislav.androidstudy.kotlin.utils.removeExcessiveSpaces() */
fun String.removeExcessiveSpaces2() = this.filterIndexed { index, char ->
    index < this.length - 1 && !(char == this[index + 1] && char == ' ')
}.trim()

/** Removes digits, spaces and special characters from string, keeping only symbolic chars. */
fun String.removeAllExceptChars() = this.filter { it.isLetter() }

/** Removes chars, spaces and special characters from string. */
fun String.removeAllExceptDigits() = this.filter { it.isDigit() }

/** Removes all characters, except digits and chars from string */
fun String.removeAllExceptDigitsAndChars() = this.filter { it.isLetterOrDigit() }

/** Counts a number of a capital chars in a string */
fun String.capitalCharsCount() = this.count { it.isUpperCase() }

/** Counts a number of a lowercase chars in a string */
fun String.lowercaseCharsCount() = this.count { it.isLowerCase() }

/** Make every word to begin with capital letter */
fun String.capitalizeFirstLetters() = this.split(Regex("\\s+")).map { it.capitalize(Locale.getDefault()) }.joinToString(" ")

/** Returns true, if symbols (, {, [ have respective pairs and false otherwise. */
fun String.checkIfBracesPaired(): Boolean {
    var squareBrackets = 0
    var brackets = 0
    var braces = 0
    this.map {
        when (it) {
            '(' -> brackets++
            ')' -> brackets--
            '{' -> braces++
            '}' -> braces--
            '[' -> squareBrackets++
            ']' -> squareBrackets--
        }
        if (brackets == -1
                || braces == -1
                || squareBrackets == -1
        ) {
            return false
        }
    }
    return (squareBrackets == 0
            || braces == 0
            || squareBrackets == 0)
}

/** Solves arithmetic expression in a string. Do not put any symbols different to digits, dot and operations (*+-/). */
fun String.solveExpression(): String {
    var value: String
    if (this.contains("[^a-zA-Z0-9.]")) {
        return "NAN"
    } else {
        var operations = this.trim().split(Regex("[0-9.]+")) // 0-9 same as /d
                .map { it.trim() }
                .filter { it.isNotBlank() }
        if (operations.any { it.length > 1 }) {
            return "Wrong operation"
        }
        var operands = this.trim().split(Regex("[+/*\\-]"))
        var index = 0
        do {
            when (operations[index]) {
                "*", "/" -> {
                    value = performArithmeticOperation(operands[index], operands[index + 1], operations[index])
                    operations = operations.take(index) + operations.takeLast(operations.size - index - 1)
                    operands = operands.take(index) + value + operands.subList(index + 2, operands.size)
                }
            }
            index++
        } while (index < operations.size)
        value = operands[0]
        var operandIndex = 0
        operands.drop(1).map {
            value = performArithmeticOperation(value, it, operations[operandIndex++])
        }
    }
    return value
}

/** Replace a [replaceableString] with a [replacingString]. */
fun String.replaceString(replaceableString: String, replacingString: String) =
        // No need to make this function, since there is replace(...) fun for String
        this.trim().split(Regex("\\s+")).joinToString {
            if (it == replaceableString) {
                replacingString
            } else it
        }

/** Replaces digits with its symbolic representation, say "462 3" -> "four six two three" */
fun String.replaceDigitsWithSymbolicRepresentation(): String {
    var resultString = ""
    this.trim().map {
        if (it.isDigit()) {
            resultString = resultString.plus(
                    it.getDigitSymbolicRepresentation()
            )
        }
    }
    return resultString
}

/**
 * Replaces numbers with its symbolic representation,
 * say "462 740 400" -> "four hundred sixty two, seven hundred forty, four hundred".
 *
 * ! Only numbers up to a thousand are processable.
 */
fun String.replaceNumbersWithSymbolicRepresentation(): String {
    var resultNumberString: String
    var resultString = ""
    this.trim().split(Regex("\\s+")).map {
        val reversedNumber = it.reversed()
        resultNumberString = ""
        if (reversedNumber[0] == '0') {
            if (reversedNumber.length > 1) {
                if (reversedNumber[1] == '0') {
                    // Hundreds are checked lower in a code
                } else {
                    resultNumberString = if (reversedNumber[1] == '1') {
                        "ten "
                    } else {
                        reversedNumber[1].getTensSymbolicRepresentation()
                    }
                }
            } else {
                resultNumberString = "zero"
            }
        } else {
            if (reversedNumber.length > 1) {
                if (reversedNumber[1] == '1') {
                    resultNumberString =
                            reversedNumber[0].getTenToTwentySymbolicRepresentation() + resultNumberString
                } else {
                    resultNumberString = reversedNumber[0].getDigitSymbolicRepresentation() + resultNumberString
                    resultNumberString = reversedNumber[1].getTensSymbolicRepresentation() + resultNumberString
                }
            } else {
                resultNumberString = reversedNumber[0].getDigitSymbolicRepresentation() + resultNumberString
            }
        }
        if (reversedNumber.length > 2 && reversedNumber[2] != '0') {
            resultNumberString =
                    reversedNumber[2].getDigitSymbolicRepresentation() + "hundred " + resultNumberString
        }
        resultString = "${resultString.trim()}, ${resultNumberString.trim()}"
    }
    return resultString.drop(2)
}

fun Char.getTenToTwentySymbolicRepresentation() =
        when (this) {
            '0' -> ""
            '1' -> "eleven "
            '2' -> "twelve "
            '3' -> "thirteen "
            '4' -> "fourteen "
            '5' -> "fifteen "
            '6' -> "sixteen "
            '7' -> "seventeen "
            '8' -> "eighteen "
            '9' -> "nineteen "
            else -> ""
        }

private fun Char.getDigitSymbolicRepresentation() =
        when (this) {
            '0' -> "zero "
            '1' -> "one "
            '2' -> "two "
            '3' -> "three "
            '4' -> "four "
            '5' -> "five "
            '6' -> "six "
            '7' -> "seven "
            '8' -> "eight "
            '9' -> "nine "
            else -> ""
        }

private fun Char.getTensSymbolicRepresentation() =
        when (this) {
            '0' -> ""
            '1' -> ""
            '2' -> "twenty "
            '3' -> "thirty "
            '4' -> "forty "
            '5' -> "fifty "
            '6' -> "sixty "
            '7' -> "seventy "
            '8' -> "eighty "
            '9' -> "ninety "
            else -> ""
        }

// Not useful as of now
private fun Char.getHundredsSymbolicRepresentation() =
        when (this) {
            '0' -> ""
            '1' -> "one hundred"
            '2' -> "two hundred"
            '3' -> "three hundred"
            '4' -> "four hundred"
            '5' -> "five hundred"
            '6' -> "six hundred"
            '7' -> "seven hundred"
            '8' -> "eight hundred"
            '9' -> "nine hundred"
            else -> ""
        }

/**
 * Checks if every char is unique in given string.
 * "qwerty" - returns true
 * "asdfa" - returns false.
 */
fun String.isEveryCharUnique(): Boolean {
    val map: MutableMap<Char, Int> = mutableMapOf()
    this.map {
        if (it in map) return false
        map[it] = 1
    }
    return true
}

/**
 * Checks if every char's frequency is unique in given string.
 * "qw" - returns false
 * "qww" - returns true.
 */
fun String.isEveryCharFrequencyUnique(): Boolean {
    val map: MutableMap<Char, Int> = mutableMapOf()
    this.map {
        if (it in map) {
            map[it] = map[it]?.plus(1) ?: 1
        } else {
            map[it] = 1
        }
    }
    return map.values.distinct().size == map.values.size
    // map.values.distinct().size   can also be replaced with map.values.toSet().size
}

fun String.getUniqueChars(): Set<Char> {
    val valuesMap: MutableMap<Char, Int> = mutableMapOf()
    this.map {
        valuesMap[it] = (valuesMap[it] ?: 0).plus(1)
    }
    return valuesMap.filter {
        it.value == 1
    }.keys
}

fun String.getUniqueChars2() = this.toList().groupingBy { it }.eachCount().filter { it.value == 1 }

/** Taken from https://stackoverflow.com/questions/62232908/kotlin-unique-characters-in-string */
fun isEveryCharacterUnique(s: String): Boolean = s.groupBy { it }
        .values
        .stream()
        .allMatch { it.size == 1 }

/** Taken from https://stackoverflow.com/questions/62232908/kotlin-unique-characters-in-string */
fun String.allUnique(): Boolean = all(hashSetOf<Char>()::add)

fun String.isEveryCharUnique2() = this.toCharArray().distinct().size == this.length

fun String.isEveryCharUnique3() = this.toSet().size == this.length

// Doesn't work
fun String.isEveryCharUnique4() = this.chars().distinct().toString().length == this.length

/** Retrieve distinct chars. */
fun String.distinctChars() = this.toCharArray().distinct()

/** Retrieve distinct chars. */
fun String.distinctChars2() = this.toCharArray().toSet()

/** Retrieve distinct letters (a-z, A-Z). */
fun String.distinctLetters() = this.toCharArray().distinct().filter { it.isLetter() }

fun String.distinctLetters2() = this.toCharArray().distinctBy { it.isLetter() }

/**
 * Retrieve distinct(unique) digits (0-9).
 */
fun String.distinctDigits() = this.toCharArray().distinct().filter { it.isDigit() }

/**
 * Retrieve distinct(unique) strings
 */
fun String.distinctStrings() = this.split(Regex("\\s+")).distinct()

/**
 * Retrieve chars in ascending order
 */
fun String.toAscendingOrderChars() = this.toCharArray().toSortedSet()

/**
 * Retrieve chars in descending order
 */
fun String.toDescendingOrderChars() = this.toCharArray().toSortedSet().reversed()

/**
 * Retrieve string in backwards order of its chars.
 */
fun String.toBackwardsOrderChars() = this.reversed()

/**
 * Retrieve string in backwards order of its incorporating strings.
 */
fun String.toBackwardsOrderStrings() = this.split(Regex("\\s+")).reversed()

/**
 * Retrieve string in sorted order of its incorporating strings.
 */
fun String.toAlphabetSortedStrings() = this.trim().split(Regex("\\s+"))
        .sorted()      // trim() is needed here, else it provides empty item in 1st and 2nd positions

/**
 * Retrieve string in sorted order of its incorporating strings.
 */
fun String.toAlphabetSortedStrings2() = this.trim().split(Regex("\\s+"))
        .toSortedSet()      // trim() is needed here, else it provides empty item in 1st position

/**
 * Get all Java versions, say "java Java 5, Java  6, Java 1.6" should yield [Java 5, Java  6, Java 1.6]
 */
fun String.getAllJavaVersions(): List<String> {
    val numberPattern =
            "([0-9]+[.])?[0-9]+".toRegex()  // TODO How to fix leading zero, when it is only one present - Java 0 ?
    val stringsList = this.split(Regex("[\\s]+"))
    val stringsListFinal: MutableList<String> = mutableListOf()
    stringsList.forEachIndexed { index, currentString ->
        if (currentString == "Java"
                && index != stringsList.size - 1
                && numberPattern.matches(stringsList[index + 1])
                && stringsList[index + 1] != "0"    // TODO Remove this condition when a regex is to exclude 0.
        ) {
            stringsListFinal.add(currentString + " " + stringsList[index + 1])
        }
    }
    return stringsListFinal
}

/**
 * Retrieve string in sorted order of a lengths of its incorporating strings.
 */
fun String.toLengthSortedStrings() = this.split(Regex("\\s+")).sortedBy {
    it.length
}

/**
 * Retrieve shortest string.
 */
fun String.findShortestString() = this.split(Regex("\\s+")).minByOrNull { it.length }

/**
 * Retrieve shortest string map, like (string, string length), say (s, 1)
 */
fun String.findShortestStringMap() = this.split(Regex("\\s+")).map { it to it.length }.minByOrNull { it.second }

/**
 * Retrieve distance between 2 strings in string.
 */
fun String.getDistanceBetween2Strings(string1: String, string2: String): Int {
    var index1 = this.indexOf(string1)
    var index2 = this.indexOf(string2)
    return if (index1 > index2) {
        index1 - index2 - string2.length
    } else {
        index2 - index1 - string1.length
    }
}

/**
 * Retrieve a substring most often met in a given string.
 * String "as sd df fg as" returns "as=2".
 */
fun String.mostOftenMetString(): String {
    val map = mutableMapOf<String, Int>()
    this.split(Regex("\\s+")).forEach {
        if (map[it] != null) {
            map[it] = map[it]?.plus(1) as Int
        } else {
            map[it] = 1
        }
    }
    return map.maxByOrNull { it.value }.toString()
}

/** Simplified version of com.example.vladislav.androidstudy.kotlin.utils.mostOftenMetString(). */
fun String.mostOftenMetString2() = this.split(Regex("\\s+")).groupingBy { it }.eachCount().maxByOrNull { it.value }.toString()

/**
 * Retrieve a number of lucky tickets, whose sum of first 3 digits equal to sum of 3 last digits.
 */
fun retrieveNumberOfLuckyTickets(): Int {
    var sum = 0
    var sumI: Int
    var sumJ: Int
    for (i in 1..999) {
        sumI = i.toString().sumOf { it.code - 49 }
        for (j in 1..999) {
            sumJ = j.toString().sumOf { it.code - 49 }
            if (sumI == sumJ) {
                sum++
            }
        }
    }
    return sum
}

private fun performArithmeticOperation(
        operand1: String,
        operand2: String,
        operation: String
): String {
    when (operation) {
        "-" -> return (operand1.toDouble() - operand2.toDouble()).toString()
        "+" -> return (operand1.toDouble() + operand2.toDouble()).toString()
        "*" -> return (operand1.toDouble() * operand2.toDouble()).toString()
        "/" -> {
            if (operand2.toDouble().compareTo(0) == 0) {
                return "Division by zero"
            }
            return (operand1.toDouble() / operand2.toDouble()).toString()
        }
    }
    return "Unsupported operation"
}
