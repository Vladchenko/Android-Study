package com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vladislav.androidstudy.R
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.model.ContactInfoModel
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.view.ContactViewHolder

/** Adapter to pass contacts data to recyclerview. */
class ContactsRecyclerViewAdapter(private val clickListener: (String) -> Unit) :
    RecyclerView.Adapter<ContactViewHolder>() {

    private var contactItems = mutableListOf<ContactInfoModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.contact_view, parent, false)
        return ContactViewHolder(view, clickListener)
    }

    override fun getItemCount() = contactItems.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contactItems[position])
    }

    fun updateContacts(contactsNewItems: List<ContactInfoModel>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(contactItems, contactsNewItems))
        contactItems.clear()
        contactItems.addAll(contactsNewItems)
        diffResult.dispatchUpdatesTo(this)
    }

    private class DiffUtilCallback (
        private val oldList: List<ContactInfoModel>,
        private val newList: List<ContactInfoModel>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] === newList[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}