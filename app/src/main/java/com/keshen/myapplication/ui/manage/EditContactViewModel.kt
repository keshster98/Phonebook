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
import java.time.LocalDateTime
import androidx.core.net.toUri

class EditContactViewModel(
    repo: ContactRepo
): BaseManageViewModel(repo) {

    private var contact: Contact? = null

    fun getContact(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getContactById(id)?.let {
                contact = it
                profilePhotoUri.value = it.profilePhotoUri?.toUri()
                firstName.value = it.firstName
                lastName.value = it.lastName
                phoneNumber.value = it.phoneNumber
                birthday.value = it.birthday
            }
        }
    }

    override fun submit() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!validateInputs()) return@launch

            val updated = contact?.copy(
                profilePhotoUri = profilePhotoUri.value?.toString(),
                firstName = firstName.value.trim(),
                lastName = lastName.value.trim(),
                phoneNumber = phoneNumber.value,
                birthday = birthday.value,
                updatedAt = LocalDateTime.now()
            ) ?: return@launch

            repo.updateContact(updated)
            _finish.emit(Unit)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[APPLICATION_KEY] as MyApp).repo
                EditContactViewModel(repo = myRepository)
            }
        }
    }
}