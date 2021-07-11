package com.example.vladislav.androidstudy.kotlin.study.coursera

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

/**
 * https://www.coursera.org/learn/kotlin-for-java-developers/programming/vmwVT/mastermind-game
 */
fun evaluateGuess(secret: String, guess: String): Evaluation {
    var rightPositions = 0
    var wrongPositions = 0
    val secretToggles = arrayOf(0, 0, 0, 0)
    val guessToggles = arrayOf(0, 0, 0, 0)

    secret.forEachIndexed { secretIndex, secretChar ->
        run {
            if (secretChar == guess[secretIndex]) {
                rightPositions++
                secretToggles[secretIndex] = 1
                guessToggles[secretIndex] = 1
            }
        }
    }

    secret.forEachIndexed { secretIndex, secretChar ->
        run {
            guess.forEachIndexed { guessIndex, guessChar ->
                run {
                    if (secretChar == guessChar
                        && secretToggles[secretIndex] != 1
                        && guessToggles[guessIndex] != 1
                    ) {
                        if (secretIndex == guessIndex) {
                            rightPositions++
                            secretToggles[secretIndex] = 1
                            guessToggles[guessIndex] = 1
                        } else {
                            wrongPositions++
                            secretToggles[secretIndex] = 1
                            guessToggles[guessIndex] = 1
                        }
                    }
                }
            }
        }
    }
    return Evaluation(rightPositions, wrongPositions)
}
