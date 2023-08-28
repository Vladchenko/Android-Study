package com.example.vladislav.androidstudy.kotlin.study.leetcode

import java.lang.System.arraycopy
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Tasks taken from leetcode.com
 */
class Solution {

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

    /** Extra solution for isPalindrome */
    fun isPalindrome2(x: Int): Boolean {    // This one is faster and takes less memory
        val string = x.toString()
        for (i in 0..string.length / 2) {
            if (string[i] != string[string.length - i - 1]) {
                return false
            }
        }
        return true
    }

    /** Extra solution for isPalindrome */
    fun isPalindrome3(x: Int) = x.toString() == x.toString().reversed()

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
        val string = re.replace(s, "").lowercase(Locale.getDefault())
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

    /**
     * Problem is described at https://leetcode.com/problems/length-of-last-word/
     */
    fun lengthOfLastWord(s: String): Int {
        return s.trim().split(Regex("\\s+"), 0).last().length
    }

    /**
     * Problem is described at https://leetcode.com/problems/plus-one/
     */
    fun plusOne(digits: IntArray): IntArray {
        if (digits.isEmpty()) {
            return digits
        }
        for (index in digits.indices.reversed()) {
            if (digits[index] == 9) {
                digits[index] = 0
            } else {
                digits[index]++
                return digits
            }
        }
        if (digits[0] == 0) {
            val numbers = IntArray(digits.size + 1)
            arraycopy(digits, 0, numbers, 1, digits.size)
            numbers[0] = 1
            return numbers
        }
        return digits
    }

    /**
     * Problem is described at https://leetcode.com/problems/add-binary/
     */
    fun addBinary(a: String, b: String): String {
        if (a.isEmpty() || b.isEmpty()) {
            return ""
        }
        var result: String
        try {
            result = (a.toLong(2) + b.toLong(2)).toString(radix = 2)
        } catch (ex: NumberFormatException) {
            return ""
        }
        return result
    }

    /**
     * Problem is described at https://leetcode.com/problems/sqrtx/
     */
    fun mySqrt(x: Int): Int {
        return sqrt(x.toDouble()).toInt()
    }

    /**
     * Problem is described at https://leetcode.com/problems/single-number/
     */
    fun singleNumber(nums: IntArray): Int {
        val map = mutableMapOf<Int, Int>()
        var count: Int
        if (nums.isEmpty()) return -1
        nums.forEach {
            count = map[it] ?: 0
            map[it] = count + 1
        }
        return map.keys.first { map[it] == 1 }
    }

    /**
     * Problem is described at https://leetcode.com/problems/pascals-triangle/
     */
    fun generate(numRows: Int): List<List<Int>> {
        val mainList = mutableListOf(mutableListOf<Int>())
        composeList(mainList, numRows)
        return mainList
    }

    private fun composeList(
        mainList: MutableList<MutableList<Int>>,
        numRows: Int
    ): List<List<Int>> {
        if (numRows == 0) {
            return mainList
        }
        if (numRows == 1) {
            mainList[0] = mutableListOf(1)
            return mainList
        }
        if (numRows == 2) {
            mainList[0] = mutableListOf(1)
            mainList.add(mutableListOf(1, 1))
            return mainList
        }
        return composeListFor3AndMoreLines(mainList, numRows)
    }

    private fun composeListFor3AndMoreLines(
        mainList: MutableList<MutableList<Int>>,
        numRows: Int
    ): List<List<Int>> {
        mainList[0] = mutableListOf(1)
        mainList.add(mutableListOf(1, 1))
        for (i in 2 until numRows) {
            mainList.add(mutableListOf())
            mainList[i].add(1)
            for (j in 0..mainList[i - 1].size - 2) {
                mainList[i].add(mainList[i - 1][j] + mainList[i - 1][j + 1])
            }
            mainList[i].add(1)
        }
        return mainList
    }

    /**
     * Problem is described at https://leetcode.com/problems/pascals-triangle-ii/
     */
    fun getRow(rowIndex: Int): List<Int> {
        return when (rowIndex) {
            0 -> {
                mutableListOf(1)
            }

            1 -> {
                mutableListOf(1, 1)
            }

            in 2..Int.MAX_VALUE -> {
                return getRowBiggerThan2(rowIndex)
            }

            else -> {
                mutableListOf()
            }
        }
    }

    private fun getRowBiggerThan2(rowIndex: Int): MutableList<Int> {
        var list1 = mutableListOf(1, 1)
        val list2 = mutableListOf<Int>()
        for (i in 2 until rowIndex + 1) {
            list2.add(1)
            for (j in 0..list1.size - 2) {
                list2.add(list1[j] + list1[j + 1])
            }
            list2.add(1)
            list1.clear()
            list1 = list2.toMutableList()
            list2.clear()
        }
        return list1
    }

    /**
     * Problem is described at https://leetcode.com/problems/merge-two-sorted-lists/description/
     */
    fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
        var list11 = list1
        var list22 = list2
        var operatingListNode: ListNode?
        val resultListNode: ListNode?

