package com.example.vladislav.androidstudy.kotlin.sometasks.stopwatch

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Менеджер для приостановки корутины.
 */
class SuspensionManager {
    private var deferred: CompletableDeferred<Unit>? = null

    suspend fun suspendIndefinitely() = withContext(Dispatchers.IO) {
        val newDeferred = CompletableDeferred<Unit>()
        deferred = newDeferred
        try {
            newDeferred.await()
        } catch (e: Exception) {
            println("Приостановка отменена: $e")
        } finally {
            deferred = null
        }
    }

    fun resume() {
        deferred?.complete(Unit)
    }

    fun resumeWithException(exception: Throwable) {
        deferred?.completeExceptionally(exception)
    }
}



// Рабочий вариант, но не предпочтительный (старый способ).

//private var continuation: CancellableContinuation<Unit>? = null
//
//suspend fun suspendIndefinitely() = suspendCancellableCoroutine<Unit> { cont ->
//    continuation = cont
//    cont.invokeOnCancellation {
//        continuation = null
//        println("Приостановка отменена")
//    }
//}
//
//fun resume() {
//    continuation?.resume(Unit)
//    continuation = null
//}
//
//fun resumeWithException(exception: Throwable) {
//    continuation?.resumeWithException(exception)
//    continuation = null
//}

//Старый способ                                   Новый способ
//suspendCancellableCoroutine                     CompletableDeferred
//Ручное управление через invokeOnCancellation    Автоматическое завершение через await()
//Больше кода и сложнее в понимании               Просто и интуитивно понятно
//
//🧪 Плюсы нового подхода:
//
//Чище и проще — меньше ручного управления.
//Меньше ошибок — стандартные конструкции Kotlinx Coroutines проверены временем.
//Легче тестировать — вы можете просто вызвать deferred.complete(...) в юнит-тестах.
//
//🔄 Как это работает?
//Когда вызывается suspendIndefinitely(), создается новый CompletableDeferred. Корутина ждет, пока этот deferred не будет завершен (через resume() или resumeWithException()). При этом, если корутина была отменена, блок finally очищает ссылку на deferred.