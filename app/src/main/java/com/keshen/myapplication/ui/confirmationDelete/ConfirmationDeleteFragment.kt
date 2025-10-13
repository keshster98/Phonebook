package com.keshen.myapplication.ui.confirmationDelete

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.keshen.myapplication.databinding.FragmentConfirmationDeleteBinding
import kotlin.getValue

class ConfirmationDeleteFragment: DialogFragment() {
    private lateinit var binding: FragmentConfirmationDeleteBinding
    private val viewModel: ConfirmationDeleteViewModel by viewModels {
        ConfirmationDeleteViewModel.Factory
    }
    private val args: ConfirmationDeleteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfirmationDeleteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mbCancel.setOnClickListener {
            dismiss()
        }

        binding.mbDelete.setOnClickListener {
            viewModel.delete(args.contactId)
            val action = ConfirmationDeleteFragmentDirections.actionConfirmationDeleteFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }
}