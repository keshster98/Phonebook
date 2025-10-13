package com.keshen.myapplication.ui.home.nested

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.keshen.myapplication.databinding.FragmentContactsBinding
import com.keshen.myapplication.ui.adapter.ContactAdapter
import com.keshen.myapplication.ui.home.HomeFragmentDirections
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
        setupAdapter()

        lifecycleScope.launch {
            viewModel.contacts.collect { list ->
                if (viewModel.contactsSize(list)) {
                    binding.llEmptyContactsPlaceholder.visibility = View.VISIBLE
                } else {
                    binding.llEmptyContactsPlaceholder.visibility = View.GONE
                }
                adapter.setContacts(list)
            }
        }

        binding.fabAdd.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddContactFragment()
            findNavController().navigate(action)
        }
    }

    fun setupAdapter() {
        adapter = ContactAdapter(emptyList(), onClick = {
            val action = HomeFragmentDirections.actionHomeFragmentToContactDetailsFragment(contact = it)
            findNavController().navigate(action)
        }, isBirthdayList = false)
        binding.rvContacts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvContacts.adapter = adapter
    }
}