package com.keshen.myapplication.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Parcelize
@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val profilePhotoUri: String? = null,
    val firstName: String,
    val lastName: String? = null,
    val phoneNumber: String,
    val birthday: LocalDate? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
): Parcelable {
    val fullName: String
        get() = listOfNotNull(firstName, lastName).joinToString(" ")

    val formattedBirthday: String
        get() = birthday?.format(DateTimeFormatter.ofPattern("dd MMM yyyy")) ?: " "

    val formattedCreatedAt: String
        get() = createdAt.format(DateTimeFormatter.ofPattern("HH:mm, dd MMM yyyy"))

    val formattedUpdatedAt: String
        get() = updatedAt.format(DateTimeFormatter.ofPattern("HH:mm, dd MMM yyyy"))
}