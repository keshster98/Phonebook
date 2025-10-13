package com.keshen.myapplication.data.repo

import com.keshen.myapplication.data.db.ContactDao
import com.keshen.myapplication.data.model.Contact
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

class ContactRepo(
    private val dao: ContactDao
) {
    fun getAllContacts(): Flow<List<Contact>> {
        return dao.getAllContacts()
    }

    suspend fun getContactById(id: Int): Contact? {
        return dao.getContactById(id)
    }

    suspend fun addContact(contact: Contact) {
        dao.addContact(contact)
    }

    suspend fun updateContact(contact: Contact) {
        val updatedContact = contact.copy(updatedAt = LocalDateTime.now())
        dao.updateContact(updatedContact)
    }

    suspend fun deleteContact(id: Int) {
        dao.deleteContact(id)
    }
}