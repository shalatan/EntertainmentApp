package com.shalatan.entertainmentapp.ui.moviedetail

import com.shalatan.entertainmentapp.database.MovieDAO
import com.shalatan.entertainmentapp.database.SavedMovie
import com.shalatan.entertainmentapp.model.CompleteMovieDetail
import com.shalatan.entertainmentapp.model.MovieResponse
import com.shalatan.entertainmentapp.network.TmdbApiService
import com.shalatan.entertainmentapp.utils.Constants
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val dao: MovieDAO,
    private val tmdbApiService: TmdbApiService
) {

    suspend fun insertMovie(savedMovie: SavedMovie) {
        dao.insert(savedMovie)
    }

    suspend fun isMovieInWatchedList(movieId: Int): Int {
        return dao.isMovieInRatedList(movieId)
    }

    suspend fun isMovieInWatchLaterList(movieId: Int): Int {
        return dao.isMovieInWatchLaterList(movieId)
    }

    suspend fun changeMovieWatchLaterStatus(movieId: Int, isWatchLater: Boolean) {
        dao.changeWatchLaterStatus(movieId, isWatchLater)
    }

    suspend fun changeMovieRatedStatus(movieId: Int, isWatchLater: Boolean, rating: Float) {
        dao.changeRatedStatus(movieId, isWatchLater, rating)
    }

    suspend fun changeRecommendationConsideredStatus(movieId: Int, isRecommendationConsidered: Boolean) {
        dao.changeRecommendationConsideredStatus(movieId, isRecommendationConsidered)
    }

    fun fetchCompleteMovieDataAsync(movieId: Int): Deferred<CompleteMovieDetail> {
        return tmdbApiService.getCompleteMovieDetailAsync(movieId, Constants.API_KEY, Constants.VIR)
    }

    fun fetchSimilarMoviesDataAsync(movieId: Int): Deferred<MovieResponse> {
        return tmdbApiService.getRecommendationMoviesAsync(movieId, Constants.API_KEY, 1)
    }

    suspend fun updateMovieRecommendationWeight(movieId: Int, rating: Float) {
        dao.updateMovieRecommendationWeight(movieId, rating)
    }

}


