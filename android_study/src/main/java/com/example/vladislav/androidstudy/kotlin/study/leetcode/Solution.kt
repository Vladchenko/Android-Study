package com.example.vladislav.androidstudy.kotlin.study.leetcode

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.substring

/**
 * Tasks taken from leetcode.com
 */
class Solution {

    // https://leetcode.com/problemset/all/?difficulty=EASY&page=1

    /**
     * Problem is described at https://leetcode.com/problems/two-sum/
     */
    fun twoSum(nums: IntArray, target: Int): IntArray {
        nums.forEachIndexed { i, elementI ->
            for (j in i + 1 until nums.size) {
                if (i != j
                    && elementI + nums[j] == target
                ) {
                    return intArrayOf(i, j)
                }
            }
        }
        return intArrayOf()
    }

    /**
     * Problem is described at https://leetcode.com/problems/palindrome-number/
     */
    fun isPalindrome(x: Int): Boolean {     // This one is faster and takes less memory
        val string = x.toString()
        var index = 0
        var isPalindrome = true
        string.dropLast(string.length / 2).chars().forEach outer@{
            index++
            if (it.toChar() != string[string.length - index]) {
                isPalindrome = false
                return@outer
            }
        }
        return isPalindrome
    }

    /**
     * Extra solution for isPalindrome
     */
    fun isPalindrome2(x: Int): Boolean {    // This one is faster and takes less memory
        val string = x.toString()
        for (i in 0..string.length / 2) {
            if (string[i] != string[string.length - i - 1]) {
                return false
            }
        }
        return true
    }

    /**
     * Problem is described at https://leetcode.com/problems/roman-to-integer/
     */
    fun romanToInt(s: String): Int {
        if (s.length == 1) return romanCharToInt(s[0])
        var amount = romanCharToInt(s[s.length - 1])
        for (i in s.length - 2 downTo 0) {
            if (romanCharToInt(s[i]) < romanCharToInt(s[i + 1])) {
                amount -= romanCharToInt(s[i])
                continue   // Acts as continue in Java
            }
            amount += romanCharToInt(s[i])
        }
        return amount
    }

    private fun romanCharToInt(char: Char): Int {
        return when (char) {
            'I' -> 1
            'V' -> 5
            'X' -> 10
            'L' -> 50
            'C' -> 100
            'D' -> 500
            'M' -> 1000
            else -> throw NumberFormatException("Wrong char for roman letter provided")
        }
    }

    /**
     * Problem is described at https://leetcode.com/problems/longest-common-prefix/
     */
    fun longestCommonPrefix(strs: Array<String>): String {
        var maxCharMatchingIndex = -1
        var stringsMatchingNumber: Int
        strs.forEach {
            if (it == "") {
                return ""
            }
        }
        run over@{
            for (charsIndex in strs[0].indices) {
                run loop@{
                    stringsMatchingNumber = 0
                    for (strsIndex in 0..strs.size - 2) {
                        try {
                            if (strs[strsIndex][charsIndex] == strs[strsIndex + 1][charsIndex]) {
                                stringsMatchingNumber++
                            } else {
                                return@over
                            }
                        } catch (ex: Exception) {
                            return@loop
                        }
                    }
                    if (stringsMatchingNumber == strs.size - 1) {
                        maxCharMatchingIndex = charsIndex
                    }
                }
            }
        }
        return strs[0].substring(0, maxCharMatchingIndex + 1)
    }

