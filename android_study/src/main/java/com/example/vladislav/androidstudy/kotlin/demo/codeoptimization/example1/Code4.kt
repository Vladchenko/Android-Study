package com.example.vladislav.androidstudy.kotlin.demo.codeoptimization.example1

/** Another one optimized code. */
class Code4 {

    // Considering previous sample - saving and validating should not go in one method, so here
    // it is split.

    /** TODO */
    data class User(val id: Int, val name: String, val address: String)

    /**
     * TODO
     * @property user to be saved to database
     */
    fun persistUser(user: User) {
        user.validateUser()
        user.saveUser()
    }

    private fun User.saveUser() {
        // Here goes a code to save a user instance to database. It also has to become Serializable.
    }

    private fun User.validateUser() {
        fun validate(user: User, value: String, fieldName: String) {
            if (value.isEmpty()) {
                throw IllegalArgumentException(
                        "User ${user.id} data is not consistent: $fieldName is empty"
                )
            }
        }
        validate(this, this.name, "Name")
        validate(this, this.address, "Address")
    }
}
