package com.shalatan.entertainmentapp.data.remote

import com.app.base.StateFullResult
import com.shalatan.entertainmentapp.domain.model.Movie
import com.shalatan.entertainmentapp.domain.model.MovieResponse

interface ApiHelper {

    suspend fun getNowPlayingMovies(): StateFullResult<MovieResponse>

    suspend fun getTopRatedMovies(): StateFullResult<MovieResponse>

    suspend fun getPopularMovies(): StateFullResult<MovieResponse>

    suspend fun getUpcomingMovies(): StateFullResult<MovieResponse>

    suspend fun getMovieData(movieId: Int): StateFullResult<Movie>
}