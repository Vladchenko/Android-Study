package com.example.vladislav.androidstudy.kotlin.demo.coroutines.task6

class SequenceComputer {

    /**
     * Корутина здесь и не нужна. Вычисление последовательное. Задача не удачная.
     */
    suspend fun getSequence(k: Int, n: Int): List<Int> {
        require(n >= 2) { "Длина последовательности должна быть минимум 2" }
        val sequence = mutableListOf<Int>()
        var currentValue = 1 // Первое значение последовательности
        sequence.add(currentValue)
        sequence.add(currentValue)

        for (i in 1..n) {
//            delay(30)   // Задержка для имитации
            currentValue += k * i // Следующее значение согласно формуле
            sequence.add(currentValue)
        }
        return sequence
    }
}