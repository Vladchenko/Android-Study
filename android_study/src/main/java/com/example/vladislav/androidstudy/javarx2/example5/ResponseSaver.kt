package com.example.vladislav.androidstudy.javarx2.example5

import okhttp3.Response
import java.io.File

/**
 * Класс отвечает за сохранение данных.
 *
 * @author Yanchenko Vladislav
 */
class ResponseSaver {

    /**
     * Method saves a [response] to a file by its [filePath].
     */
    fun saveDataToFile(filePath: String, response: Response): File {
        val file = File(filePath)
        file.writeBytes(response.body?.bytes()!!)
        return file
    }
}