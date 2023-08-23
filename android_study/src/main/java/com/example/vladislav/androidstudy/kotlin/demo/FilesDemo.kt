package com.example.vladislav.androidstudy.kotlin.demo

import android.content.Context
import com.example.vladislav.androidstudy.kotlin.utils.createFilesDirIfAbsent
import java.io.File
import java.nio.file.Files
import kotlin.math.roundToInt

/**
 * Files operations demonstration.
 *
 * @author Yanchenko Vladislav on 28.08.2020.
 */
class FilesDemo(private val context: Context) {

    private val filesDirectory = "//"
    private val map = mutableMapOf<Int, Boolean>()
    private val defaultFilesDirectory: File = createFilesDirIfAbsent(context)

    /**
     * Method that runs all the files ops demonstration.
     */
    fun filesDemo() {
        createFoldersAndFilesRandomly()
        traverseFoldersAndFiles()
        deleteAllFoldersAndFiles()
    }

    private fun createFoldersAndFilesRandomly() {
        println("\n\nAll the files and folders are located in " + context.filesDir)
        println("\n\n>>>>>>>>> Files and folders creation has begun >>>>>>>>>")
        createFoldersAndFiles(
            folderToCreateAndEnterInitial = "",
            filesPerFolderMax = 3,
            foldersInOneLevelMax = 2,
            folderLevelsMax = 5
        )
        println("<<<<<<<<< Files and folders creation has finished <<<<<<<<<")
    }

    private fun traverseFoldersAndFiles() {
        println("\n\n>>>>>>>>> Folders and Files traversal has begun >>>>>>>>>")
        getFolderContents(context.filesDir, -1)
        println("<<<<<<<<< Folders and Files traversal has finished <<<<<<<<<\n\n")
    }

    private fun deleteAllFoldersAndFiles() {
        println("\n\n>>>>>>>>> Files and folders deletion has begun >>>>>>>>>")
        context.filesDir.listFiles()?.map {
            it.deleteRecursively()
        }
        println("<<<<<<<<< Files and folders deletion finished <<<<<<<<<\n\n")
    }

    private fun createFile(fileName: String) {
        val file = File(defaultFilesDirectory, fileName)
        if (!file.exists()) {
            file.createNewFile()
            println("File $file has been created")
        } else {
            println("File $file has NOT been created, it already exists")
        }
    }

    private fun createFolder(folderName: String) {
        val folder = File(defaultFilesDirectory, folderName)
        if (!folder.exists()) {
            folder.mkdir()
            println("Folder $folder has been created")
        } else {
            println("Folder $folder already exists")
        }
    }

    private fun deleteFile(fileName: String) {
        val dir = File(defaultFilesDirectory, filesDirectory)
        val file = File(dir, fileName)
        if (file.exists()) {
            file.delete()
            println("File $file has been deleted")
        }
    }

    // fun writeToFile(fileName: String, data: String) {
    //     File(fileName).writeText(data)
    // }

    private fun writeToFile(fileName: String, vararg args: String) {
        val dir = File(defaultFilesDirectory, filesDirectory)
        args.forEach { File(dir, fileName).appendText(it) }
    }

    private fun readFromFile(fileName: String) {
        val dir = File(defaultFilesDirectory, filesDirectory)
        File(dir, fileName).forEachLine { println(it) }
    }

    private fun getFileInfo(fileName: String) {
        val dir = File(defaultFilesDirectory, filesDirectory)
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

    private fun getFolderContents(fileOrFolder: File?, folderLevel: Int) {
        fileOrFolder?.listFiles()?.map {
            printTabs(folderLevel)
            if (Files.isRegularFile(it.toPath())) {
                printFileName(it, fileOrFolder)
            } else {
                printFolderName(it, fileOrFolder, folderLevel)
                getFolderContents(it, folderLevel + 1)
            }
        }
    }

    private fun printFolderName(it: File, fileOrFolder: File, folderLevel: Int) {
        if (it == fileOrFolder.listFiles()?.last()) {
            print('└')
            map[folderLevel + 1] = false
        } else {
            print('├')
            map[folderLevel + 1] = true
        }
        println("Folder ${it.path.replace(context.filesDir.path, FILE_PATH_SUBSTITUTE)}")
    }

    private fun printFileName(it: File, fileOrFolder: File) {
        if (it == fileOrFolder.listFiles()?.last()) {
            print('└')
        } else {
            print('├')
        }
        println("File ${it.path.replace(context.filesDir.path, FILE_PATH_SUBSTITUTE)}")
    }

    private fun printTabs(folderLevel: Int) {
        for (i in 0..folderLevel) {
            if (map.containsKey(i)) {
                if (map[i]!!) {
                    print("│ ")
                } else {
                    print("  ")
                }
            }
        }
    }

    private fun createFoldersAndFiles(
        folderToCreateAndEnterInitial: String,
        filesPerFolderMax: Int,
        foldersInOneLevelMax: Int,
        folderLevelsMax: Int
    ) {
        for (i in 0..(Math.random() * filesPerFolderMax).roundToInt()) {
            createFile(
                folderToCreateAndEnterInitial
                    + File.separator
                    + FILE.nameSuffix()
                    + FILE_EXTENTION
            )
        }
        for (i in 0..(Math.random() * foldersInOneLevelMax).roundToInt()) {
            val folderToCreateAndEnter = folderToCreateAndEnterInitial + File.separator + FOLDER.nameSuffix()
            createFolder(folderToCreateAndEnter)
            if (folderLevelsMax - 1 > 0) {
                createFoldersAndFiles(
                    folderToCreateAndEnter,
                    filesPerFolderMax,
                    foldersInOneLevelMax,
                    folderLevelsMax - 1
                )
            }
        }
    }

    private fun String.nameSuffix() = this + (Math.random() * 100000).roundToInt().toString()

    companion object {
        private const val FILE_PATH_SUBSTITUTE = "..."
        private const val FILE_EXTENTION = ".txt"
        private const val FILE = "file"
        private const val FOLDER = "folder"
    }
}
