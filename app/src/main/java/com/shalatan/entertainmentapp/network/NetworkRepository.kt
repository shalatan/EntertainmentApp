package com.shalatan.entertainmentapp.network

import com.shalatan.entertainmentapp.model.CompleteMovieDetail
import com.shalatan.entertainmentapp.model.MovieResponse
import com.shalatan.entertainmentapp.utils.Constants
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.flow
import java.util.concurrent.Flow
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val tmdbApiService: TmdbApiService) {

    fun getNowPlayingMovies() = flow<MovieResponse> {
        emit(tmdbApiService.getNowPlayingMovies(1))
    }

    fun getTopRatedMovies() = flow<MovieResponse> {
        emit(tmdbApiService.getTopRatedMovies(1))
    }

    fun getUpcomingMovies() = flow<MovieResponse> {
        emit(tmdbApiService.getUpcomingMovies(1))
    }

    fun getPopularMovies() = flow<MovieResponse> {
        emit(tmdbApiService.getPopularMovies(1))
    }

    fun getMovieCompleteData(movieId: Int) = flow<CompleteMovieDetail> {
        emit(tmdbApiService.getCompleteMovieDetail(movieId, Constants.VIR))
    }

    fun getSimilarMovies(movieId: Int) = flow<MovieResponse> {
        emit(tmdbApiService.getSimilarMovies(movieId))
    }

    fun getSearchedMovieAsync(query: String) = flow<MovieResponse> {
        emit(tmdbApiService.getSearchedMovieAsync(query))
    }
}


