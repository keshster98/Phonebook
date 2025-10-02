package com.keshen.myapplication

import android.app.Application
import androidx.room.Room
import com.google.android.material.color.DynamicColors
import com.keshen.myapplication.data.db.MyDatabase
import com.keshen.myapplication.data.repo.ContactRepo

class MyApp: Application() {
    lateinit var repo: ContactRepo

    override fun onCreate() {
        super.onCreate()

        // Applies Material You dynamic colors to all activities if the device supports it.
        // On supported devices, the app’s theme will automatically adapt to the user’s wallpaper colors.
        // On older devices, the default theme colors defined in themes.xml will be used instead.
        DynamicColors.applyToActivitiesIfAvailable(this)

        val db = Room.databaseBuilder(
            this,
            MyDatabase::class.java,
            MyDatabase.NAME
        ).build()
        repo = ContactRepo(db.getContactDao())
    }
}