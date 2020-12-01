package com.shalatan.entertainmentapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shalatan.entertainmentapp.model.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDAO

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        private val LOCK = Any()

        fun getInstance(context: Context): MovieDatabase {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            MovieDatabase::class.java,
                            "saved_movies_database"
                    ).fallbackToDestructiveMigration()
                            .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}