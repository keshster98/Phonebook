package com.keshen.myapplication.ui.manage

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keshen.myapplication.data.repo.ContactRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.time.format.DateTimeFormatter

abstract class BaseManageViewModel(
    val repo: ContactRepo
) : ViewModel() {

    val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error.asSharedFlow()

    val profilePhotoUri = MutableStateFlow<Uri?>(null)
    val firstName = MutableStateFlow("")
    val lastName = MutableStateFlow("")
    val phoneNumber = MutableStateFlow("")
    val birthday = MutableStateFlow<LocalDate?>(null)
    val birthdayText = birthday.map { date ->
        date?.format(DateTimeFormatter.ofPattern("dd MMM yyyy")) ?: ""
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    protected val _finish = MutableSharedFlow<Unit>()
    val finish = _finish.asSharedFlow()

    abstract fun submit()

    protected suspend fun validateInputs(): Boolean {
        val firstNameText = firstName.value.trim()
        val lastNameText = lastName.value.trim()
        val phoneNumberText = phoneNumber.value.trim()

        return when {
            firstNameText.isBlank() && lastNameText.isBlank() -> {
                _error.emit("Both first and last name cannot be empty!")
                false
            }
            firstNameText.isBlank() -> {
                _error.emit("First name cannot be empty!")
                false
            }
            lastNameText.isBlank() -> {
                _error.emit("Last name cannot be empty!")
                false
            }
            phoneNumberText.isBlank() -> {
                _error.emit("Phone number cannot be empty!")
                false
            }
            else -> true
        }
    }
}