        return if (list1 == null) {
            list2
        } else if (list2 == null) {
            list1
        } else {
            if (list11?.`val`!! > list22?.`val`!!) {
                operatingListNode = ListNode(list22.`val`)
                resultListNode = operatingListNode
                list22 = list22.next
                if (list22 == null) {
                    while (list11 != null) {
                        operatingListNode = appendToList(operatingListNode, list11.`val`)
                        list11 = list11.next
                    }
                }
            } else if (list11.`val` < list22.`val`) {
                operatingListNode = ListNode(list11.`val`)
                resultListNode = operatingListNode
                list11 = list11.next
                if (list11 == null) {
                    while (list22 != null) {
                        operatingListNode = appendToList(operatingListNode, list22.`val`)
                        list22 = list22.next
                    }
                }
            } else {
                operatingListNode = ListNode(list11.`val`)
                operatingListNode.next = ListNode(list22.`val`)
                resultListNode = operatingListNode
                operatingListNode = operatingListNode.next
                list11 = list11.next
                list22 = list22.next
            }

            if (list11 != null && list22 != null) {
                while (true) {
                    if (list11?.`val`!! > list22?.`val`!!) {
                        operatingListNode = appendToList(operatingListNode, list22?.`val`!!)
                        list22 = list22?.next
                        if (list22 == null) {
                            // Copy all nodes from list11 to resultListNode
                            while (list11 != null) {
                                operatingListNode = appendToList(operatingListNode, list11?.`val`!!)
                                list11 = list11.next
                            }
                            return resultListNode
                        }
                    }
                    if (list11?.`val`!! < list22?.`val`!!) {
                        operatingListNode = appendToList(operatingListNode, list11?.`val`!!)
                        list11 = list11?.next
                        if (list11 == null) {
                            // Copy all nodes from list22 to resultListNode
                            while (list22 != null) {
                                operatingListNode = appendToList(operatingListNode, list22?.`val`!!)
                                list22 = list22.next
                            }
                            return resultListNode
                        }
                    }
                    if (list11?.`val`!! == list22?.`val`!!) {
                        operatingListNode = appendToList(operatingListNode, list11?.`val`!!)
                        list11 = list11?.next
                        operatingListNode = appendToList(operatingListNode, list22?.`val`!!)
                        list22 = list22?.next
                        if (list11 == null) {
                            // Copy all nodes from list22 to resultListNode
                            while (list22 != null) {
                                operatingListNode = appendToList(operatingListNode, list22?.`val`!!)
                                list22 = list22.next
                            }
                            return resultListNode
                        }
                        if (list22 == null) {
                            // Copy all nodes from list11 to resultListNode
                            while (list11 != null) {
                                operatingListNode = appendToList(operatingListNode, list11?.`val`!!)
                                list11 = list11.next
                            }
                            return resultListNode
                        }
                    }
                }
            }
            return resultListNode
        }
    }

    /**
     * Problem is described at https://leetcode.com/problems/merge-two-sorted-lists/description/
     * Solution from chatGPT
     */
    fun mergeTwoListsFromChatGPT(list1: ListNode?, list2: ListNode?): ListNode? {
        var l1 = list1
        var l2 = list2
        var result: ListNode? = null
        var tail: ListNode? = null

        while (l1 != null || l2 != null) {
            val node = when {
                l1 == null -> l2.also { l2 = l2?.next }
                l2 == null -> l1.also { l1 = l1?.next }
                l1?.`val`!! <= l2?.`val`!! -> l1.also { l1 = l1?.next }
                else -> l2.also { l2 = l2?.next }
            }

            if (result == null) {
                result = node
                tail = node
            } else {
                tail!!.next = node
                tail = node
            }
        }

        return result
    }

    private fun appendToList(node: ListNode?, value: Int): ListNode {
        val newNode = ListNode(value)
        node?.next = newNode
        return newNode
    }

    fun generateListNodes(): ListNode {
        val maxIncrement = (Math.random() * 2).toInt() + 2
        val listNode = ListNode((Math.random() * 3).toInt())
        var listNodeNew = listNode
        for (i in 1..(Math.random() * 5).toInt() + 5) {
            listNodeNew.next =
                ListNode(listNodeNew.`val`.plus((Math.random() * maxIncrement).toInt()))
            listNodeNew = listNodeNew.next!!
        }
        return listNode
    }

    class ListNode(var `val`: Int) {
        var next: ListNode? = null
    }

    /**
     * Problem is described at https://leetcode.com/problems/number-of-1-bits/
     */
    fun hammingWeight(n: Int): Int {
        return n.toUInt().toString(2).count { it == '1' }
    }

    /**
     * NOT PASSED
     * Leetcode review throws following exception:
     * unresolved reference: digitToInt
     * char.digitToInt().toDouble().pow(2)
     * Problem is described at https://leetcode.com/problems/happy-number/
     */
    fun isHappy(n: Int): Boolean {
        var value = n
        if (value in 0..4) {
            return false
        }
        try {
            while (value != 1) {
                value = calculateSumOfDigitsSquaresForDigitsInInt(value)
                if (value in 2..4) {
                    return false
                }
            }
            return true
        } catch (aex: ArithmeticException) {
            return false
        }
    }

    private fun calculateSumOfDigitsSquaresForDigitsInInt(n: Int) =
        n.toString().map { char ->
            char.digitToInt().toDouble().pow(2)
        }.sum()
            .toInt()

    /**
     * https://leetcode.com/problems/remove-duplicates-from-sorted-list/
     */
    fun deleteDuplicates(head: ListNode?): ListNode? {
        if (head == null) {
            return null
        }
        if (head.next == null) {
            return head
        } else {
            if (head.`val` == head.next?.`val`
                && head.next?.next == null
            ) {
                head.next = null
                return head
            }
            if (head.`val` == head.next?.`val`) {
                if (head.next?.next != null) {
                    head.next = head.next?.next
                    deleteDuplicates(head)
                } else {
                    head.next = null
                    return head
                }
            } else {
                if (head.next != null) {
                    deleteDuplicates(head.next)
                }
            }
        }
        return head
    }

    companion object {
        private const val BRACES = "braces"
        private const val BRACKETS = "brackets"
        private const val SQUARE_BRACKETS = "squareBrackets"

        fun traverseListNode(_head: ListNode?) {
            var head = _head
            while (head != null) {
                print("${head.`val`} ")
                head = head.next
            }
            println()
        }
    }
}