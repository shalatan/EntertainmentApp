package com.shalatan.entertainmentapp.network

import com.app.base.StateFullResult
import com.shalatan.entertainmentapp.model.CompleteMovieDetail
import com.shalatan.entertainmentapp.model.MovieResponse
import com.shalatan.entertainmentapp.utils.toStateFullResult
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getNowPlayingMovies(): StateFullResult<MovieResponse> {
        return apiService.newGetNowPlayingMovies(1).toStateFullResult()
    }

    override suspend fun getPopularMovies(): StateFullResult<MovieResponse> {
        return apiService.newGetPopularMovies(1).toStateFullResult()
    }

    override suspend fun getTopRatedMovies(): StateFullResult<MovieResponse> {
        return apiService.newGetTopRatedMovies(1).toStateFullResult()
    }

    override suspend fun getUpcomingMovies(): StateFullResult<MovieResponse> {
        return apiService.newGetUpcomingMovies(1).toStateFullResult()
    }

    override suspend fun getMovieDetail(movieId: Int): StateFullResult<CompleteMovieDetail> {
        return apiService.newGetCompleteMovieDetail(movieId).toStateFullResult()
    }
}