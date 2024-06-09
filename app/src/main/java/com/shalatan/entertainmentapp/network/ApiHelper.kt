package com.shalatan.entertainmentapp.network

import com.app.base.StateFullResult
import com.shalatan.entertainmentapp.model.CompleteMovieDetail
import com.shalatan.entertainmentapp.model.MovieResponse

interface ApiHelper {

    suspend fun getNowPlayingMovies(): StateFullResult<MovieResponse>
    suspend fun getPopularMovies(): StateFullResult<MovieResponse>
    suspend fun getTopRatedMovies(): StateFullResult<MovieResponse>
    suspend fun getUpcomingMovies(): StateFullResult<MovieResponse>
    suspend fun getMovieDetail(movieId: Int): StateFullResult<CompleteMovieDetail>
}