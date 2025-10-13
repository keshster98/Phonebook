package com.keshen.myapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.keshen.myapplication.data.model.Contact

@Database(entities = [Contact::class], version = 1)
@TypeConverters(Converters::class)
abstract class MyDatabase: RoomDatabase() {
    abstract fun getContactDao(): ContactDao

    companion object {
        const val NAME = "my_database"
    }
}