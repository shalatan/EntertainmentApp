package com.shalatan.entertainmentapp.data.remote

import com.shalatan.entertainmentapp.domain.model.Movie
import com.shalatan.entertainmentapp.domain.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("3/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

    @GET("3/movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

    @GET("3/movie/{movieId}")
    suspend fun getMovieData(
        @Path("movieId") movieId: Int,
        @Query("append_to_response") atr: String = ApiConstants.VIR
    ): Response<Movie>
}