package com.shalatan.entertainmentapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_movies_table")
data class SavedMovie(
    @PrimaryKey
    var Id: Int = -1,
    var movieTitle: String? = "",
    var moviePoster: String? = "",
//    var overview: String? = "",
    var isWatchLater: Boolean = false,
    var isRated: Boolean = false,
//    var recommendationWeight: Int = -1
)
