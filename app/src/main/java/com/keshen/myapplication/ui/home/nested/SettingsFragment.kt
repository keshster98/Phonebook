package com.keshen.myapplication.ui.home.nested

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.keshen.myapplication.databinding.FragmentSettingsBinding
import com.keshen.myapplication.ui.home.HomeViewModel
import kotlin.getValue

class SettingsFragment: Fragment() {

    private val viewModel: HomeViewModel by viewModels(
        ownerProducer = { requireParentFragment() },
        factoryProducer = { HomeViewModel.Factory }
    )
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }
}