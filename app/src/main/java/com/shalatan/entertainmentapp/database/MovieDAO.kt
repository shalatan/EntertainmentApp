package com.shalatan.entertainmentapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(savedMovie: SavedMovie)

    @Update
    suspend fun update(savedMovie: SavedMovie)

    @Query("SELECT * FROM saved_movies_table WHERE isRated = 1")
    fun getAllWatchedMovies(): LiveData<List<SavedMovie>>

    @Query("SELECT * FROM saved_movies_table WHERE isWatchLater = 1")
    fun getAllWatchLaterMovies(): LiveData<List<SavedMovie>>

    @Delete
    suspend fun delete(savedMovie: SavedMovie)

    @Query("SELECT count(*)!=0 FROM saved_movies_table WHERE Id = :uid AND isRated = 1")
    suspend fun isMovieInRatedList(uid: Int): Int

    @Query("SELECT count(*)!=0 FROM saved_movies_table WHERE Id = :uid AND isWatchLater = 1")
    suspend fun isMovieInWatchLaterList(uid: Int): Int

    @Query("DELETE FROM saved_movies_table")
    suspend fun clear()
}