package com.keshen.myapplication.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.keshen.myapplication.data.model.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts")
    fun getAllContacts(): Flow<List<Contact>>
    @Query("SELECT * FROM contacts where id = :id")
    suspend fun getContactById(id: Int): Contact?
    @Insert
    suspend fun addContact(contact: Contact)
    @Update
    suspend fun updateContact(contact: Contact)
    @Query("DELETE FROM contacts WHERE id = :id")
    suspend fun deleteContact(id: Int)
}