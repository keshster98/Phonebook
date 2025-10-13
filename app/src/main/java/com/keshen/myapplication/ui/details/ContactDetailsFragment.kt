package com.keshen.myapplication.ui.details

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.keshen.myapplication.R
import com.keshen.myapplication.databinding.FragmentContactDetailsBinding
import kotlinx.coroutines.launch
import kotlin.getValue
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController

class ContactDetailsFragment: Fragment() {
    private lateinit var binding: FragmentContactDetailsBinding
    private val args: ContactDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val contact = args.contact

            binding.run {
                tvContactName.text = contact.fullName
                tvPhoneNumber.text = contact.phoneNumber
                tvBirthday.text = contact.formattedBirthday
                tvCreatedAt.text = contact.formattedCreatedAt
                tvUpdatedAt.text = contact.formattedUpdatedAt

                if (contact.birthday == null) {
                    mcwBirthday.visibility = View.GONE
                } else {
                    mcwBirthday.visibility = View.VISIBLE
                }

                if (contact.isRecentlyCreated) {
                    mcwUpdatedAt.visibility = View.GONE
                } else {
                    mcwUpdatedAt.visibility = View.VISIBLE
                }

                if (contact.profilePhotoUri.isNullOrEmpty()) {
                    ivProfile.setImageResource(R.drawable.ic_outline_person_24)
                } else {
                    ivProfile.load(contact.profilePhotoUri.toUri()) {
                        crossfade(true)
                        placeholder(R.drawable.ic_outline_person_24)
                        error(R.drawable.ic_outline_person_24)
                    }
                }

                ivBack.setOnClickListener {
                    findNavController().popBackStack(R.id.homeFragment, false)
                }

                btnCall.setOnClickListener {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = "tel:${contact.phoneNumber}".toUri()
                    }
                    it.context.startActivity(intent)
                }

                btnMessage.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = "sms:${contact.phoneNumber}".toUri()
                        putExtra("sms_body", "Hi, ${contact.firstName}.") // prefilled message
                    }
                    it.context.startActivity(intent)
                }

                ivEdit.setOnClickListener {
                    val action = ContactDetailsFragmentDirections.actionContactDetailsFragmentToEditContactFragment(contact.id!!)
                    findNavController().navigate(action)
                }

                ivDelete.setOnClickListener {
                    val action = ContactDetailsFragmentDirections.actionContactDetailsFragmentToConfirmationDeleteFragment(contact.id!!)
                    findNavController().navigate(action)
                }
            }
        }
    }
}
