package com.keshen.myapplication.ui.confirmationDelete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.keshen.myapplication.MyApp
import com.keshen.myapplication.data.repo.ContactRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConfirmationDeleteViewModel(
    private val repo: ContactRepo
): ViewModel() {

    fun delete(contactId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteContact(contactId)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[APPLICATION_KEY] as MyApp).repo
                ConfirmationDeleteViewModel(repo = myRepository)
            }
        }
    }
}