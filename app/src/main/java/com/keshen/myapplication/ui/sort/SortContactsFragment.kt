package com.keshen.myapplication.ui.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.keshen.myapplication.R
import com.keshen.myapplication.databinding.FragmentSortContactsBinding
import kotlin.getValue

class SortContactsFragment: DialogFragment() {

    private lateinit var binding: FragmentSortContactsBinding
    private val args: SortContactsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSortContactsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        var selectedOrder = args.sort1
        var selectedBy = args.sort2

        fun radioCheck() {
            binding.run {
                rgOrder.check(when(selectedOrder) {
                    0 -> R.id.rbAsc
                    1 -> R.id.rbDesc
                    else -> R.id.rbAsc
                })
                rgBy.check(when(selectedBy) {
                    0 -> R.id.rbFirstName
                    1 -> R.id.rbLastName
                    2 -> R.id.rbBirthday
                    else -> R.id.rbFirstName
                })
            }
        }

        binding.run {
            radioCheck()
            rgOrder.setOnCheckedChangeListener { _, checkedId ->
                selectedOrder = when(checkedId) {
                    R.id.rbAsc -> 0
                    R.id.rbDesc -> 1
                    else -> 0
                }
            }
            rgBy.setOnCheckedChangeListener { _, checkedId ->
                selectedBy = when(checkedId) {
                    R.id.rbFirstName -> 0
                    R.id.rbLastName -> 1
                    R.id.rbBirthday -> 2
                    else -> 0
                }
            }
            mbDone.setOnClickListener {
                val resultBundle = bundleOf("sort1" to selectedOrder, "sort2" to selectedBy)
                setFragmentResult("manage_sort", resultBundle)
                dismiss()
            }
        }
    }
}