    /**
     * Returns true, if symbols (, {, [ have respective pairs and do not intersect and false otherwise.
     */
    fun isValid(s: String): Boolean {
        val bracketsMap = mutableMapOf(
            BRACES to 0,
            BRACKETS to 0,
            SQUARE_BRACKETS to 0
        )
        s.forEach {
            when (it) {
                '(' -> {
                    bracketsMap[BRACKETS] = bracketsMap[BRACKETS]?.plus(1) ?: 0
//                    if ((bracketsMap[BRACES] ?: 0) > 0
//                        || (bracketsMap[SQUARE_BRACKETS] ?: 0) > 0) {
//                        return false
//                    }
                }
                ')' -> {
                    if ((bracketsMap[BRACES] ?: 0) > 0
                        || (bracketsMap[SQUARE_BRACKETS] ?: 0) > 0
                    ) {
                        return false
                    }
                    bracketsMap[BRACKETS] = bracketsMap[BRACKETS]?.minus(1) ?: 0
                }
                '{' -> {
                    bracketsMap[BRACES] = bracketsMap[BRACES]?.plus(1) ?: 0
//                    if ((bracketsMap[BRACKETS] ?: 0) > 0
//                        || (bracketsMap[SQUARE_BRACKETS] ?: 0) > 0) {
//                        return false
//                    }
                }
                '}' -> {
                    if ((bracketsMap[BRACKETS] ?: 0) > 0
                        || (bracketsMap[SQUARE_BRACKETS] ?: 0) > 0
                    ) {
                        return false
                    }
                    bracketsMap[BRACES] = bracketsMap[BRACES]?.minus(1) ?: 0
                }
                '[' -> {
                    bracketsMap[SQUARE_BRACKETS] = bracketsMap[SQUARE_BRACKETS]?.plus(1) ?: 0
//                    if ((bracketsMap[BRACES] ?: 0) > 0
//                        || (bracketsMap[BRACKETS] ?: 0) > 0) {
//                        return false
//                    }
                }
                ']' -> {
                    if ((bracketsMap[BRACES] ?: 0) > 0
                        || (bracketsMap[BRACKETS] ?: 0) > 0
                    ) {
                        return false
                    }
                    bracketsMap[SQUARE_BRACKETS] = bracketsMap[SQUARE_BRACKETS]?.minus(1) ?: 0
                }
            }
            if (bracketsMap[BRACKETS] == -1
                || bracketsMap[BRACES] == -1
                || bracketsMap[SQUARE_BRACKETS] == -1
            ) {
                return false
            }
        }
        if (bracketsMap[BRACKETS] == 0
            || bracketsMap[BRACES] == 0
            || bracketsMap[SQUARE_BRACKETS] == 0
        ) {
            return true
        }
        return false
    }

    /**
     * Problem is described at https://leetcode.com/problems/valid-palindrome/
     */
    fun isPalindrome(s: String): Boolean {
        val re = Regex("[^A-Za-z0-9\\*s]")
        val string = re.replace(s, "").toLowerCase()
        if (string.isEmpty() || string.length == 1) return true
        for (i in 0..string.length / 2) {
            if (string[i] != string[string.length - i - 1]) {
                return false
            }
        }
        return true
    }

    /**
     * Problem is described at https://leetcode.com/problems/remove-duplicates-from-sorted-array/
     */
    fun removeDuplicates(nums: IntArray): Int {
        var sortedArraySize = nums.size
        var temp: Int

        if (nums.isEmpty()) {
            return 0
        }
        if (nums.size == 1) {
            return 1
        }

        // Find out a value that is different to a previous ones
        var index = nums.size - 1
        temp = nums.size - 1
        while (index - 1 > -1
            && nums[index] == nums[index - 1]
        ) {
            temp = index - 1
            index--
        }

        sortedArraySize -= nums.size - 1 - temp

        for (i in temp downTo 1) {
            if (nums[i] == nums[i - 1]) {
                sortedArraySize--
                for (j in i until temp) {
                    nums[j] = nums[j + 1]
                }
            }
        }
        return sortedArraySize
    }

    /**
     * Someone's solution
     */
    fun removeDuplicates2(nums: IntArray): Int {
        if (nums.size == 0) return 0
        var slowPointer = 0
        for (i in 0..nums.size - 1) {
            if (nums[slowPointer] != nums[i]) {
                nums[slowPointer + 1] = nums[i]
                slowPointer++
            }
        }
        return slowPointer + 1
    }

    /**
     * Problem is described at https://leetcode.com/problems/remove-element/
     */
    fun removeElement(nums: IntArray, `val`: Int): Int {
        var valIndex = 0
        for (i in nums.indices) {
            if (nums[i] != `val`) {
                nums[valIndex] = nums[i]
                valIndex++
            }
        }
        return valIndex
    }

    /**
     * Problem is described at https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/
     */
    fun strStr(haystack: String, needle: String): Int {
        return haystack.indexOf(needle)
    }

    /**
     * Problem is described at https://leetcode.com/problems/search-insert-position/
     */
    fun searchInsert(nums: IntArray, target: Int): Int {
        if (nums.isEmpty()) return 0
        nums.forEachIndexed { index, value ->
            if (value == target) {
                return index
            } else {
                if (value > target) {
                    return index
                }
            }
        }
        if (target > nums.last()) {
            return nums.lastIndex + 1
        }
        return 0
    }

    companion object {
        private const val BRACES = "braces"
        private const val BRACKETS = "brackets"
        private const val SQUARE_BRACKETS = "squareBrackets"
    }
}