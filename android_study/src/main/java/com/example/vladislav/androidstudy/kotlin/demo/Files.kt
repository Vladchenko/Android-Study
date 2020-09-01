package com.example.kotlinstudy.demo

import android.content.Context
import java.io.File

/**
 * @author Yanchenko Vladislav on 28.08.2020.
 */
class Files(private val context: Context) {

    private val filesDirectory = "//"

    fun createFile(fileName: String) {
        val dir = File(context.getFilesDir().path)
        if (!dir.exists()) {
            dir.mkdir()
            println("Directory $dir has been created")
        }
        val file = File(dir, fileName)
        if (!file.exists()) {
            file.createNewFile()
            println("File $file has been created")
        }
    }

    fun deleteFile(fileName: String) {
        val dir = File(context.getFilesDir(), filesDirectory)
        val file = File(dir, fileName)
        if (file.exists()) {
            file.delete()
            println("File $file has been deleted")
        }
    }

//    fun writeToFile(fileName: String, data: String) {
//        File(fileName).writeText(data)
//    }

    fun writeToFile(fileName: String, vararg args: String) {
        val dir = File(context.getFilesDir(), filesDirectory)
        args.forEach { File(dir, fileName).appendText(it) }
    }

    fun readFromFile(fileName: String) {
        val dir = File(context.getFilesDir(), filesDirectory)
        File(dir, fileName).forEachLine { println(it) }
    }

    fun getFileInfo(fileName: String) {
        val dir = File(context.getFilesDir(), filesDirectory)
        val file = File(dir, fileName)
        if (file.exists()) {
            with(file) {
                println("file.absoluteFile is $absoluteFile")
                println("file.absolutePath is $absolutePath")
                println("file.canonicalFile is $canonicalFile")
                println("file.canonicalPath is $canonicalPath")
                println("file.freeSpace is $freeSpace")
                println("file.isAbsolute is $isAbsolute")
                println("file.isDirectory is $isDirectory")
                println("file.isFile is $isFile")
                println("file.isHidden is $isHidden")
            }
        }
    }

    fun filesDemo() {
        //        val file = context.getFilesDir().name + File.separator + "mydir/newFile.txt"
        val fileName = "newFile.txt"
        val files = Files(context);
        files.createFile(fileName)
        files.getFileInfo(fileName)
        files.writeToFile(fileName, "1", "2", "3", "4", "5", "6", "7")
        files.readFromFile(fileName)
        files.deleteFile(fileName)
    }
}
