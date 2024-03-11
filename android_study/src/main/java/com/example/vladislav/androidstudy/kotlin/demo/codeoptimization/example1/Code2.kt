package com.example.vladislav.androidstudy.kotlin.demo.codeoptimization.example1

/**
 * Code could be optimized even more - validation could be done as an extension function.
 *
 * Code taken from a "Kotlin in action" book.
 *
 * @author Yanchenko Vladislav
 * @since 15.03.2021
 */
class Code2 {
    class User(val id: Int, val name: String, val address: String)

    fun saveUser(user: User) {
    }

    fun validate(user: User, value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException(
                    "Cannot save user ${user.id}: $fieldName is empty"
            )
        }
    }

    fun validateUser(user: User, value: String, fieldName: String) {
        validate(user, user.name, "Name")
        validate(user, user.address, "Address")
    }
// Save user to the database }
}
