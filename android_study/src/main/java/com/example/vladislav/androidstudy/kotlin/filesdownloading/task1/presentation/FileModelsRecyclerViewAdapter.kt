package com.example.vladislav.androidstudy.kotlin.filesdownloading.task1.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vladislav.androidstudy.R
import com.example.vladislav.androidstudy.kotlin.filesdownloading.task1.data.network.DownloadState

/**
 * Adapter for a recyclerview of List<FileModel>
 */
class FileModelsRecyclerViewAdapter : RecyclerView.Adapter<FileModelsRecyclerViewAdapter.FilesViewHolder>() {

    private var fileProgressModels: List<DownloadState>? = null

    /** Set list of [models] to be displayed in recyclerview */
    fun setFileProgressModels(models: List<DownloadState>) {
        fileProgressModels = models
    }

    /**
     * ViewHolder for [FileProgressModel]
     *
     * @constructor creates instance for class
     * @property itemView view for a current FileModel
     */
    class FilesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fileNameTextView: TextView = itemView.findViewById(R.id.file_mame_text_view)
        val progressTextView: ProgressBar = itemView.findViewById(R.id.progress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.file_downloading_item, parent, false)
        return FilesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FilesViewHolder, position: Int) {
        when (fileProgressModels!![position]) {
            is DownloadState.Downloading -> {
                Log.d("myTag", "progress=${(fileProgressModels!![position] as DownloadState.Downloading).progress}")
                holder.fileNameTextView.text = (fileProgressModels!![position] as DownloadState.Downloading).fileName
                holder.progressTextView.progress =
                    (fileProgressModels!![position] as DownloadState.Downloading).progress
            }

            is DownloadState.Failed -> {
                holder.fileNameTextView.text = (fileProgressModels!![position] as DownloadState.Failed).error.toString()
            }

            is DownloadState.Finished -> {
                holder.fileNameTextView.text =
                    "${(fileProgressModels!![position] as DownloadState.Finished).fileName} downloading complete"
            }
        }
    }

    override fun getItemCount() = fileProgressModels?.size ?: 0
}