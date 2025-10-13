package com.keshen.myapplication.ui.manage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.keshen.myapplication.R
import kotlin.getValue

class AddContactFragment: BaseManageFragment() {

    override val viewModel: AddContactViewModel by viewModels {
        AddContactViewModel.Factory
    }

    override fun getManageContactPageTitle() = getString(R.string.add_contact)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }
}