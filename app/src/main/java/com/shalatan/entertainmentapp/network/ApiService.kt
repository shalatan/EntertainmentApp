package com.shalatan.entertainmentapp.network

import com.shalatan.entertainmentapp.model.CompleteMovieDetail
import com.shalatan.entertainmentapp.model.MovieResponse
import com.shalatan.entertainmentapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("3/movie/now_playing")
    suspend fun newGetNowPlayingMovies(
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

    @GET("3/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 1
    ): MovieResponse

    //add region
    @GET("3/movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("3/movie/{movieId}")
    suspend fun getCompleteMovieDetail(
        @Path("movieId") movieID: Int,
        @Query("append_to_response") atr: String = Constants.VIR
    ): CompleteMovieDetail

    @GET("3/movie/{movieId}/recommendations")
    suspend fun getSimilarMovies(
        @Path("movieId") movieID: Int,
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("3/search/movie")
    suspend fun getSearchedMovieAsync(
        @Query("query") search: String
    ): MovieResponse
}