package com.shalatan.entertainmentapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shalatan.entertainmentapp.model.Movie

@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(movie: Movie): Long

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<Movie>>

    @Delete
    suspend fun deleteMovie(movie: Movie)
}