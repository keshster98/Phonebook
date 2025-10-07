package com.keshen.myapplication.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.keshen.myapplication.databinding.FragmentContactDetailsBinding
import kotlinx.coroutines.launch
import kotlin.getValue

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

                if (contact.updatedAt == contact.createdAt) {
                    mcwUpdatedAt.visibility = View.GONE
                } else {
                    mcwUpdatedAt.visibility = View.VISIBLE
                }
            }
        }
    }
}