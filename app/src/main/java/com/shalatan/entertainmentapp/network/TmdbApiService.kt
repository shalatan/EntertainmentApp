package com.shalatan.entertainmentapp.network

import com.shalatan.entertainmentapp.model.CompleteMovieDetail
import com.shalatan.entertainmentapp.model.MovieResponse
import com.shalatan.entertainmentapp.utils.Constants
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {

    //add region
    @GET("3/movie/now_playing")
    fun getNowPlayingMoviesAsync(
        @Query("page") page: Int = 1
    ): Deferred<MovieResponse>

    @GET("3/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("3/movie/popular")
    fun getPopularMoviesAsync(
        @Query("page") page: Int = 1
    ): Deferred<MovieResponse>

    @GET("3/movie/top_rated")
    fun getTopRatedMoviesAsync(
        @Query("page") page: Int = 1
    ): Deferred<MovieResponse>

    //add region
    @GET("3/movie/upcoming")
    fun getUpcomingMoviesAsync(
        @Query("page") page: Int = 1
    ): Deferred<MovieResponse>

    @GET("3/movie/{movieId}")
    suspend fun getCompleteMovieDetailAsync(
        @Path("movieId") movieID: Int,
        @Query("append_to_response") atr: String = Constants.VIR
    ): Deferred<CompleteMovieDetail>

    @GET("3/search/movie")
    fun getSearchedMovieAsync(
        @Query("query") search: String
    ): Deferred<MovieResponse>

    //add region
    @GET("3/movie/{movieId}/recommendations")
    fun getRecommendationMoviesAsync(
        @Path("movieId") movieID: Int,
        @Query("page") page: Int = 1
    ): Deferred<MovieResponse>
}