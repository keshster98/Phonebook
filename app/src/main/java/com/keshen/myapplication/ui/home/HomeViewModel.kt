package com.keshen.myapplication.ui.home

import android.os.Environment
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDate

class HomeViewModel(
    private val repo: ContactRepo
): ViewModel() {
    var searchState = ""
    var sortState1 = 0
    var sortState2 = 0


    val displayedContacts = MutableStateFlow<List<Contact>>(emptyList())
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

    fun List<Contact>.searchSort(search: String, sort1: Int, sort2: Int): List<Contact> {
        var list = this
        if (search.isNotEmpty()) {
            list = list.filter {
                it.firstName.contains(search, ignoreCase = true) || it.lastName.contains(search, ignoreCase = true)
            }
        }
        list = when (sort2) {
            0 -> { // sort by first name
                if (sort1 == 0) list.sortedBy { it.firstName.lowercase() }
                else list.sortedByDescending { it.firstName.lowercase() }
            }
            1 -> { // sort by last name
                if (sort1 == 0) list.sortedBy { it.lastName.lowercase() }
                else list.sortedByDescending { it.lastName.lowercase() }
            }
            2 -> { // sort by birthday
                if (sort1 == 0) list.sortedWith(compareBy<Contact> { it.birthday }.thenBy { it.firstName.lowercase() })
                else list.sortedWith(compareByDescending<Contact> { it.birthday }.thenBy { it.firstName.lowercase() })
            }
            else -> list
        }
        return list
    }

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            repo.getAllContacts().collect { contacts ->
                displayedContacts.value = contacts.searchSort(searchState, sortState1, sortState2)
            }
        }
    }

    fun updateSortStates(s1: Int, s2: Int) {
        sortState1 = s1
        sortState2 = s2
        refresh()
    }

    fun backupContactsAsCsv(onResult: (String) -> Unit) {
        viewModelScope.launch {
            repo.getAllContacts().collect { list ->
                if (list.isEmpty()) {
                    onResult("EMPTY")
                    return@collect
                }

                val header = "First Name,Last Name,Phone Number,Birthday"
                val rows = list.joinToString("\n") { contact ->
                    val first = contact.firstName.replace(",", " ")
                    val last = contact.lastName.replace(",", " ")
                    val phone = contact.phoneNumber.replace(",", " ")
                    val birthday = contact.birthday?.toString() ?: ""
                    "$first,$last,$phone,$birthday"
                }

                val csvData = "$header\n$rows"

                // Save to Downloads folder
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val filename = "contacts_backup_${System.currentTimeMillis()}.csv"
                val file = File(downloadsDir, filename)

                file.writeText(csvData)
                onResult(file.absolutePath)

                return@collect
            }
        }
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