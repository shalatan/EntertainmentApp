package com.shalatan.entertainmentapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shalatan.entertainmentapp.network.MovieResult

@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(movieResult: MovieResult): Long

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<MovieResult>>

    @Delete
    suspend fun deleteMovie(movieResult: MovieResult)
}