package com.example.vladislav.androidstudy.kotlin.utils

import com.example.vladislav.androidstudy.kotlin.utils.Utils.Companion.longestWord

/**
 * This method is same to the one placed inside of a companion object of a class
 */
fun isNotDigit(c: Char) = c !in '0'..'9'

/**
 * Some utils class
 *
 * @author Yanchenko Vladislav on 05.08.2020.
 */
class Utils {

    companion object {

        fun charToInt(c: Char): Int {
            if (c !in '0'..'9') throw IllegalArgumentException("Argument is wrong -> $c, it has to be a digit")
            else return c.toInt() - 48
        }

        fun <T> printArray(array: Array<T>) {
//            array.forEach(System.out::print)
//            println(array.joinToString(" "))
            for (element in array) {
                println(element)
            }
        }

        fun isLetter(c: Char) = c in 'a'..'z' && c in 'A'..'Z'

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
         * Removes all the unnecessary spaces
         */
        fun String.retrieveWords() = this.trim().split(Regex("\\s+"), 0)

        /**
         * Gets a quantity of a words present in string (numbers included)
         */
        fun String.wordsNumber() = this.trim().split(Regex("\\s+"), 0).size

        /**
         * Gets a quantity of a numbers present in string
         */
        fun String.numbersQuantity() = this.trim().split(Regex("\\s+"), 0).count {
            it.toIntOrNull() != null
        }

        /**
         * Gets a number of a words in a string (numbers are not considered)
         */
        fun String.wordsQuantity() = this.trim().split(Regex("\\s+"), 0).count {
            it.toIntOrNull() == null
        }

        /**
         * Gets a number of char present in a string
         */
        fun String.charNumberInString(char: Char) = this.count { it == char }

        /**
         * Gets the longest word (first occurence is taken)
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
         * Removes digits and spaces characters from string
         */
        fun String.removeAllExceptLetters() = this.filter { it.isLetter() }

        /**
         * Removes digits and spaces characters from string
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
         * Returns [true], if symbols (, {, [ have respective pairs and [false] otherwise.
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
         * Solves expression in a string. Do not put any symbols different to digits, dot and operations (*+-/).
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
                        "*","/" -> {
                            value = performOperation(values[index], values[index + 1], operands[index])
                            operands = operands.take(index) + operands.takeLast(operands.size - index - 1)
                            values = values.take(index) + value + values.subList(index + 2, values.size)
                        }
                    }
                    index++
                } while (index < operands.size)
                value = values[0]
                var operandIndex = 0
                values.drop(1).map {
                    value = performOperation(value, it, operands[operandIndex++])
                }
            }
            return value
        }

        private fun performOperation(
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
    }
}
