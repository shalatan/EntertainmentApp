package com.shalatan.entertainmentapp.data.remote

import com.app.base.StateFullResult
import com.shalatan.entertainmentapp.domain.model.Movie
import com.shalatan.entertainmentapp.domain.model.MovieResponse
import com.shalatan.entertainmentapp.utils.toStateFullResult
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getNowPlayingMovies(): StateFullResult<MovieResponse> {
        return apiService.getNowPlayingMovies(page = 1).toStateFullResult()
    }

    override suspend fun getTopRatedMovies(): StateFullResult<MovieResponse> {
        return apiService.getTopRatedMovies(page = 1).toStateFullResult()
    }

    override suspend fun getPopularMovies(): StateFullResult<MovieResponse> {
        return apiService.getPopularMovies(page = 1).toStateFullResult()
    }

    override suspend fun getUpcomingMovies(): StateFullResult<MovieResponse> {
        return apiService.getUpcomingMovies(page = 1).toStateFullResult()
    }

    override suspend fun getMovieData(movieId: Int): StateFullResult<Movie> {
        return apiService.getMovieData(movieId = movieId).toStateFullResult()
    }
}