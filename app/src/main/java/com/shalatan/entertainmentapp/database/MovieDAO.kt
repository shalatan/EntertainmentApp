package com.shalatan.entertainmentapp.database

import androidx.room.*

@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(savedMovie: SavedMovie)

    @Update
    fun update(savedMovie: SavedMovie)

    @Query("SELECT * FROM saved_movies_table WHERE isRated = 1")
    fun getAllRatedMovies(): List<SavedMovie>

    //TODO delete
    @Query("SELECT * FROM saved_movies_table WHERE isRated = 1")
    fun getAllRatedMoviesList(): List<SavedMovie>

    @Query("SELECT * FROM saved_movies_table WHERE isWatchLater = 1")
    fun getAllWatchLaterMovies(): List<SavedMovie>

    //get all recommended movies i.e. unrated(unwatched) movies
    @Query("SELECT * FROM saved_movies_table WHERE isRated = 0 ORDER BY recommendationWeight DESC")
    fun getAllRecommendedMovies(): List<SavedMovie>

    @Query("DELETE FROM saved_movies_table WHERE isRated = 0 AND isWatchLater = 0")
    fun clearResidueMoviesFromDatabase()

    @Query("UPDATE saved_movies_table SET recommendationWeight = 0 WHERE isWatchLater = 1")
    fun clearRecommendationWeightOfWatchLaterMovies()

    @Query("UPDATE saved_movies_table SET isWatchLater = :isWatchLater WHERE Id = :id")
    suspend fun changeWatchLaterStatus(id: Int, isWatchLater: Boolean)

    @Query("UPDATE saved_movies_table SET isRated = :isRated, rating = :rating WHERE Id = :id")
    suspend fun changeRatedStatus(id: Int, isRated: Boolean, rating: Float)

    @Query("UPDATE saved_movies_table SET isRecommendationConsidered = :isRecommendationConsidered WHERE Id = :id")
    fun changeRecommendationConsideredStatus(id: Int, isRecommendationConsidered: Boolean)

    @Query("UPDATE saved_movies_table SET recommendationWeight = recommendationWeight+:rating WHERE ID = :id")
    suspend fun updateMovieRecommendationWeight(id: Int, rating: Float)

    @Delete
    fun delete(savedMovie: SavedMovie)

    @Query("SELECT count(*)!=0 FROM saved_movies_table WHERE Id = :uid AND isRated = 1")
    suspend fun isMovieInRatedList(uid: Int): Int

    @Query("SELECT count(*)!=0 FROM saved_movies_table WHERE Id = :uid AND isWatchLater = 1")
    suspend fun isMovieInWatchLaterList(uid: Int): Int
}