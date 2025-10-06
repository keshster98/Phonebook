package com.keshen.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.keshen.myapplication.data.model.Contact
import com.keshen.myapplication.databinding.LayoutItemContactBinding

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
                llContact.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }
}

