package com.keshen.myapplication.ui.manage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.keshen.myapplication.R

class EditContactFragment: BaseManageFragment() {
    override val viewModel: EditContactViewModel by viewModels {
        EditContactViewModel.Factory
    }

    private val args: EditContactFragmentArgs by navArgs()

    override fun getManageContactPageTitle() = getString(R.string.update_contact)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel= viewModel
        viewModel.getContact(args.contactId)

        binding.ivClose.setOnClickListener {
            findNavController().popBackStack(R.id.contactDetailsFragment, false)
        }
    }
}