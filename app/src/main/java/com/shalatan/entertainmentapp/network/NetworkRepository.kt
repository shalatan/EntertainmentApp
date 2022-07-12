package com.shalatan.entertainmentapp.network

import com.shalatan.entertainmentapp.model.MovieResponse
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val tmdbApiService: TmdbApiService) {

    fun getSearchedMovieAsync(apiKey: String, query: String): Deferred<MovieResponse> {
        return tmdbApiService.getSearchedMovieAsync(apiKey, query)
    }
}


