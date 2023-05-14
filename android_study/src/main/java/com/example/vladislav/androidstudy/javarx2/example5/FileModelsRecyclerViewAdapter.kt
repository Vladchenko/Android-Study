package com.example.vladislav.androidstudy.javarx2.example5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vladislav.androidstudy.R

/**
 * Adapter for a recyclerview of List<FileModel>
 */
class FileModelsRecyclerViewAdapter : RecyclerView.Adapter<FileModelsRecyclerViewAdapter.FilesViewHolder>() {

    private var fileProgressModels: List<FileProgressModel>? = null

    /** Set list of [FileProgressModel] to be downloaded */
    fun setFileProgressModels(models: List<FileProgressModel>) {
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
        holder.fileNameTextView.text = fileProgressModels!![position].fileName
        holder.progressTextView.progress = fileProgressModels!![position].progress.toInt()
    }

    override fun getItemCount() = fileProgressModels?.size ?: 0
}