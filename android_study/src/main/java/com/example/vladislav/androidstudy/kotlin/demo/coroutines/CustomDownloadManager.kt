package com.example.vladislav.androidstudy.kotlin.demo.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay

/**
 * Напишите программу на Kotlin с использованием корутин, которая УСЛОВНО скачивает содержимое трёх
 * веб-сайтов параллельно и возвращает общее количество символов в каждом файле. Скачивать страницы
 * будем мнимыми ресурсами ("site1.txt", "site2.txt", "site3.txt"), представляющими файлы текста.
 *
 * Предположим, что файлы заранее подготовлены локально, и скачивание сводится к чтению файла.
 *
 * Программа должна возвращать результат примерно следующего вида:
 * Total symbols count from site1.txt: XXXX,
 * Total symbols count from site2.txt: YYYY,
 * Total symbols count from site3.txt: ZZZZ
 *
 * Дополнительное условие: Программа должна обеспечивать параллельность загрузки файлов, но
 * финальный результат должен отображаться строго после завершения всех загрузок.
 *
 * @property scope
 */
class CustomDownloadManager(private val scope: CoroutineScope) {

    private val listDeferreds = mutableListOf<Deferred<String>>()

    fun addUrls(urls: List<String>) {
        listDeferreds.clear()
        urls.forEach { url ->
            listDeferreds.add(downloadWebSite(scope, url))
        }
    }

    // Если нужно здесь же дождаться завершения всех задач
//    fun runTasks() = runBlocking { listDeferreds.awaitAll() }

    suspend fun runDownloads() = listDeferreds.awaitAll()

    // После завершения всех корутин можно завершить работу scope,
    // чтобы убедиться, что все корутины закончены
    // Очищаем задачи после завершения
    fun cleanup() {
        scope.coroutineContext.cancelChildren()
    }
}

private fun downloadWebSite(scope: CoroutineScope, url: String): Deferred<String> {
    return scope.async {
        delay((Math.random() * 3000).toLong())
        "Total symbols count from $url: ${(Math.random() * 10000).toLong()}"
    }
}
