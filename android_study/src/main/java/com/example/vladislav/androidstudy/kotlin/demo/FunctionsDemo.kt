package com.example.vladislav.androidstudy.kotlin.demo

class FunctionsDemo {

    fun generateAnswerString(): String {
        val answerString = if (COUNT == 42) {
            "I have the answer."
        } else {
            "The answer eludes me"
        }
        return answerString
    }

    fun generateAnswerString2(count: Int): String {
        val answerString = if (count == 42) {
            "I have the answer."
        } else {
            "The answer eludes me"
        }
        return answerString
    }

    fun generateAnswerString3(count: Int): String {
        return if (count == 42) {
            "I have the answer."
        } else {
            "The answer eludes me"
        }
    }

    fun generateAnswerString4(count: Int) = if (count == 42) "yes" else "no"

    fun functionAsParameter() {
        listOf(1, 2, 3, 4, 5).forEach(fun(value: Int) {
            if (value == 3) return  // local return to the caller of the anonymous fun, i.e. the forEach loop
            print(value)
        })
        print(" done with anonymous function")
    }

    // Higher-order functions
    private fun stringMapper(str: String, mapper: (String) -> Int): Int {
        // Invoke function
        return mapper(str)
    }

    // Using stringMapper
    val length: Int = stringMapper("Android", { input ->
        input.length
    })

    // Using stringMapper
    val length2 = stringMapper("Android") { input ->
        input.length
    }

    companion object {
        private const val COUNT = 42       // This is a constant, type is automatically inferred as int
    }
}