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
 * Character to integer conversion.
 */
fun charToInt(c: Char): Int {
    if (!c.isDigit()) throw IllegalArgumentException("$c argument is wrong, it has to be a digit")
    else return c.toInt() - 48      // c.digitToInt() - this one should work, but it cannot be imported
}

fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'  // c.isLetter(), method from CharJVM.kt

/**
 * Checks if all the symbols in string are letters.
 */
fun String.isLetters2() = this.all { it.isLetter()  }

fun String.isNumber() = this.toDoubleOrNull() != null

fun String.isIntegerNumber() = this.toIntOrNull() != null

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

fun String.isPalindrome2():Boolean {
    val stringHalf =
        if (this.length % 2 == 0)  {
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

/**
 * Retrieve a list of words
 */
fun String.retrieveWords() = this.trim().split(Regex("\\s+"), 0)
// For split(), one could use split(" "), even split("") for space delimiter
// trim() is redundant, since split(" ") or split("") removes leading and subsequent spaces.

/**
 * Get a quantity of a words present in a string (numbers included)
 */
fun String.wordsNumber() = this.trim().split(Regex("\\s+"), 0).size // Or .count()

/**
 * Retrieve a list of palindromes
 */
fun String.retrievePalindromes() = this.retrieveWords().filter { it.isPalindrome() }

/**
 * Retrieve a number of palindromes
 */
fun String.retrievePalindromesNumber() = this.retrieveWords().count { it.isPalindrome() }

/**
 * Retrieve a longest palindrome
 */
fun String.retrieveLongestPalindrome() = this.retrievePalindromes().maxByOrNull { it.length }

/**
 * Retrieve a list of integer and real numbers
 */
fun String.retrieveNumbers() = this.split(" ").filter {
    it.toDoubleOrNull() != null
}

/**
 * Retrieve a list of integer numbers
 */
fun String.retrieveIntegerNumbers() = this.trim().split(Regex("\\s+"), 0).filter {
    it.toIntOrNull() != null
}

/**
 * Checks if all the char sequences are numbers
 */
fun String.isAllNumbers() = this.trim().split(Regex("\\s+"), 0).all {
    it.toDoubleOrNull() != null
}

/**
 * Checks if all the char sequences are integer numbers
 */
fun String.isAllIntegerNumbers() = this.trim().split(Regex("\\s+"), 0).all {
    it.toIntOrNull() != null
}

/**
 * Checks if all the char sequences are integer numbers
 */
fun String.isAllRealNumbers() = this.trim().split(Regex("\\s+"), 0).all {
    it.toDoubleOrNull() != null && it.toIntOrNull() == null
}

/**
 * Retrieve a quantity of a numbers (integral or real) present in a string
 */
fun String.numbersQuantity() = this.trim().split(Regex("\\s+"), 0).count {
    it.toDoubleOrNull() != null
}

/**
 * Retrieve a number of a words in a string (numbers are not considered)
 */
fun String.wordsQuantity() = this.trim().split(Regex("\\s+"), 0).count {
    it.toDoubleOrNull() == null
}

/**
 * Retrieve a number of entries of char present in a string
 */
fun String.retrieveCharEntries(char: Char) = this.count { it == char }

/**
 * Retrieve a number of digit chars present in a string
 */
fun String.retrieveDigitCharsNumber() = this.count { it.isDigit() }

/**
 * Retrieve a number of non digit chars present in a string
 */
fun String.retrieveNonDigitCharsNumber() = this.count { !it.isDigit() }

/**
 * Retrieve the longest word (first occurence is taken)
 */
fun String.longestWord() = this.trim().split(Regex("\\s+"), 0).maxOrNull()

/**
 * Retrieve position of a subString in a string (first occurrence)
 */
fun String.stringPosition(subString: String) = this.indexOf(subString)

/**
 * Turns repetitive symbols placed in one sequence, into one symbol in a string.
 * 00 1 -> 0 1
 * 11 22 344 -> 1 2 34
 * 555565 66676777 -> 565 6767
 */
fun String.removeRepetitiveChars(): String {
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
 * Retrieve an index of a longest word in a string.
 */
fun String.indexOfLongestWord(): Int {
    val longestWord = this.longestWord()
    longestWord?.let {
        return this.indexOf(it)
    }
    return -1
}

/**
 * Removes excessive spaces (ones that come in a sequence) from string
 */
fun String.removeExcessiveSpaces() = this.split(Regex("\\s+")).toString()       // joinToString()

/**
 * Same functionality as removeExcessiveSpaces()
 */
fun String.removeExcessiveSpaces2() = this.filterIndexed { index, char ->
    index < this.length - 1 && !(char == this[index + 1] && char == ' ')
}.trim()

/**
 * Removes digits, spaces and special characters from string, keeping only symbolic Chars.
 */
fun String.removeAllExceptChars() = this.filter { it.isLetter() }

/**
 * Removes chars, spaces and special characters from string.
 */
fun String.removeAllExceptDigits() = this.filter { it.isDigit() }

/**
 * Removes all characters, except digits and chars from string
 */
fun String.removeAllExceptDigitsAndChars() = this.filter { it.isLetterOrDigit() }

/**
 * Counts a number of a capital chars in a string
 */
fun String.capitalCharsCount() = this.count { it.isUpperCase() }

/**
 * Counts a number of a lowercase chars in a string
 */
fun String.lowercaseCharsCount() = this.count { it.isLowerCase() }

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
 * Replace a [replaceableString] with a [replacingString].
 */
fun String.replaceString(replaceableString: String, replacingString: String) =  // No need to make this function, since there is replace(...) fun for String
    this.trim().split(Regex("\\s+")).joinToString {
        if (it == replaceableString) {
            replacingString
        } else it
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

fun String.isEveryCharUnique2() = this.toCharArray().distinct().size == this.length

fun String.isEveryCharUnique3() = this.toSet().size == this.length

/**
 * Retrieve distinct(unique) chars.
 */
fun String.distinctChars() = this.toCharArray().distinct()

/**
 * Retrieve distinct(unique) chars.
 */
fun String.distinctChars2() = this.toCharArray().toSet()

/**
 * Retrieve distinct(unique) letters (a-z, A-Z).
 */
fun String.distinctLetters() = this.toCharArray().distinct().map { it.isLetter() }

/**
 * Retrieve distinct(unique) digits (0-9).
 */
fun String.distinctDigits() = this.toCharArray().distinct().map { it.isDigit() }

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
fun String.toAlphabetSortedStrings() = this.trim().split(Regex("\\s+")).sorted()      // trim() is needed here, else it provides empty item in 1st and 2nd positions

/**
 * Retrieve string in sorted order of its incorporating strings.
 */
fun String.toAlphabetSortedStrings2() = this.trim().split(Regex("\\s+")).toSortedSet()      // trim() is needed here, else it provides empty item in 1st position

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
 * Retrieve shortest string, like (string, string length), say (s, 1)
 */
fun String.findShortestStringMap() = this.split(Regex("\\s+")).map { it to it.length }.minByOrNull { it.second }

/**
 * Retrieve distance between 2 string in chars number.
 */
fun String.getDistanceBetween2Strings(string1: String, string2: String): Int {
    var index1 = -1
    var index2 = -1
    index1 = this.indexOf(string1)
    index2 = this.indexOf(string2)
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

/**
 * Retrieve a number of lucky tickets, whose sum of first 3 digits equal to sum of 3 last digits.
 */
fun retrieveNumberOfLuckyTickets(): Int {
    var sum = 0
    var sumI: Int
    var sumJ: Int
    for (i in 1..999) {
        sumI = i.toString().sumOf { it.toInt() - 49 }
        for (j in 1..999) {
            sumJ = j.toString().sumOf { it.toInt() - 49 }
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
