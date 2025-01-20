package com.example.vladislav.androidstudy.kotlin.demo.codeoptimization.example1

/**
 * Problem - boilerplate code (user name and address check should be joined).
 * Check Code2 sample for that.
 *
 * Code taken from a "Kotlin in action" book.
 *
 * @author Yanchenko Vladislav
 * @since 15.03.2021
 */
class Code1 {
    class User(val id: Int, val name: String, val address: String)

    fun saveUser(user: User) {
        if (user.name.isEmpty()) {
            throw IllegalArgumentException(
                "Cannot save user ${user.id}: Name is empty"
            )
        }
        if (user.address.isEmpty()) {
            throw IllegalArgumentException(
                "Cannot save user ${user.id}: Address is empty"
            )
        }
    }
    // Save user to the database
}
