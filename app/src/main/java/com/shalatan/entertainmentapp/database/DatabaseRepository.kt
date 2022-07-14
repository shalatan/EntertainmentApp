package com.shalatan.entertainmentapp.database

import androidx.lifecycle.LiveData
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val dao: MovieDAO) {

    suspend fun insertMovie(savedMovie: SavedMovie) {
        dao.insert(savedMovie)
    }

    suspend fun isMovieInWatchedList(movieId: Int): Int {
        return dao.isMovieInRatedList(movieId)
    }

    suspend fun isMovieInWatchLaterList(movieId: Int): Int {
        return dao.isMovieInWatchLaterList(movieId)
    }

    suspend fun changeRecommendationConsideredStatus(movieId: Int, isRecommendationConsidered: Boolean) {
        dao.changeRecommendationConsideredStatus(movieId, isRecommendationConsidered)
    }

    suspend fun updateMovieRecommendationWeight(movieId: Int, rating: Float) {
        dao.updateMovieRecommendationWeight(movieId, rating)
    }
    fun getAllWatchedMovies(): LiveData<List<SavedMovie>> {
        return dao.getAllWatchedMovies()
    }

    fun getAllWatchLaterMovies(): LiveData<List<SavedMovie>> {
        return dao.getAllWatchLaterMovies()
    }

    fun getAllRecommendedMovies(): LiveData<List<SavedMovie>> {
        return dao.getAllRecommendedMovies()
    }

    suspend fun changeMovieWatchLaterStatus(movieId: Int, isWatchLater: Boolean) {
        dao.changeWatchLaterStatus(movieId, isWatchLater)
    }

    suspend fun changeMovieRatedStatus(movieId: Int, isRated: Boolean, rating: Float) {
        dao.changeRatedStatus(movieId, isRated, rating)
    }
}


