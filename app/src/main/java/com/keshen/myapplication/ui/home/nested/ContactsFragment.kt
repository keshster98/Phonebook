package com.keshen.myapplication.ui.home.nested

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.keshen.myapplication.databinding.FragmentContactsBinding
import com.keshen.myapplication.ui.adapter.ContactAdapter
import com.keshen.myapplication.ui.home.HomeViewModel
import kotlinx.coroutines.launch

class ContactsFragment: Fragment() {

    private val viewModel: HomeViewModel by viewModels(
        ownerProducer = { requireParentFragment() },
        factoryProducer = { HomeViewModel.Factory }
    )
    private lateinit var adapter: ContactAdapter
    private lateinit var binding: FragmentContactsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.contacts.collect {
                if (viewModel.contactsSize()) {
                    binding.llEmptyContactsPlaceholder.visibility = View.GONE
                } else {
                    binding.llEmptyContactsPlaceholder.visibility = View.VISIBLE
                }
            }
        }
    }
}