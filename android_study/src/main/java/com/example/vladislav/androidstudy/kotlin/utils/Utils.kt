package com.example.vladislav.androidstudy.kotlin.utils

import java.util.stream.IntStream

/**
 * Some utils class.
 *
 * Several tasks from http://www.itmathrepetitor.ru/prog/zadachi-na-stroki/ implemented.
 *
 * @author Yanchenko Vladislav on 05.08.2020.
 */

/**
 * Checks if character is not a digit.
 */
fun isNotDigit(c: Char) = c !in '0'..'9'    // Or !c.isDigit()

/**
 * Character on integer conversion.
 */
fun charToInt(c: Char): Int {
    if (c !in '0'..'9') throw IllegalArgumentException("Argument is wrong -> $c, it has to be a digit")
    else return c.toInt() - 48
}

fun <T> printArray(array: Array<T>) {
//            for (element in array) {
//                println(element)
//            }
//            array.forEach(System.out::print)
//            array.forEach(::print)
//            println(array.joinToString(" "))
    array.map { println(it) }
}

fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'  // c.isLetter()

fun String.isPalindrome(): Boolean {
    if (this.length == 1) return true
    this.dropLast(this.length / 2).mapIndexed { index, char ->
        if (char != this[this.length - 1 - index]) {
            return false
        }
    }
    return true
}

/**
 * Retrieves a list of words
 */
fun String.retrieveWords() = this.trim().split(Regex("\\s+"), 0)

/**
 * Retrieves a list of numbers
 */
fun String.retrieveNumbers() = this.trim().split(Regex("\\s+"), 0).filter {
    it.toDoubleOrNull() != null
}

/**
 * Gets a quantity of a words present in a string (numbers included)
 */
fun String.wordsNumber() = this.trim().split(Regex("\\s+"), 0).size // Or .count()

/**
 * Retrieves a quantity of a numbers present in a string
 */
fun String.numbersQuantity() = this.trim().split(Regex("\\s+"), 0).count {
    it.toDoubleOrNull() != null
}

/**
 * Retrieves a number of a words in a string (numbers are not considered)
 */
fun String.wordsQuantity() = this.trim().split(Regex("\\s+"), 0).count {
    it.toDoubleOrNull() == null
}

/**
 * Retrieves a number of char present in a string
 */
fun String.charNumberInString(char: Char) = this.count { it == char }   // Or just String.size

/**
 * Retrieves the longest word (first occurence is taken)
 */
fun String.longestWord() = this.trim().split(Regex("\\s+"), 0).maxOrNull()

/**
 * Gets position of a subString in a string (first occurence)
 */
fun String.stringPosition(subString: String) = this.indexOf(subString)

/**
 * Turns repetitive symbols placed in one sequence, into one symbol in a string.
 * 00 1 -> 0 1
 * 11 22 344 -> 1 2 34
 * 555565 66676777 -> 565 6767
 */
fun String.removeRepetitiveSymbols(): String {
    if (this.length < 2) return this
    var string = ""
    this.mapIndexed { index, char ->
        if (this.length > index + 1
            && char != this[index + 1]
        ) {
            string += char
        }
    }
    string += this[length - 1]
    return string
}

/**
 * Retrieves an index of a longest word in a string.
 */
fun String.indexOfLongestWord(): Int {
    val longestWord = this.longestWord()
    longestWord?.let {
        return indexOf(it)
    }
    return -1
}

/**
 * Removes excessive spaces (ones that come in a sequence) from string
 */
fun String.removeExcessiveSpaces() = this.split(Regex("\\s+")).toString()

/**
 * Same functionality as removeExcessiveSpaces()
 */
fun String.removeExcessiveSpaces2() = this.filterIndexed { index, char ->
    index < this.length - 1 && !(char == this[index + 1] && char == ' ')
}.trim()

/**
 * Removes digits, spaces and special characters from string, keeping only letters.
 */
fun String.removeAllExceptLetters() = this.filter { it.isLetter() }

/**
 * Removes letters, spaces and special characters from string.
 */
fun String.removeAllExceptDigits() = this.filter { it.isDigit() }

/**
 * Removes all characters, except digits and letters from string
 */
fun String.removeAllExceptDigitsAndLetters() = this.filter { it.isLetterOrDigit() }

/**
 * Counts a number of a capital letters in a string
 */
fun String.capitalLettersCount() = this.count { it.isUpperCase() }

/**
 * Counts a number of a lowercase letters in a string
 */
fun String.lowercaseLettersCount() = this.count { it.isLowerCase() }

/**
 * Returns true, if symbols (, {, [ have respective pairs and false otherwise.
 */
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
    if (squareBrackets == 0
        || braces == 0
        || squareBrackets == 0
    ) {
        return true
    }
    return false
}

/**
 * Solves arithmetic expression in a string. Do not put any symbols different to digits, dot and operations (*+-/).
 */
fun String.solveExpression(): String {
    var value = ""
    if (this.contains("[^a-zA-Z0-9.]")) {
        return "NAN"
    } else {
        var operands = this.trim().split(Regex("[0-9.]+"))
            .map { it.trim() }
            .filter { it.isNotBlank() }
        var values = this.trim().split(Regex("[+/*\\-]"))
        var index = 0;
        do {
            when (operands[index]) {
                "*", "/" -> {
                    value = performArithmeticOperation(values[index], values[index + 1], operands[index])
                    operands = operands.take(index) + operands.takeLast(operands.size - index - 1)
                    values = values.take(index) + value + values.subList(index + 2, values.size)
                }
            }
            index++
        } while (index < operands.size)
        value = values[0]
        var operandIndex = 0
        values.drop(1).map {
            value = performArithmeticOperation(value, it, operands[operandIndex++])
        }
    }
    return value
}

/**
 * Replaces digits with its symbolic representation, say "462 3" -> "four six two three"
 */
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
 * ! Only numbers up to hundreds are processable.
 */
fun String.replaceNumbersWithSymbolicRepresentation(): String {
    var resultNumberString = ""
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

/**
 * Checks if every char is unique in given string.
 * "qwerty" - returns true
 * "asdfa" - return false.
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
 * Retrieves distinct chars.
 */
fun String.distinctChars() = this.toCharArray().distinct()

/**
 * Retrieves distinct chars.
 */
fun String.distinctChars2() = this.toCharArray().toSet()

/**
 * Retrieves chars in ascending order
 */
fun String.toAscendingOrderChars() = this.toCharArray().toSortedSet()

/**
 * Retrieves chars in descending order
 */
fun String.toDescendingOrderChars() = this.toCharArray().toSortedSet().reversed()

/**
 * Retrieves a substring most often met in a given string.
 * String "as sd df fg as" returns "as=2".
 */
fun String.mostOftenMetString(): String {
    val map: MutableMap<String, Int> = mutableMapOf()
    this.split(Regex("\\s+")).map {
        if (map[it] != null) {
            map[it] = map[it]?.plus(1) as Int
        } else {
            map[it] = 1
        }
    }
    return map.maxByOrNull { it.value }.toString()
}

private fun Char.getTenToTwentySymbolicRepresentation() =
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
