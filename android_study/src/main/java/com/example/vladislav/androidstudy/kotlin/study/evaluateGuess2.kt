package com.example.vladislav.androidstudy.kotlin.study

/**
 * Another template on "mastermind" task
 * https://www.coursera.org/learn/kotlin-for-java-developers/programming/vmwVT/mastermind-game
 *
 * @author Yanchenko Vladislav
 * @since 08.03.2021
 */
class evaluateGuess2 {

    fun evaluateGuess(secret: String, guess: String): Evaluation {

        val rightPositions = secret.zip(guess).count {
            it.first == it.second   // Here was //TODO
        }

        val commonLetters = "ABCDEF".sumBy { ch ->

            Math.min(
                secret.count {
                    it == ch   // Here was //TODO
                },
                guess.count {
                    it == ch   // Here was //TODO
                }
            )
        }
        return Evaluation(rightPositions, commonLetters - rightPositions)
    }

    fun main(args: Array<String>) {
        val result = Evaluation(rightPosition = 1, wrongPosition = 1)
        evaluateGuess("BCDF", "ACEB") eq result
        evaluateGuess("AAAF", "ABCA") eq result
        evaluateGuess("ABCA", "AAAF") eq result
    }

    private infix fun <T> T.eq(other: T) {
        if (this == other) println("OK")
        else println("Error: $this != $other")
    }
}
