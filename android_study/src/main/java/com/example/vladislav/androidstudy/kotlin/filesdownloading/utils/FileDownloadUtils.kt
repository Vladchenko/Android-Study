package com.example.vladislav.androidstudy.kotlin.filesdownloading.utils

import com.example.vladislav.androidstudy.kotlin.filesdownloading.network.DownloadState

/**
 * Utils to assist files downloading.
 */
object FileDownloadUtils {

    /**
     * Update [filesList] with an item [fileProgressModel] which fileName match
     *
     * @param filesList that has an item to update
     * @param fileProgressModel an item to be replaced with a respective item in filesList
     */
    fun updateListOnlyWithNewItems(filesList: MutableList<DownloadState>, fileProgressModel: DownloadState) {
        var updated = false
        for (i in 0 until filesList.size) {
            // Replace an existing fileProgressModel with the one that has a new progress value
            if ((filesList[i] as DownloadState.Downloading).fileName == (fileProgressModel as DownloadState.Downloading).fileName) {
                filesList[i] = fileProgressModel
                updated = true
            }
        }
        if (!updated) {
            filesList.add(fileProgressModel)
        }
    }
}