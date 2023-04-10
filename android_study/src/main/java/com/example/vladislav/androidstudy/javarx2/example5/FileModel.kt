package com.example.vladislav.androidstudy.javarx2.example5

/**
 * Presentation-layer model representing file to be downloaded.
 *
 * @param fileName file name
 * @param progress current progress for downloading a file
 */
data class FileModel(
    val fileName: String,
    val progress: Long
)
