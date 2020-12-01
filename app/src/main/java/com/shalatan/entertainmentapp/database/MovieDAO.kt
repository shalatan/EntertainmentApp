package com.shalatan.entertainmentapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shalatan.entertainmentapp.model.Movie

@Dao
interface MovieDAO {

    @Insert
    suspend fun insert(movie: Movie)

    @Update
    suspend fun update(movie: Movie)

    @Query("SELECT * FROM saved_movies_table")
    fun getAllMovies(): LiveData<List<Movie>>

    @Delete
    suspend fun deleteMovie(movie: Movie)
}