package com.keshen.myapplication.ui.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.keshen.myapplication.data.model.Contact
import com.keshen.myapplication.databinding.LayoutItemContactBinding
import coil.load
import androidx.core.net.toUri

class ContactAdapter (
    private var contacts: List<Contact>,
    private var onClick: (Contact) -> Unit
): RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder {
        val binding = LayoutItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ContactViewHolder,
        position: Int
    ) {
        val contact = contacts[position]
        holder.bind(contact)
    }

    override fun getItemCount() = contacts.size

    fun setContacts(contacts: List<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }

    inner class ContactViewHolder(
        val binding: LayoutItemContactBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Contact) {
            binding.run {
                tvFirstName.text = item.firstName
                tvLastName.text = item.lastName

                if (item.profilePhotoUri.isNullOrEmpty()) {
                    ivProfile.setImageDrawable(null)
                    ivPlaceholder.visibility = View.VISIBLE
                } else {
                    ivProfile.load(item.profilePhotoUri.toUri()) {
                        crossfade(true)
                    }
                    ivPlaceholder.visibility = View.GONE
                }

                ivCall.setOnClickListener {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = "tel:${item.phoneNumber}".toUri()
                    }
                    it.context.startActivity(intent)
                }

                ivText.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = "sms:${item.phoneNumber}".toUri()
                        putExtra("sms_body", "Hi, ${item.firstName}.") // prefilled message
                    }
                    it.context.startActivity(intent)
                }

                llContact.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }
}

