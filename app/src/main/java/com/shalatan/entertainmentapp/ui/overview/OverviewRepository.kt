package com.shalatan.entertainmentapp.ui.overview

import com.shalatan.entertainmentapp.database.MovieDAO
import com.shalatan.entertainmentapp.model.MovieResponse
import com.shalatan.entertainmentapp.network.TmdbApiService
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class OverviewRepository @Inject constructor(
    private val dao: MovieDAO,
    private val tmdbApiService: TmdbApiService
) {

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
}


