package com.shalatan.entertainmentapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_movies_table")
data class SavedMovie(
    @PrimaryKey
    var Id: Int = -1,
    var movieTitle: String = "",
    var isWatched: Boolean = false,
    var isWatchLater: Boolean = false
)
