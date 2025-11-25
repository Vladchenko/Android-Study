package com.example.vladislav.androidstudy.kotlin.demo.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoroutinesTasks {
    /**
     * Напиши простую программу на Kotlin с использованием корутин, которая выводит на экран числа
     * от 1 до 5 с задержкой в одну секунду перед каждым выводом.
     *
     * Коммент от Гигачата:
     *     Лучше вернуть какой-то объект типа Job, чтобы можно было управлять выполнением корутины
     *     извне (например, отменить выполнение).
     *
     * Вопрос Гигачату:
     *      А какой профит оборачивания в job ?
     *
     * Ответ
     * Основные преимущества использования Job:
     * 1. Управление жизненным циклом корутины
     * Объект Job позволяет контролировать состояние запущенной корутины:
     * Можно проверять статус корутины (выполняется ли она, завершилась успешно или была отменена).
     * Возможность отмены корутины прямо в процессе выполнения. Это полезно, если пользователь вышел
     * из экрана или приложение должно освободить ресурсы.
     *
     * 2. Удобство работы с группами корутин
     * Если у вас много корутин, запущенных параллельно, их можно объединить в группу с помощью
     * родительского Job. Например, используя конструкцию вроде supervisorScope или coroutineScope,
     * вы можете легко отслеживать весь прогресс группы корутин.
     *
     * 3. Работа с исключениями
     * Если корутина завершится с ошибкой, это событие можно обработать и передать дальше (например,
     * показать ошибку пользователю или сохранить в логи). Объект Job предоставляет удобный механизм
     * обработки ошибок и синхронизации потоков выполнения.
     *
     * 4. Чёткое разделение обязанностей
     * Использование Job помогает разделить ответственность: одна часть приложения создаёт корутину,
     * другая управляет её состоянием и обработкой результатов. Такое деление облегчает тестирование
     * и понимание архитектуры приложения.
     */
    fun task1(): Job {
        return CoroutineScope(Dispatchers.Default).launch {
            for (i in 1..5) {
                delay(1000)
                println(i)
            }
        }
    }

    /**
     * Создай две корутины: одна должна выводить чётные числа от 2 до 10 каждые 2 секунды, вторая
     * — нечётные числа от 1 до 9 каждую секунду. Выводы обеих корутин должны происходить
     * одновременно, чередуя друг друга.
     *
     * Решил Гигачат.
     */
    fun task2() =
        CoroutineScope(Dispatchers.Default).launch {
            val evenNumbersJob = launch {
                var number = 2
                while (number <= 10) {
                    delay(2000L)
                    println("Even Number: $number")
                    number += 2
                }
            }

            val oddNumbersJob = launch {
                var number = 1
                while (number <= 9) {
                    delay(1000L)
                    println("Odd Number: $number")
                    number += 2
                }
            }

            // В Андроиде не обязательно дожидаться, даже желательно не дожидаться
//            joinAll(evenNumbersJob, oddNumbersJob) // Ожидаем завершение обеих корутин
        }

    /**
     * Решение предыдущей задачи каналами
     */
    fun task2_1() = CoroutineScope(Dispatchers.Default).launch {
        val channel = Channel<Int>()

        launch {
            var num = 2
            while (num <= 10) {
                channel.send(num)
                num += 2
                delay(2000L)
            }
        }

        launch {
            var num = 1
            while (num <= 9) {
                channel.send(num)
                num += 2
                delay(1000L)
            }
        }

        repeat(10) {
            println(channel.receive())
        }
    }
}