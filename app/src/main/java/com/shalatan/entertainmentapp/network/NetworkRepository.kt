package com.shalatan.entertainmentapp.network

import com.shalatan.entertainmentapp.model.CompleteMovieDetail
import com.shalatan.entertainmentapp.model.MovieResponse
import com.shalatan.entertainmentapp.utils.Constants
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val apiService: ApiService) {

    fun getNowPlayingMovies() = flow<MovieResponse> {
        emit(apiService.getNowPlayingMovies(1))
    }

    fun getTopRatedMovies() = flow<MovieResponse> {
        emit(apiService.getTopRatedMovies(1))
    }

    fun getUpcomingMovies() = flow<MovieResponse> {
        emit(apiService.getUpcomingMovies(1))
    }

    fun getPopularMovies() = flow<MovieResponse> {
        emit(apiService.getPopularMovies(1))
    }

    fun getMovieCompleteData(movieId: Int) = flow<CompleteMovieDetail> {
        emit(apiService.getCompleteMovieDetail(movieId, Constants.VIR))
    }

    fun getSimilarMovies(movieId: Int) = flow<MovieResponse> {
        emit(apiService.getSimilarMovies(movieId))
    }

    fun getSearchedMovieAsync(query: String) = flow<MovieResponse> {
        emit(apiService.getSearchedMovieAsync(query))
    }
}


