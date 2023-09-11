package com.shalatan.entertainmentapp.database

import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val dao: MovieDAO) {

    fun insertMovie(savedMovie: SavedMovie) {
        dao.insert(savedMovie)
    }

    suspend fun isMovieInWatchedList(movieId: Int): Int {
        return dao.isMovieInRatedList(movieId)
    }

    suspend fun isMovieInWatchLaterList(movieId: Int): Int {
        return dao.isMovieInWatchLaterList(movieId)
    }

    suspend fun changeRecommendationConsideredStatus(
        movieId: Int,
        isRecommendationConsidered: Boolean
    ) {
        dao.changeRecommendationConsideredStatus(movieId, isRecommendationConsidered)
    }

    suspend fun updateMovieRecommendationWeight(movieId: Int, rating: Float) {
        dao.updateMovieRecommendationWeight(movieId, rating)
    }

    suspend fun getAllRatedMoviesList(): List<SavedMovie> {
        return dao.getAllRatedMoviesList()
    }

    fun getAllRatedMovies() = flow {
        emit(dao.getAllRatedMovies())
    }

    fun getAllWatchLaterMovies() = flow {
        emit(dao.getAllWatchLaterMovies())
    }

    fun getAllRecommendedMovies() = flow {
        emit(dao.getAllRecommendedMovies())
    }

    fun changeMovieWatchLaterStatus(movieId: Int, isWatchLater: Boolean) {
        dao.changeWatchLaterStatus(movieId, isWatchLater)
    }

    fun changeMovieRatedStatus(movieId: Int, isRated: Boolean, rating: Float) {
        dao.changeRatedStatus(movieId, isRated, rating)
    }

    suspend fun clearResidueMovies() {
        dao.clearResidueMoviesFromDatabase()
    }

    suspend fun clearRecommendationWeightForWatchLaterMovies() {
        dao.clearRecommendationWeightOfWatchLaterMovies()
    }

}


