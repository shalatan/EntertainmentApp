package com.shalatan.entertainmentapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDAO {

    @Insert
    suspend fun insert(savedMovie: SavedMovie)

    @Update
    suspend fun update(savedMovie: SavedMovie)

    @Query("SELECT * FROM saved_movies_table")
    fun getAllMovies(): LiveData<List<SavedMovie>>

    @Delete
    suspend fun delete(savedMovie: SavedMovie)
}