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
    private var onClick: (Contact) -> Unit,
    private val isBirthdayList: Boolean = false
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
                    // Force clear Coil’s cached image to prevent ghost overlay
                    ivProfile.load(null as Uri?) {
                        placeholder(android.R.color.transparent)
                        error(android.R.color.transparent)
                        // Disable caching entirely for this load
                        memoryCachePolicy(coil.request.CachePolicy.DISABLED)
                        diskCachePolicy(coil.request.CachePolicy.DISABLED)
                    }
                    ivProfile.setImageDrawable(null)
                    ivPlaceholder.visibility = View.VISIBLE
                } else {
                    ivProfile.load(item.profilePhotoUri.toUri()) {
                        crossfade(true)
                        placeholder(android.R.color.transparent)
                        error(android.R.color.transparent)
                        // Prevent Coil from reusing outdated cached version
                        memoryCachePolicy(coil.request.CachePolicy.DISABLED)
                        diskCachePolicy(coil.request.CachePolicy.ENABLED)
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
                    val message = if (isBirthdayList)
                        "Happy Birthday, ${item.firstName}! Best wishes always and I hope you'll have a grand time with your loved ones."
                    else
                        "Hi, ${item.firstName}."

                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("sms:${item.phoneNumber}")
                        putExtra("sms_body", message)
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

