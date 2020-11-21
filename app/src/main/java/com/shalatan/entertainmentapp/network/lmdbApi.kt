package com.shalatan.entertainmentapp.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface lmdbApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key")
        api_key: String = "ea9a49ebf2b74721a75aae271ebd3036"
    ): Call<MovieResponse>

    @GET("movie/top-rated")
    suspend fun getTopRatedMovies(
        @Query("api_key")
        api_key: String = "ea9a49ebf2b74721a75aae271ebd3036"
    ): Call<MovieResponse>
}