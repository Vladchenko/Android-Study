package com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vladislav.androidstudy.R
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.model.ContactInfoModel

/**
 * View holder for a phone contact
 *
 * @param itemView view for this holder
 * @param clickListener that accepts clicks onto this view
 */
class ContactViewHolder(itemView: View, private val clickListener: (String) -> Unit) :
    RecyclerView.ViewHolder(itemView) {

    private val idTextView = itemView.findViewById(R.id.contact_id_text_view) as TextView
    private val nameTextView = itemView.findViewById(R.id.contact_name_text_view) as TextView
    private val phoneNumberTextView =
        itemView.findViewById(R.id.contact_phone_number_text_view) as TextView

    fun bind(contactInfo: ContactInfoModel) {
        idTextView.text = contactInfo.id.toString()
        nameTextView.text = contactInfo.name
        phoneNumberTextView.text = contactInfo.phoneNumber
        itemView.setOnClickListener { clickListener.invoke(contactInfo.phoneNumber) }
    }
}