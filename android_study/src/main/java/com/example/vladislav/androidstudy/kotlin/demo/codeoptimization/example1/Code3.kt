package com.example.vladislav.androidstudy.kotlin.demo.codeoptimization.example1

/**
 * Optimized code.
 *
 * Code taken from a "Kotlin in action" book.
 *
 * @author Yanchenko Vladislav
 * @since 15.03.2021
 */
class Code3 {
    class User(val id: Int, val name: String, val address: String)

    fun saveUser(user: User) {
        fun User.validateUserBeforeSave() {
            fun validate(
                user: User, value: String,
                fieldName: String
            ) {
                if (value.isEmpty()) {
                    throw IllegalArgumentException(
                        "Cannot save user ${user.id}: $fieldName is empty"
                    )
                }
            }
            validate(user, user.name, "Name")
            validate(user, user.address, "Address")
        }
        user.validateUserBeforeSave()
        // Save user to database
    }

}
