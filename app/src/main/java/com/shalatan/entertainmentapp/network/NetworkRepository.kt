package com.shalatan.entertainmentapp.network

import com.shalatan.entertainmentapp.model.CompleteMovieDetail
import com.shalatan.entertainmentapp.model.MovieResponse
import com.shalatan.entertainmentapp.utils.Constants
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val tmdbApiService: TmdbApiService) {

    fun getTopRatedMoviesAsync(): Deferred<MovieResponse> {
        return tmdbApiService.getTopRatedMoviesAsync(1)
    }

    fun getPopularMoviesAsync(): Deferred<MovieResponse> {
        return tmdbApiService.getPopularMoviesAsync(1)
    }

    fun getNowPlayingMoviesAsync(): Deferred<MovieResponse> {
        return tmdbApiService.getNowPlayingMoviesAsync(1)
    }

    fun getUpcomingMoviesAsync(): Deferred<MovieResponse> {
        return tmdbApiService.getUpcomingMoviesAsync(1)
    }

    fun getSearchedMovieAsync(apiKey: String, query: String): Deferred<MovieResponse> {
        return tmdbApiService.getSearchedMovieAsync(apiKey, query)
    }

    fun fetchCompleteMovieDataAsync(movieId: Int): Deferred<CompleteMovieDetail> {
        return tmdbApiService.getCompleteMovieDetailAsync(movieId, Constants.API_KEY, Constants.VIR)
    }

    fun fetchSimilarMoviesDataAsync(movieId: Int): Deferred<MovieResponse> {
        return tmdbApiService.getRecommendationMoviesAsync(movieId, Constants.API_KEY, 1)
    }
}


