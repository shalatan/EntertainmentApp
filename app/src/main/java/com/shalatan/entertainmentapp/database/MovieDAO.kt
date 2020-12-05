package com.shalatan.entertainmentapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(savedMovie: SavedMovie)

    @Update
    suspend fun update(savedMovie: SavedMovie)

    @Query("SELECT * FROM saved_movies_table WHERE isWatched = 1")
    fun getAllWatchedMovies(): LiveData<List<SavedMovie>>

    @Query("SELECT * FROM saved_movies_table WHERE isWatchLater = 1")
    fun getAllWatchLaterMovies(): LiveData<List<SavedMovie>>

    @Delete
    suspend fun delete(savedMovie: SavedMovie)

    @Query("DELETE FROM saved_movies_table")
    suspend fun clear()
}