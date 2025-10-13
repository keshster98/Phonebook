package com.keshen.myapplication.ui.manage

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.keshen.myapplication.MyApp
import com.keshen.myapplication.data.model.Contact
import com.keshen.myapplication.data.repo.ContactRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddContactViewModel(
    repo: ContactRepo
): BaseManageViewModel(repo) {
    override fun submit() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!validateInputs()) return@launch

            val contact = Contact(
                profilePhotoUri = profilePhotoUri.value?.toString(),
                firstName = firstName.value.trim(),
                lastName = lastName.value.trim(),
                phoneNumber = phoneNumber.value,
                birthday = birthday.value,
            )

            repo.addContact(contact)
            _finish.emit(Unit)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[APPLICATION_KEY] as MyApp).repo
                AddContactViewModel(repo = myRepository)
            }
        }
    }
}