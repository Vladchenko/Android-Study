package com.example.vladislav.androidstudy.kotlin.study.leetcode

/**
 *
 */
class Solution {
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
}