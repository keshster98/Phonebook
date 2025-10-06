package com.keshen.myapplication.ui.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.keshen.myapplication.databinding.FragmentHomeBinding
import com.keshen.myapplication.ui.home.nested.ContactsFragment
import com.keshen.myapplication.ui.home.nested.HighlightsFragment
import com.keshen.myapplication.ui.home.nested.SettingsFragment
import com.keshen.myapplication.ui.home.nested.TabsAdapter

class HomeFragment: Fragment() {

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.Factory
    }
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TabsAdapter(
            fragments = listOf(ContactsFragment(), HighlightsFragment(), SettingsFragment()),
            fragment = this
        )

        binding.viewPager.adapter = adapter
    }
}