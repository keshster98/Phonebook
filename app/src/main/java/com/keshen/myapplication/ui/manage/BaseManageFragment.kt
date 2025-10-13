package com.keshen.myapplication.ui.manage

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.keshen.myapplication.R
import com.keshen.myapplication.databinding.FragmentBaseManageBinding
import kotlinx.coroutines.launch
import java.time.LocalDate
import android.net.Uri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.keshen.myapplication.ui.home.HomeFragmentDirections

abstract class BaseManageFragment : Fragment() {
    protected lateinit var binding: FragmentBaseManageBinding
    protected abstract val viewModel: BaseManageViewModel
    protected abstract fun getManageContactPageTitle(): String

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            requireContext().contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            viewModel.profilePhotoUri.value = it
            binding.ivPlaceholder.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseManageBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTitle.text = getManageContactPageTitle()

        binding.ivProfile.setOnClickListener {
            pickImageLauncher.launch(arrayOf("image/*"))
        }

        binding.ivProfile.setOnLongClickListener {
            viewModel.profilePhotoUri.value = null
            binding.ivProfile.setImageDrawable(null)
            binding.ivPlaceholder.visibility = View.VISIBLE
            true
        }

        binding.etBirthday.setOnClickListener {
            val currentDate = viewModel.birthday.value ?: LocalDate.now()

            val datePicker = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    viewModel.birthday.value = LocalDate.of(year, month + 1, dayOfMonth)
                },
                currentDate.year,
                currentDate.monthValue - 1,
                currentDate.dayOfMonth
            )

            // Prevent future dates
            datePicker.datePicker.maxDate = System.currentTimeMillis()
            datePicker.show()
        }

        binding.etBirthday.setOnLongClickListener {
            viewModel.birthday.value = null
            true
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profilePhotoUri.collect { uri ->
                    if (uri != null) {
                        binding.ivProfile.setImageURI(uri)
                        binding.ivPlaceholder.visibility = View.GONE
                    } else {
                        binding.ivProfile.setImageDrawable(null)
                        binding.ivPlaceholder.visibility = View.VISIBLE
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.finish.collect {
                findNavController().popBackStack(R.id.homeFragment, false)
            }
        }

        lifecycleScope.launch {
            viewModel.error.collect {
                showToast(it)
            }
        }
    }

    private fun showToast(msg: String) {
        val toast = Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT)
        toast.show()
    }
}