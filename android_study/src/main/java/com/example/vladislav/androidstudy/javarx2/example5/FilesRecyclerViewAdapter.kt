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
class FilesRecyclerViewAdapter : RecyclerView.Adapter<FilesRecyclerViewAdapter.FilesViewHolder>() {

    private var files: List<FileModel>? = null

    /**
     * Set list of [FileModel] to be downloaded
     */
    fun setFilesToDownload(files: List<FileModel>) {
        this.files = files
    }

    /**
     * ViewHolder for FileModel
     *
     * @constrcutor creates instance for class
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
        holder.fileNameTextView.text = files!![position].fileName
        holder.progressTextView.progress = files!![position].progress.toInt()
    }

    override fun getItemCount() = files?.size ?: 0
}