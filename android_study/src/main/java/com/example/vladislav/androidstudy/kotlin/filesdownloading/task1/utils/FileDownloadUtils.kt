package com.example.vladislav.androidstudy.kotlin.filesdownloading.task1.utils

import com.example.vladislav.androidstudy.kotlin.filesdownloading.task1.data.network.DownloadState

/**
 * Utils to assist files downloading.
 */
object FileDownloadUtils {

    /** Update [filesList] with an item [fileProgressModel] which fileName matching */
    fun updateListOnlyWithNewItems(
        filesList: MutableList<DownloadState>,
        fileProgressModel: DownloadState
    ) {
        var updated = false
        for (i in 0 until filesList.size) {
            // Replace an existing fileProgressModel with the one that has a new progress value
            if ((filesList[i] as DownloadState.Downloading).fileName
                == (fileProgressModel as DownloadState.Downloading).fileName
            ) {
                filesList[i] = fileProgressModel
                updated = true
            }
        }
        if (!updated) {
            filesList.add(fileProgressModel)
        }
    }
}