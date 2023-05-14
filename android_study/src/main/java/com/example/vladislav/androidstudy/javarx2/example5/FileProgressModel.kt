package com.example.vladislav.androidstudy.javarx2.example5

/**
 * Presentation-layer model representing file to be downloaded.
 *
 * @property fileName file name
 * @property progress current progress for downloading a file
 */
data class FileProgressModel(
    val fileName: String,
    val progress: Long
)
