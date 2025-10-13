package com.keshen.myapplication.ui.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.keshen.myapplication.databinding.FragmentHomeBinding
import com.keshen.myapplication.ui.home.nested.ContactsFragment
import com.keshen.myapplication.ui.home.nested.HighlightsFragment
import com.keshen.myapplication.ui.home.nested.SettingsFragment
import com.keshen.myapplication.ui.home.nested.TabsAdapter
import androidx.core.view.get
import androidx.fragment.app.setFragmentResultListener
import com.keshen.myapplication.R

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

        setFragmentResultListener("manage_sort") { _, bundle ->
            val s1 = bundle.getInt("sort1")
            val s2 = bundle.getInt("sort2")
            viewModel.updateSortStates(s1, s2)
        }

        val adapter = TabsAdapter(
            fragments = listOf(ContactsFragment(), HighlightsFragment(), SettingsFragment()),
            fragment = this
        )

        binding.viewPager.adapter = adapter

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.nvBottomNavbar.menu[position].isChecked = true
            }
        })

        binding.nvBottomNavbar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottomNavbarContacts -> binding.viewPager.currentItem = 0
                R.id.bottomNavbarHighlights -> binding.viewPager.currentItem = 1
                R.id.bottomNavbarSettings -> binding.viewPager.currentItem = 2
            }
            true
        }
    }
}