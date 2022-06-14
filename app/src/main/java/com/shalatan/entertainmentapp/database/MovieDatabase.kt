package com.shalatan.entertainmentapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SavedMovie::class], version = 3, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun logDao(): MovieDAO
}