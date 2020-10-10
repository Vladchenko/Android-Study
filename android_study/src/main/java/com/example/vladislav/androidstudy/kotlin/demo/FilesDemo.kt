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

    private val defaultFilesDirectory: File = createFilesDirIfAbsent(context)

    private val filesDirectory = "//"

    /**
     * Method that runs all the files ops demonstration.
     */
    fun filesDemo() {
        createFilesRandomly()
        traverseFilesAndFolders()
        deleteAllFilesAndFolders()
    }

    private fun createFilesRandomly() {
        println("\n\nAll the files and folders are located in " + context.filesDir)
        println("\n\n>>>>>>>>> Files and folders creation has begun >>>>>>>>>")
        createDirsAndFiles(
            folderToCreateAndEnterInitial = "",
            filesPerFolderMax = 2,
            foldersInOneLevelMax = 2,
            folderLevelsMax = 4
        )
        println("<<<<<<<<< Files and folders creation has finished <<<<<<<<<")
    }

    private fun traverseFilesAndFolders() {
        println("\n\n>>>>>>>>> Files and folders traverse has begun >>>>>>>>>")
        getFolderContents(context.filesDir, -1)
        println("<<<<<<<<< Files and folders traverse has finished <<<<<<<<<\n\n")
    }

    private fun deleteAllFilesAndFolders() {
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
            println("Folder $folder has NOT been created, it already exists")
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
            for (i in 0..folderLevel) {
                print("  ")
            }
            if (Files.isRegularFile(it.toPath())) {
                if (it.equals(fileOrFolder.listFiles()?.last())) {
                    print('└')
                } else {
                    print('├')
                }
                println("File ${it.path.replace(context.filesDir.path, FILE_PATH_SUBSTITUTE)}")
            } else {
                if (it.equals(fileOrFolder.listFiles()?.last())) {
                    print('└')
                } else {
                    print('├')
                }
                println("Folder ${it.path.replace(context.filesDir.path, FILE_PATH_SUBSTITUTE)}")
                getFolderContents(it, folderLevel + 1)
            }
        }
    }

    private fun createDirsAndFiles(
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
                createDirsAndFiles(
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
