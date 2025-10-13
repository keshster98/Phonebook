package com.keshen.myapplication.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.keshen.myapplication.MyApp
import com.keshen.myapplication.data.model.Contact
import com.keshen.myapplication.data.repo.ContactRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate

class HomeViewModel(
    private val repo: ContactRepo
): ViewModel() {

    val contacts: Flow<List<Contact>> = repo.getAllContacts()
    val today: LocalDate = LocalDate.now()
    fun contactsSize(list: List<Contact>): Boolean = list.isEmpty()
    val birthdayContacts: Flow<List<Contact>> = contacts
        .map { list ->
            list.filter { contact ->
                contact.birthday?.let {
                    it.monthValue == today.monthValue && it.dayOfMonth == today.dayOfMonth
                } ?: false
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[APPLICATION_KEY] as MyApp).repo
                HomeViewModel(repo = myRepository)
            }
        }
    }
}