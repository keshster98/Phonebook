package com.keshen.myapplication.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.keshen.myapplication.MyApp
import com.keshen.myapplication.data.model.Contact
import com.keshen.myapplication.data.repo.ContactRepo
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel(
    private val repo: ContactRepo
): ViewModel() {
    val contacts = MutableStateFlow<List<Contact>>(emptyList())

    fun contactsSize(): Boolean {
        return contacts.value.isEmpty()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[APPLICATION_KEY] as MyApp).repo
                HomeViewModel(repo = myRepository)
            }
        }
    }
}