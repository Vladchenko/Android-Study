package com.example.vladislav.androidstudy.kotlin.demo.coroutines.task5

import android.content.Context
import android.util.Log
import com.example.vladislav.androidstudy.kotlin.utils.createFilesDirIfAbsent
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File

/**
 * Creation and removal of files.
 * Breaks SRP, but it's not a big deal for this task
 * Leaving no documentation
 */
class FileManager(val context: Context) {

    private val writeMutex = Mutex() // Глобальный замок для записи файлов
    private val filesDirectory = createFilesDirIfAbsent(context)

    fun createDirectory(relativeFilePath: String): File? {
        if (relativeFilePath.isBlank()) {
            Log.e(TAG, "createDirectory: relativeFilePath is empty")
            return null
        }
        val fileToCreate = File(
            filesDirectory.path + if (relativeFilePath.isNotBlank() && relativeFilePath.first() != File.separator[0]) {
                File.separator + relativeFilePath
            } else {
                relativeFilePath
            }
        )
        fileToCreate.mkdirs()
        Log.i(TAG, "createDirectory: directory named \"$relativeFilePath\" created")
        Log.i(TAG, "createDirectory: absolute path is ${fileToCreate.absolutePath}")
        return fileToCreate
    }

    fun createStubFiles(relativeFilePath: String = "", number: Int) {
        val fullFilePath =
            filesDirectory.path + if (relativeFilePath.isNotBlank() && relativeFilePath.first() != File.separator[0]) {
                File.separator
            } else {
                ""
            } + relativeFilePath
        for (i in 0..number) {
            createFile("$fullFilePath/$i.txt")
            Log.i(TAG, "createStubFiles: file with path ${fullFilePath}/$i.txt created")
        }
    }

    fun getFile(relativeFilePath: String, fileName: String): File? {
        val file =
            File(
                filesDirectory.path + if (relativeFilePath.isNotBlank() && relativeFilePath.first() != File.separator[0]) {
                    File.separator + relativeFilePath
                } else {
                    File.separator
                } + fileName
            )
        return if (file.exists()) {
            Log.i(TAG, "getFile: file with path ${file.path} retrieved")
            file
        } else {
            Log.i(TAG, "getFile: file with path ${file.path} not present")
            null
        }
    }

    suspend fun copyFile(fileToCopy: File, newPath: String): Pair<File, Result> {
        val destinationDir = File(filesDirectory, newPath)

        // Убедимся, что папка существует
        if (!destinationDir.exists()) {
            destinationDir.mkdirs()
        }

        // Создаём новый файл в destinationDir с тем же именем
        val copiedFile = File(destinationDir, fileToCopy.name)

        try {
            writeMutex.withLock {
                // Намеренно создаём редкую ошибку о неудачном копировании, для появления некоторых неуспешных случаев
                if (Math.random() > 0.8) {
                    throw Exception("Some error occurred during file copying")
                }
                fileToCopy.copyTo(copiedFile, overwrite = true)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to copy file: ${e.message}")
            return copiedFile to Result.Failure(e.message ?: "")
        }

        Log.i(TAG, "copyFile: file with path ${fileToCopy.path} copied to ${copiedFile.path}")
        return copiedFile to Result.Success
    }

    fun deleteStubFiles(relativeFilePath: String) {
        val fullFilePath = File(filesDirectory, relativeFilePath)
        if (fullFilePath.exists()) {
            fullFilePath.deleteRecursively()
            Log.i(TAG, "deleteStubFiles: fully deleted on path: ${fullFilePath.absolutePath}")
        } else {
            Log.i(TAG, "deleteStubFiles: path $relativeFilePath does not exist")
        }
    }

    private fun createFile(fileName: String) {
        File(fileName).createNewFile()
    }

    companion object {
        private const val TAG = "FileManager"
    }
}